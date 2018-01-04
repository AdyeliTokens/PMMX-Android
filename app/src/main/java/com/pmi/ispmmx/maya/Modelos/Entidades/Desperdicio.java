package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;

import java.util.Date;



public class Desperdicio {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Cantidad")
    @Expose
    private int cantidad;

    @SerializedName("Fecha")
    @Expose
    private String fechaApi;

    private Date fecha;

    @SerializedName("IdPersona")
    @Expose
    private int idPersona;

    @SerializedName("IdSeccion")
    @Expose
    private int idSeccion;

    @SerializedName("IdWorkCenter")
    @Expose
    private int idWorkCenter;

    @SerializedName("IdMarca")
    @Expose
    private int idMarca;


    @SerializedName("Reportante")
    @Expose
    private Persona reportante;

    @SerializedName("Seccion")
    @Expose
    private ModuloSeccion moduloSeccion;

    @SerializedName("WorkCenter")
    @Expose
    private WorkCenter workCenter;

    @SerializedName("MarcaDelCigarrillo")
    @Expose
    private Marca marca;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        int idx1 = fechaApi.indexOf("(");
        int idx2 = fechaApi.indexOf(")") - 5;
        String s = fechaApi.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        fecha = new Date(l);
        return fecha;
    }


    public Date getFechaApi() {
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

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public int getIdWorkCenter() {
        return idWorkCenter;
    }

    public void setIdWorkCenter(int idWorkCenter) {
        this.idWorkCenter = idWorkCenter;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public Persona getReportante() {
        return reportante;
    }

    public void setReportante(Persona reportante) {
        this.reportante = reportante;
    }

    public ModuloSeccion getModuloSeccion() {
        return moduloSeccion;
    }

    public void setModuloSeccion(ModuloSeccion moduloSeccion) {
        this.moduloSeccion = moduloSeccion;
    }

    public WorkCenter getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(WorkCenter workCenter) {
        this.workCenter = workCenter;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
