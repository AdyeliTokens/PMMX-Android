package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Area;

import java.util.List;

/**
 * Created by chan jacky chan on 28/11/2017.
 */

public class Indicador {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("Activo")
    @Expose
    private Boolean activo;

    @SerializedName("Calificaciones")
    @Expose
    private List<Calificacion> calificaciones;

    @SerializedName("Areas")
    @Expose
    private List<Area> areas;

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
