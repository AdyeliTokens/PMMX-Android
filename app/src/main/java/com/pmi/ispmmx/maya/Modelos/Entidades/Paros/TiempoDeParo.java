package com.pmi.ispmmx.maya.Modelos.Entidades.Paros;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by chan jacky chan on 30/08/2017.
 */

public class TiempoDeParo {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Inicio")
    @Expose
    private String inicioApi;

    @SerializedName("Fin")
    @Expose
    private String finApi;

    private Date inicio;
    private Date fin;

    @SerializedName("IdParo")
    @Expose
    private int idParo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getInicio() {
        if (inicioApi != "") {
            int idx1 = inicioApi.indexOf("(");
            int idx2 = inicioApi.indexOf(")") - 5;
            String s = inicioApi.substring(idx1 + 1, idx2);
            long l = Long.valueOf(s);
            inicio = new Date(l);
        } else {
            inicio = null;
        }


        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        if (inicioApi != "") {
            int idx1 = finApi.indexOf("(");
            int idx2 = finApi.indexOf(")") - 5;
            String s = finApi.substring(idx1 + 1, idx2);
            long l = Long.valueOf(s);
            fin = new Date(l);
        } else {
            fin = null;
        }


        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public int getIdParo() {
        return idParo;
    }

    public void setIdParo(int idParo) {
        this.idParo = idParo;
    }
}
