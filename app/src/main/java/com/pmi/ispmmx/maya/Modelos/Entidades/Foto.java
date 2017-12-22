package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chan jacky chan on 06/09/2017.
 */

public class Foto {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Path")
    @Expose
    private String path;

    @SerializedName("IdDefecto")
    @Expose
    private int idDefecto;

    @SerializedName("Nombre")
    @Expose
    private String nombre;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
