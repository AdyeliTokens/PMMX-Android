package com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Indicador;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;

import java.util.List;


public class Area {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("NombreCorto")
    @Expose
    private String nombreCorto;

    @SerializedName("Activo")
    @Expose
    private Boolean activo;

    @SerializedName("Responsable")
    @Expose
    private Persona responsable;

    @SerializedName("BussinesUnit")
    @Expose
    private List<BussinesUnit> bussinesUnit;

    @SerializedName("Indicadores")
    @Expose
    private List<Indicador> indicadores;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Persona getResponsable() {
        return responsable;
    }

    public void setResponsable(Persona responsable) {
        this.responsable = responsable;
    }

    public List<BussinesUnit> getBussinesUnit() {
        return bussinesUnit;
    }

    public void setBussinesUnit(List<BussinesUnit> bussinesUnit) {
        this.bussinesUnit = bussinesUnit;
    }

    public List<Indicador> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(List<Indicador> indicadores) {
        this.indicadores = indicadores;
    }
}
