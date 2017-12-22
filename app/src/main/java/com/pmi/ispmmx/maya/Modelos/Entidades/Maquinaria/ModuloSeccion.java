package com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.NoConformidad;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;

import java.util.List;


public class ModuloSeccion {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("Activo")
    @Expose
    private Boolean activo;

    @SerializedName("Modulos")
    @Expose
    private List<Modulo> modulos;

    @SerializedName("Mecanicos")
    @Expose
    private List<Persona> mecanicos;

    @SerializedName("Operadores")
    @Expose
    private List<Persona> operadores;

    @SerializedName("NoConformidades")
    @Expose
    private List<NoConformidad> noconformidad;




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

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public List<Persona> getMecanicos() {
        return mecanicos;
    }

    public void setMecanicos(List<Persona> mecanicos) {
        this.mecanicos = mecanicos;
    }

    public List<Persona> getOperadores() {
        return operadores;
    }

    public void setOperadores(List<Persona> operadores) {
        this.operadores = operadores;
    }

    public List<NoConformidad> getNoconformidad() {
        return noconformidad;
    }

    public void setNoconformidad(List<NoConformidad> noconformidad) {
        this.noconformidad = noconformidad;
    }


}
