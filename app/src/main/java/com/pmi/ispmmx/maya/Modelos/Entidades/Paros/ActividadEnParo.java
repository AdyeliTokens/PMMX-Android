package com.pmi.ispmmx.maya.Modelos.Entidades.Paros;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;

import java.util.Date;

/**
 * Created by ispmmx on 7/31/17.
 */

public class ActividadEnParo {
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("IdPersona")
    private int idPersona;
    @SerializedName("IdParo")
    private int idParo;
    @SerializedName("Descripcion")
    private String descripcion;
    @SerializedName("Fecha")
    private Date fecha;

    @SerializedName("Paro")
    private Paro paro;
    @SerializedName("Persona")
    private Persona Ejecutante;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdParo() {
        return idParo;
    }

    public void setIdParo(int idParo) {
        this.idParo = idParo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paro getParo() {
        return paro;
    }

    public void setParo(Paro paro) {
        this.paro = paro;
    }

    public Persona getEjecutante() {
        return Ejecutante;
    }

    public void setEjecutante(Persona ejecutante) {
        Ejecutante = ejecutante;
    }
}
