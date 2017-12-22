package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;

import java.util.List;

/**
 * Created by ispmmx on 7/19/17.
 */

public class Persona {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdPuesto")
    private int idPuesto;

    @SerializedName("IdWorkCenter")
    private int idWorkCenter;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Apellido1")
    private String apellido1;

    @SerializedName("Apellido2")
    private String apellido2;

    @SerializedName("Activo")
    private Boolean activo;


    @SerializedName("Puesto")
    private Puesto puesto;

    @SerializedName("WorkCenter")
    private WorkCenter workCenter;

    @SerializedName("ParosAsignados")
    private List<Paro> parosAsignados;

    @SerializedName("DefectosAsignados")
    private List<Defecto> defectosAsignados;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public int getIdWorkCenter() {
        return idWorkCenter;
    }

    public void setIdWorkCenter(int idWorkCenter) {
        this.idWorkCenter = idWorkCenter;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public WorkCenter getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(WorkCenter workCenter) {
        this.workCenter = workCenter;
    }

    public List<Paro> getParosAsignados() {
        return parosAsignados;
    }

    public void setParosAsignados(List<Paro> parosAsignados) {
        this.parosAsignados = parosAsignados;
    }

    public List<Defecto> getDefectosAsignados() {
        return defectosAsignados;
    }

    public void setDefectosAsignados(List<Defecto> defectosAsignados) {
        this.defectosAsignados = defectosAsignados;
    }
}
