package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;

import java.util.Date;


public class NoConformidad {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Descripcion")
    @Expose
    private String descrcipcion;

    @SerializedName("Calificacion_Low")
    @Expose
    private int calificacion_low;

    @SerializedName("Calificacion_High")
    @Expose
    private int calificacion_high;

    @SerializedName("Calificacion_VQI")
    @Expose
    private int calificacion_vqi;

    @SerializedName("Calificacion_CSVQI")
    @Expose
    private int calificacion_csvqi;

    @SerializedName("Fecha")
    @Expose
    private String fechaApi;
    private Date fecha;

    @SerializedName("IdSeccion")
    @Expose
    private int idSeccion;

    @SerializedName("Seccion")
    @Expose
    private ModuloSeccion seccion;

    @SerializedName("IdPersona")
    @Expose
    private int idPersona;

    @SerializedName("IdWorkCenter")
    @Expose
    private int idWorkCenter;

    @SerializedName("Persona")
    @Expose
    private Persona persona;

    @SerializedName("WorkCenter")
    @Expose
    private WorkCenter workCenter;





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrcipcion() {
        return descrcipcion;
    }

    public void setDescrcipcion(String descrcipcion) {
        this.descrcipcion = descrcipcion;
    }

    public int getCalificacion_low() {
        return calificacion_low;
    }

    public void setCalificacion_low(int calificacion_low) {
        this.calificacion_low = calificacion_low;
    }

    public int getCalificacion_high() {
        return calificacion_high;
    }

    public void setCalificacion_high(int calificacion_high) {
        this.calificacion_high = calificacion_high;
    }

    public int getCalificacion_vqi() {
        return calificacion_vqi;
    }

    public void setCalificacion_vqi(int calificacion_vqi) {
        this.calificacion_vqi = calificacion_vqi;
    }

    public int getCalificacion_csvqi() {
        return calificacion_csvqi;
    }

    public void setCalificacion_csvqi(int calificacion_csvqi) {
        this.calificacion_csvqi = calificacion_csvqi;
    }

    public String getFechaApi() {
        return fechaApi;
    }

    public void setFechaApi(String fechaApi) {
        this.fechaApi = fechaApi;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdWorkCenter() {
        return idWorkCenter;
    }

    public void setIdWorkCenter(int idWorkCenter) {
        this.idWorkCenter = idWorkCenter;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public WorkCenter getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(WorkCenter workCenter) {
        this.workCenter = workCenter;
    }
}
