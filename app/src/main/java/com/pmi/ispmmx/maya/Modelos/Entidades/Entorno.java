package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ispmmx on 7/27/17.
 */

public class Entorno {
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Nombre")
    private String nombre;
    @SerializedName("Activo")
    private Boolean activo;

    private List<User> usuarioList;

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

    public List<User> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<User> usuarioList) {
        this.usuarioList = usuarioList;
    }
}
