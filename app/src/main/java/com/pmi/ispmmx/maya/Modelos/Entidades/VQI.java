package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Utils.FormatDate;

import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        FormatDate format = new FormatDate();
        fecha = format.fromISO8601UTC(fechaApi);

        return fecha;
    }

    public String getFechaApi() {

        return fechaApi;
    }

    public int getObjetivo() {
        return objetivo;
    }


}
