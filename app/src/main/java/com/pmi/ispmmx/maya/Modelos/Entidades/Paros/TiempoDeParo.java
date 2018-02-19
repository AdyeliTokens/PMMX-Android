package com.pmi.ispmmx.maya.Modelos.Entidades.Paros;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Utils.FormatDate;

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
            FormatDate format = new FormatDate();
            inicio = format.fromISO8601UTC(inicioApi);

        } else {
            inicio = null;
        }
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        if (finApi != "") {
            FormatDate format = new FormatDate();
            fin = format.fromISO8601UTC(finApi);

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
