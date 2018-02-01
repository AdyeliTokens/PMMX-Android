package com.pmi.ispmmx.maya.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Infopdate {


    @SerializedName("versionCode")
    @Expose
    private int versionCode;


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
