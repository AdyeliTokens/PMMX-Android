package com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Calificacion;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;

import java.util.List;

/**
 * Created by ispmmx on 7/19/17.
 */

public class WorkCenter {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdBussinesUnit")
    @Expose
    private int idBussinesUnit;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("NombreCorto")
    @Expose
    private String nombreCorto;

    @SerializedName("Activo")
    @Expose
    private Boolean activo;

    @SerializedName("BussinesUnit")
    @Expose
    private BussinesUnit bussinesUnit;

    @SerializedName("DefectosActivos")
    @Expose
    private int defectosActivos;

    @SerializedName("ParosActivos")
    @Expose
    private int parosActivos;


    @SerializedName("Modulos")
    @Expose
    private List<Origen> origenes;

    @SerializedName("Responsable")
    @Expose
    private Persona responsable;

    @SerializedName("IdResponsable")
    @Expose
    private int idResponsable;

    @SerializedName("Calificaciones")
    @Expose
    private List<Calificacion> calificaciones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBussinesUnit() {
        return idBussinesUnit;
    }

    public void setIdBussinesUnit(int idBussinesUnit) {
        this.idBussinesUnit = idBussinesUnit;
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

    public BussinesUnit getBussinesUnit() {
        return bussinesUnit;
    }

    public void setBussinesUnit(BussinesUnit bussinesUnit) {
        this.bussinesUnit = bussinesUnit;
    }

    public List<Origen> getOrigenes() {
        return origenes;
    }

    public void setOrigenes(List<Origen> origenes) {
        this.origenes = origenes;
    }

    public int getDefectosActivos() {
        return defectosActivos;
    }

    public void setDefectosActivos(int defectosActivos) {
        this.defectosActivos = defectosActivos;
    }

    public int getParosActivos() {
        return parosActivos;
    }

    public void setParosActivos(int parosActivos) {
        this.parosActivos = parosActivos;
    }

    public Persona getResponsable() {
        return responsable;
    }

    public void setResponsable(Persona responsable) {
        this.responsable = responsable;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }
}
