package de.edirom.meigarage.lilypond;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import pl.psnc.dl.ege.component.Converter;
import pl.psnc.dl.ege.configuration.EGEConfigurationManager;
import pl.psnc.dl.ege.configuration.EGEConstants;
import pl.psnc.dl.ege.exception.ConverterException;
import pl.psnc.dl.ege.types.ConversionActionArguments;
import pl.psnc.dl.ege.types.DataType;
import pl.psnc.dl.ege.utils.EGEIOUtils;
import pl.psnc.dl.ege.utils.IOResolver;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class LilyPondConverter implements Converter {

    private static final Logger LOGGER = Logger.getLogger(LilyPondConverter.class);
    private IOResolver ior = EGEConfigurationManager.getInstance()
            .getStandardIOResolver();

    protected static final String LILYPOND_VERSION = "2.20.0";

    public void convert(InputStream inputStream, OutputStream outputStream,
                        ConversionActionArguments conversionDataTypes)
            throws ConverterException, IOException {

        boolean found = false;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        for (ConversionActionArguments cadt : ConverterConfiguration.CONVERSIONS) {
            if (conversionDataTypes.equals(cadt)) {
                String profile = cadt.getProperties().get(ConverterConfiguration.PROFILE_KEY);
                LOGGER.info(dateFormat.format(date) + ": Converting FROM:  "
                        + conversionDataTypes.getInputType().toString()
                        + " TO "
                        + conversionDataTypes.getOutputType().toString()
                        + " WITH profile " + profile );
                convertDocument(inputStream, outputStream, cadt.getInputType(), cadt.getOutputType(),
                        cadt.getProperties());
                found = true;
            }
        }
        if (!found) {
            throw new ConverterException(
                    ConverterException.UNSUPPORTED_CONVERSION_TYPES);
        }

    }

    /*
     * Prepares transformation : based on MIME type.
     */
    private void convertDocument(InputStream inputStream, OutputStream outputStream,
                                 DataType fromDataType, DataType toDataType, Map<String, String> properties) throws IOException,
            ConverterException {

        // LilyPond to PDF
        if (fromDataType.getFormat().equals(Conversion.LILYPONDTOPDF.getIFormatId()) &&
                toDataType.getFormat().equals(Conversion.LILYPONDTOPDF.getOFormatId())) {

            properties.put("extension", "pdf");
            performLilyPondTransformation(inputStream, outputStream, "pdf", properties);

        }
        // LilyPond to PNG
        else if(fromDataType.getFormat().equals(Conversion.LILYPONDTOPNG.getIFormatId()) &&
                toDataType.getFormat().equals(Conversion.LILYPONDTOPNG.getOFormatId())) {

            properties.put("extension", "png");
            performLilyPondTransformation(inputStream, outputStream, "png", properties);
        }
    }

    private void performLilyPondTransformation(InputStream inputStream, OutputStream outputStream, String format,
                                               Map<String, String> properties) throws IOException, ConverterException {

        File inTmpDir = null;
        File outTempDir = null;
        try {
            inTmpDir = prepareTempDir();
            ior.decompressStream(inputStream, inTmpDir);
            // avoid processing files ending in .bin
            File inputFile = searchForData(inTmpDir, "^.*(?<!bin)$");
            if(inputFile!=null) {
                String newFileName = inputFile.getAbsolutePath().substring(0, inputFile.getAbsolutePath().lastIndexOf(".")) + ".ly";
                inputFile.renameTo(new File(newFileName));

                outTempDir = prepareTempDir();

                LOGGER.warn("inTmpDir: " + inTmpDir);
                LOGGER.warn("inputFile: " + inputFile);
                LOGGER.warn("outTempDir: " + outTempDir);

                ProcessBuilder builder = new ProcessBuilder();
                builder.command("sh", "-c", "lilypond --output=" + outTempDir +  " --format=" + format + " " + newFileName);
                builder.directory(inTmpDir);
                Process process = builder.start();

                LilyPondRunner runner = new LilyPondRunner(process.getInputStream());
                Executors.newSingleThreadExecutor().submit(runner);
                int exitCode = process.waitFor();

                if(exitCode != 0) {
                    throw new ConverterException("LilyPond process ended with exit code " + exitCode);
                }

                ior.compressData(outTempDir, outputStream);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            /*
            if (outTempDir != null && outTempDir.exists())
                EGEIOUtils.deleteDirectory(outTempDir);
            if (inTmpDir != null && inTmpDir.exists())
                EGEIOUtils.deleteDirectory(inTmpDir);

             */
        }
    }

    private File prepareTempDir() {
        File inTempDir = null;
        String uid = UUID.randomUUID().toString();
        inTempDir = new File(EGEConstants.TEMP_PATH + File.separator + uid
                + File.separator);
        inTempDir.mkdir();
        return inTempDir;
    }

    /*
     * Search for specified by regex file
     */
    private File searchForData(File dir, String regex) {
        for (File f : dir.listFiles()) {
            if (!f.isDirectory() && Pattern.matches(regex, f.getName())) {
                return f;
            } else if (f.isDirectory()) {
                File sf = searchForData(f, regex);
                if (sf != null) {
                    return sf;
                }
            }
        }
        return null;
    }

    public List<ConversionActionArguments> getPossibleConversions() {
        return ConverterConfiguration.CONVERSIONS;
    }
}
