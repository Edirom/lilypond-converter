package de.edirom.meigarage.lilypond;

public enum Conversion {

    /*
      supported formats
     */
    LILYPONDTOPDF(
            "lilypondtopdf", // "id"
            "text/x-lilypond", // "input mime type"
            "lilypond", // "input format id"
            "LilyPond file (max version " + LilyPondConverter.LILYPOND_VERSION + ")", // "input description"
            "score", // "input type" (score, audio, image, customization)
            "application/pdf", // "output mime type"
            "pdf", // "output format id"
            "PDF Score", // "output description"
            "score", // "output type" (score, audio, image, customization)
            true, // "visible as input"
            9 // "cost"
    ),

    LILYPONDTOPNG(
            "lilypondtopng", // "id"
            "text/x-lilypond", // "mime type for target"
            "lilypond", // "input format id"
            "LilyPond file (max version " + LilyPondConverter.LILYPOND_VERSION + ")", // "input description"
            "score", // "input type" (score, audio, image, customization)
            "image/png", // "output mime type"
            "png", // "output format id"
            "Portable Network Graphics (PNG)", // "output description"
            "score", // "output type" (score, audio, image, customization)
            true, // "visible as input"
            9 // "cost"
    )
    ;


    private String id;
    private String iMimeType;
    private String iFormatId;
    private String iDescription;
    private String iType;
    private String oMimeType;
    private String oFormatId;
    private String oDescription;
    private String oType;
    private boolean visible;
    private int cost;

    Conversion(String id, String iMimeType, String iFormatId, String iDescription, String iType, String oMimeType, String oFormatId, String oDescription, String oType, boolean visible, int cost){
        this.id = id;
        this.iMimeType = iMimeType;
        this.iFormatId = iFormatId;
        this.iDescription = iDescription;
        this.iType = iType;
        this.oMimeType = oMimeType;
        this.oFormatId = oFormatId;
        this.oDescription = oDescription;
        this.oType = oType;
        this.visible = visible;
        this.cost = cost;
    }

    public String getId(){
        return id;
    }

    public String getIMimeType(){
        return iMimeType;
    }

    public String getOMimeType() { return oMimeType; }

    public String getOFormatId(){
        return oFormatId;
    }

    public String getIFormatId(){
        return iFormatId;
    }

    public String getInputDescription() {
        return iDescription;
    }

    public String getInputType() {
        return iType;
    }

    public String getOutputDescription() {
        return oDescription;
    }

    public String getOutputType() {
        return oType;
    }


    public boolean getVisible() {
        return visible;
    }

    public int getCost() {
        return cost;
    }
}
