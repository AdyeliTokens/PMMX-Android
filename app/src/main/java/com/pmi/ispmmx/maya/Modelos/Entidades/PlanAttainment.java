package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class PlanAttainment {
    @SerializedName("Plan_Attainment_Total")
    @Expose
    private double plan_total;

    @SerializedName("Objetivo")
    @Expose
    private double objetivo;

    @SerializedName("Fecha")
    @Expose
    private String fechaApi;

    private Date fecha;

    public double getPlan_total() {
        return plan_total;
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

    public double getObjetivo() {
        return objetivo;
    }


}

