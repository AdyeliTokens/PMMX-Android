package com.pmi.ispmmx.maya.Modelos.Entidades.Paros;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.BussinesUnit;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;

import java.util.Date;
import java.util.List;

/**
 * Created by ispmmx on 7/19/17.
 */

public class Paro {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdOrigen")
    @Expose
    private int idOrigen;

    @SerializedName("IdReportador")
    @Expose
    private int idReportador;

    @SerializedName("IdMecanico")
    @Expose
    private int idMecanico;

    @SerializedName("Mecanico")
    @Expose
    private Persona mecanico;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;
    @SerializedName("Activo")
    @Expose
    private Boolean activo;
    private Date fechaReporte;
    @SerializedName("FechaReporte")
    @Expose
    private String fechaApiReporte;
    @SerializedName("Motivo")
    @Expose
    private String motivo;
    @SerializedName("Reportador")
    @Expose
    private Persona reportador;
    @SerializedName("BussinesUnit")
    @Expose
    private BussinesUnit bussinesUnit;
    @SerializedName("Origen")
    @Expose
    private Origen origen;
    @SerializedName("ActividadesEnParo")
    @Expose
    private List<ActividadEnParo> actividadesList;
    @SerializedName("TiempoDeParos")
    @Expose
    private List<TiempoDeParo> tiempoDeParos;

    public static Persona parseReportadorJson(String response) {
        Gson gson = new GsonBuilder().create();
        Persona persona = gson.fromJson(response, Persona.class);
        return persona;
    }

    public Persona getMecanico() {
        return mecanico;
    }

    public void setMecanico(Persona mecanico) {
        this.mecanico = mecanico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public int getIdReportador() {
        return idReportador;
    }

    public void setIdReportador(int idReportador) {
        this.idReportador = idReportador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Persona getReportador() {
        return reportador;
    }

    public void setReportador(Persona reportador) {
        this.reportador = reportador;
    }

    public BussinesUnit getBussinesUnit() {
        return bussinesUnit;
    }

    public void setBussinesUnit(BussinesUnit bussinesUnit) {
        this.bussinesUnit = bussinesUnit;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public List<ActividadEnParo> getActividadesList() {
        return actividadesList;
    }

    public void setActividadesList(List<ActividadEnParo> actividadesList) {
        this.actividadesList = actividadesList;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Date getFechaApiReporte() {
        int idx1 = fechaApiReporte.indexOf("(");
        int idx2 = fechaApiReporte.indexOf(")") - 5;
        String s = fechaApiReporte.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        fechaReporte = new Date(l);

        return fechaReporte;

    }

    public void setFechaApiReporte(String fechaApiReporte) {

        this.fechaApiReporte = fechaApiReporte;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
    }

    public List<TiempoDeParo> getTiempoDeParos() {
        return tiempoDeParos;
    }

    public void setTiempoDeParos(List<TiempoDeParo> tiempoDeParos) {
        this.tiempoDeParos = tiempoDeParos;
    }


}
