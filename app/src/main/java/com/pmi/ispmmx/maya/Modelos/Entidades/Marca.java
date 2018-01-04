package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Marca {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("Codigo")
    @Expose
    private String codigo;

    @SerializedName("Activo")
    @Expose
    private boolean activo;

    @SerializedName("Desperdicios")
    @Expose
    private List<Desperdicio> desperdicio;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Desperdicio> getDesperdicio() {
        return desperdicio;
    }

    public void setDesperdicio(List<Desperdicio> desperdicio) {
        this.desperdicio = desperdicio;
    }


}
