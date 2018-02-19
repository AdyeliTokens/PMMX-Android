package com.pmi.ispmmx.maya.Modelos.Entidades.Defectos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;
import com.pmi.ispmmx.maya.Utils.FormatDate;

import java.util.Date;


public class ActividadEnDefecto {
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("IdPersona")
    private int idPersona;
    @SerializedName("IdDefecto")
    private int idDefecto;
    @SerializedName("Descripcion")
    private String descripcion;
    private Date fecha;
    @SerializedName("Fecha")
    @Expose
    private String fechaApi;
    @SerializedName("Defecto")
    private Defecto defecto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        FormatDate format = new FormatDate();
        fecha = format.fromISO8601UTC(fechaApi);

        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Defecto getDefecto() {
        return defecto;
    }

    public void setDefecto(Defecto defecto) {
        this.defecto = defecto;
    }

    public Persona getEjecutante() {
        return Ejecutante;
    }

    public void setEjecutante(Persona ejecutante) {
        Ejecutante = ejecutante;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }
}
