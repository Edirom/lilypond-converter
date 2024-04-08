package de.edirom.meigarage.lilypond;

import pl.psnc.dl.ege.configuration.EGEConfigurationManager;
import pl.psnc.dl.ege.exception.ConverterException;
import pl.psnc.dl.ege.types.ConversionActionArguments;
import pl.psnc.dl.ege.types.DataType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class LilyPondConverterTest {
    private LilyPondConverter converter;

    @org.junit.Before
    public void setUp() throws Exception {
        converter = new LilyPondConverter();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        converter = null;
    }

    @org.junit.Test
    public void convert() throws IOException, ConverterException {
        //InputStream is = new FileInputStream("src/test/resources/test-input.ly.zip");
        InputStream is = new FileInputStream("src/test/resources/test-input.ly.zip");
        //OutputStream os = new FileOutputStream("src/test/resources/test-output.pdf.zip");
        OutputStream os = new FileOutputStream("src/test/resources/test-output.png.zip");
        DataType inputType = new DataType("lilypond","text/x-lilypond");
        DataType outputType = new DataType("png","image/png");
        //DataType inputType = new DataType("mei40","text/xml");
        //DataType outputType = new DataType("lilypond","text/x-lilypond");
        //DataType outputType = new DataType("pdf-lilypond","application/pdf");
        ConversionActionArguments conversionActionArguments = new ConversionActionArguments(inputType, outputType, null);
        String tempDir = "src/test/temp";
        converter.convert(is, os, conversionActionArguments, tempDir);
        //assertNotNull(new File("src/test/resources/test-output.png.zip"));
        assertNotNull(new File("src/test/resources/test-output.png.zip"));
        InputStream isout = new FileInputStream("src/test/resources/test-output.png.zip");
        EGEConfigurationManager.getInstance().getStandardIOResolver().decompressStream(isout, new File("src/test/resources/test-output.png"));
        //System.out.println(new String(Files. readAllBytes(Paths.get("src/test/resources/test-output.txt/result.txt")), "UTF-8"));
        //assertNotEquals("", new String(Files.readAllBytes(Paths.get("src/test/resources/test-output.png/test.png")), "UTF-8"));
        assertArrayEquals("Binary files differ",
                Files.readAllBytes(Paths.get("src/test/resources/test.png")),
                Files.readAllBytes(Paths.get("src/test/resources/test-output.png/test.png")));
        is.close();
        os.close();
        isout.close();
    }

    @org.junit.Test
    public void getPossibleConversions() {
        assertNotNull(converter.getPossibleConversions());
        System.out.println(converter.getPossibleConversions());
    }
}