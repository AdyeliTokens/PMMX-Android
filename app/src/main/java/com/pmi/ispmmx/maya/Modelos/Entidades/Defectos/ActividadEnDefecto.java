package com.pmi.ispmmx.maya.Modelos.Entidades.Defectos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;

import java.util.Date;

/**
 * Created by ispmmx on 7/31/17.
 */

public class ActividadEnDefecto {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdDefecto")
    private int idDefecto;

    @SerializedName("IdEjecutante")
    private int idEjecutante;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("Fecha")
    private Date fecha;

    @SerializedName("Defecto")
    private Defecto defecto;

    @SerializedName("Ejecutante")
    private Persona ejecutante;
}
