package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Utils.FormatDate;

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
        FormatDate format = new FormatDate();
        fecha = format.fromISO8601UTC(fechaApi);

        return fecha;
    }


    public String getFechaApi() {

        return fechaApi;
    }

    public double getObjetivo() {
        return objetivo;
    }


}

