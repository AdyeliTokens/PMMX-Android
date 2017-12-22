package com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;

import java.util.List;


public class Modulo {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdSeccion")
    @Expose
    private int idSeccion;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("NombreCorto")
    private String nombreCorto;

    @SerializedName("Activo")
    private Boolean activo;

    @SerializedName("WorkCenters")
    private List<WorkCenter> workCenterList;

    @SerializedName("Paros")
    private List<Paro> paros;

    @SerializedName("Defectos")
    private List<Defecto> defectos;

    @SerializedName("Origenes")
    private List<Origen> origenes;

    @SerializedName("Seccion")
    private ModuloSeccion seccion;


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

    public List<WorkCenter> getWorkCenterList() {
        return workCenterList;
    }

    public void setWorkCenterList(List<WorkCenter> workCenterList) {
        this.workCenterList = workCenterList;
    }

    public List<Paro> getParos() {
        return paros;
    }

    public void setParos(List<Paro> paros) {
        this.paros = paros;
    }

    public List<Defecto> getDefectos() {
        return defectos;
    }

    public void setDefectos(List<Defecto> defectos) {
        this.defectos = defectos;
    }

    public List<Origen> getOrigenes() {
        return origenes;
    }

    public void setOrigenes(List<Origen> origenes) {
        this.origenes = origenes;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public ModuloSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(ModuloSeccion seccion) {
        this.seccion = seccion;
    }
}
