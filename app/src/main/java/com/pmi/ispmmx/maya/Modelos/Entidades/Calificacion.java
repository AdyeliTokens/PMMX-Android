package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;

import java.util.Date;

/**
 * Created by chan jacky chan on 29/11/2017.
 */

public class Calificacion {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdIndicador")
    @Expose
    private int idIndicador;

    @SerializedName("IdLinkUp")
    @Expose
    private int idLinkup;

    @SerializedName("IdCalificante")
    @Expose
    private int idCalificante;

    @SerializedName("Resultado")
    @Expose
    private Double resultado;

    @SerializedName("Fecha")
    @Expose
    private Date fecha;


    @SerializedName("Calificante")
    @Expose
    private Persona calificante;


    @SerializedName("LinkUp")
    @Expose
    private WorkCenter linkup;

    @SerializedName("Indicador")
    @Expose
    private WorkCenter indicador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(int idIndicador) {
        this.idIndicador = idIndicador;
    }

    public int getIdLinkup() {
        return idLinkup;
    }

    public void setIdLinkup(int idLinkup) {
        this.idLinkup = idLinkup;
    }

    public int getIdCalificante() {
        return idCalificante;
    }

    public void setIdCalificante(int idCalificante) {
        this.idCalificante = idCalificante;
    }

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Persona getCalificante() {
        return calificante;
    }

    public void setCalificante(Persona calificante) {
        this.calificante = calificante;
    }

    public WorkCenter getLinkup() {
        return linkup;
    }

    public void setLinkup(WorkCenter linkup) {
        this.linkup = linkup;
    }

    public WorkCenter getIndicador() {
        return indicador;
    }

    public void setIndicador(WorkCenter indicador) {
        this.indicador = indicador;
    }
}
