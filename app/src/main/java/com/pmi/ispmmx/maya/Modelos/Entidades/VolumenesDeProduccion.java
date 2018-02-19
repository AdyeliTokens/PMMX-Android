package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Utils.FormatDate;

import java.util.Date;

/**
 * Created by chan jacky chan on 16/01/2018.
 */

public class VolumenesDeProduccion {


    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Cantidad")
    @Expose
    private double cantidad;

    @SerializedName("Fecha")
    @Expose
    private String fechaApi;

    private Date fecha;

    @SerializedName("IdPersona")
    @Expose
    private int idPersona;

    @SerializedName("IdWorkCenter")
    @Expose
    private int idWorkCenter;

    @SerializedName("IdMarca")
    @Expose
    private int idMarca;


    @SerializedName("Reportante")
    @Expose
    private Persona reportante;

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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        FormatDate format = new FormatDate();
        fecha = format.fromISO8601UTC(fechaApi);

        return fecha;
    }


    public String getFechaApi() {

        return fechaApi;
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
