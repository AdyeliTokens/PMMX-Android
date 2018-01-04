package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by chan jacky chan on 01/12/2017.
 */

public class VQI {
    @SerializedName("VQI_Total")
    @Expose
    private int vqi_total;

    @SerializedName("Objetivo")
    @Expose
    private int objetivo;

    @SerializedName("Fecha")
    @Expose
    private String fechaApi;

    private Date fecha;

    public int getVqi_total() {
        return vqi_total;
    }


    public Date getFecha() {
        int idx1 = fechaApi.indexOf("(");
        int idx2 = fechaApi.indexOf(")") - 5;
        String s = fechaApi.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        fecha = new Date(l);
        return fecha;
    }


    public Date getFechaApi() {
        int idx1 = fechaApi.indexOf("(");
        int idx2 = fechaApi.indexOf(")") - 5;
        String s = fechaApi.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        fecha = new Date(l);
        return fecha;
    }

    public int getObjetivo() {
        return objetivo;
    }


}
