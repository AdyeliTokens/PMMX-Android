package com.pmi.ispmmx.maya.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chan jacky chan on 29/01/2018.
 */

public class Infopdate {


    @SerializedName("NewVersion")
    @Expose
    private Double newVersion;

    @SerializedName("url")
    @Expose
    private String URL;

    @SerializedName("releaseNotes")
    @Expose
    private String releaseNotes;

    @SerializedName("Fecha")
    @Expose
    private String fecha;


    public Double getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(Double newVersion) {
        this.newVersion = newVersion;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }
}
