package com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;

import java.util.List;

/**
 * Created by ispmmx on 7/19/17.
 */

public class BussinesUnit {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdResponsable")
    private int idResponsable;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("NombreCorto")
    private String nombreCorto;

    @SerializedName("Activo")
    private Boolean activo;

    @SerializedName("Responsable")
    private Persona responsable;

    @SerializedName("Mecanicos")
    private List<Persona> mecanicos;

    @SerializedName("WorkCenters")
    private List<WorkCenter> workCenterList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
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

    public List<Persona> getMecanicos() {
        return mecanicos;
    }

    public void setMecanicos(List<Persona> mecanicos) {
        this.mecanicos = mecanicos;
    }

    public List<WorkCenter> getWorkCenterList() {
        return workCenterList;
    }

    public void setWorkCenterList(List<WorkCenter> workCenterList) {
        this.workCenterList = workCenterList;
    }
}
