package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;

import java.util.Date;

/**
 * Created by chan jacky chan on 16/11/2017.
 */

public class Comentario {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdComentador")
    @Expose
    private int idComentador;

    @SerializedName("IdDefecto")
    @Expose
    private int idDefecto;

    @SerializedName("Opinion")
    @Expose
    private String opinion;

    @SerializedName("Fecha")
    @Expose
    private String fechaApi;
    private Date fecha;

    @SerializedName("Comentador")
    @Expose
    private Persona comentador;

    @SerializedName("Defecto")
    @Expose
    private Defecto defecto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComentador() {
        return idComentador;
    }

    public void setIdComentador(int idComentador) {
        this.idComentador = idComentador;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getFecha() {
        int idx1 = fechaApi.indexOf("(");
        int idx2 = fechaApi.indexOf(")") - 5;
        String s = fechaApi.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        fecha = new Date(l);
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Persona getComentador() {
        return comentador;
    }

    public void setComentador(Persona comentador) {
        this.comentador = comentador;
    }

    public Defecto getDefecto() {
        return defecto;
    }

    public void setDefecto(Defecto defecto) {
        this.defecto = defecto;
    }
}
