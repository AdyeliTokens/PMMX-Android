package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Utils.FormatDate;

import java.util.Date;


public class Desperdicio {

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

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        FormatDate format = new FormatDate();
        fecha = format.fromISO8601UTC(fechaApi);

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

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public int getIdWorkCenter() {
        return idWorkCenter;
    }

    public void setIdWorkCenter(int idWorkCenter) {
        this.idWorkCenter = idWorkCenter;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
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
