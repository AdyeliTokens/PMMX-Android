package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.ActividadEnDefecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.ActividadEnParo;

import java.util.Date;

public class Feed {

    private Date fecha;
    @SerializedName("Fecha")
    @Expose
    private String fechaApi;

    @SerializedName("actividadEnDefecto")
    @Expose
    private ActividadEnDefecto actividadEnDefecto;

    @SerializedName("actividadEnParo")
    @Expose
    private ActividadEnParo actividadEnParo;


    public Date getFecha() {
        int idx1 = fechaApi.indexOf("(");
        int idx2 = fechaApi.indexOf(")") - 5;
        String s = fechaApi.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        fecha = new Date(l);

        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ActividadEnDefecto getActividadEnDefecto() {
        return actividadEnDefecto;
    }

    public void setActividadEnDefecto(ActividadEnDefecto actividadEnDefecto) {
        this.actividadEnDefecto = actividadEnDefecto;
    }

    public ActividadEnParo getActividadEnParo() {
        return actividadEnParo;
    }

    public void setActividadEnParo(ActividadEnParo actividadEnParo) {
        this.actividadEnParo = actividadEnParo;
    }
}
