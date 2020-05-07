package de.edirom.meigarage.lilypond;

public enum Conversion {

    /*
      supported formats
     */
    LILYPONDTOPDF(
            "lilypondtopdf", // "id"
            "application/pdf", // "mime type for target"
            "lilypond", // "input format id"
            "LilyPond file (max version " + LilyPondConverter.LILYPOND_VERSION + ")", // "input description"
            "score", // "input type" (score, audio, image, customization)
            "pdf", // "output format id"
            "PDF Score", // "output description"
            "score", // "output type" (score, audio, image, customization)
            true, // "visible as input"
            9 // "cost"
    ),

    LILYPONDTOPNG(
            "lilypondtopng", // "id"
            "image/png", // "mime type for target"
            "lilypond", // "input format id"
            "LilyPond file (max version " + LilyPondConverter.LILYPOND_VERSION + ")", // "input description"
            "score", // "input type" (score, audio, image, customization)
            "png", // "output format id"
            "Portable Network Graphics (PNG)", // "output description"
            "score", // "output type" (score, audio, image, customization)
            true, // "visible as input"
            9 // "cost"
    )
    ;


    private String id;
    private String mimeType;
    private String iFormatId;
    private String iDescription;
    private String iType;
    private String oFormatId;
    private String oDescription;
    private String oType;
    private boolean visible;
    private int cost;

    Conversion(String id, String mimeType, String iFormatId, String iDescription, String iType, String oFormatId, String oDescription, String oType, boolean visible, int cost){
        this.id = id;
        this.mimeType = mimeType;
        this.iFormatId = iFormatId;
        this.iDescription = iDescription;
        this.iType = iType;
        this.oFormatId = oFormatId;
        this.oDescription = oDescription;
        this.oType = oType;
        this.visible = visible;
        this.cost = cost;
    }

    public String getId(){
        return id;
    }

    public String getMimeType(){
        return mimeType;
    }

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
