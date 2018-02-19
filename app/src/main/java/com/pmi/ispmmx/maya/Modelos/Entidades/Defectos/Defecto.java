package com.pmi.ispmmx.maya.Modelos.Entidades.Defectos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Foto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;
import com.pmi.ispmmx.maya.Utils.FormatDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ispmmx on 7/19/17.
 */

public class Defecto {


    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("IdReportador")
    private int idReportador;

    @SerializedName("IdOrigen")
    private int idOrigen;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("Activo")
    private Boolean activo;

    private Date fechaReporte;

    private Date fechaEstimada;

    @SerializedName("FechaReporte")
    private String fechaApiReporte;

    @SerializedName("FechaEstimada")
    private String fechaApiEstimada;

    @SerializedName("Reportador")
    private Persona reportador;

    @SerializedName("Origen")
    private Origen origen;

    @SerializedName("Fotos")
    private List<Foto> fotos;

    @SerializedName("Actividades")
    private List<ActividadEnDefecto> actividades;

    @SerializedName("Prioridad")
    private int prioridad;

    @SerializedName("ComentariosCount")
    private int comentariosCount;

    @SerializedName("ActividadesCount")
    private int actividadesCount;

    @SerializedName("NotoficacionSAP")
    private int notificacionSap;

    @SerializedName("IdResponsable")
    private int idResponsable;

    @SerializedName("Responsable")
    private Persona responsable;

    public static Persona parseReportadorJson(String response) {
        Gson gson = new GsonBuilder().create();
        Persona persona = gson.fromJson(response, Persona.class);
        return persona;
    }

    public Date getFechaApiReporte() {
        FormatDate format = new FormatDate();
        fechaReporte = format.fromISO8601UTC(fechaApiReporte);

        return fechaReporte;
    }

    public void setFechaApiReporte(String fechaApiReporte) {
        this.fechaApiReporte = fechaApiReporte;
    }

    public String getFechaApiEstimada() {
        return fechaApiEstimada;
    }

    public void setFechaApiEstimada(String fechaApiEstimada) {
        this.fechaApiEstimada = fechaApiEstimada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReportador() {
        return idReportador;
    }

    public void setIdReportador(int idReportador) {
        this.idReportador = idReportador;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
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

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Date getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(Date fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public Persona getReportador() {
        return reportador;
    }

    public void setReportador(Persona reportador) {
        this.reportador = reportador;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public List<ActividadEnDefecto> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadEnDefecto> actividades) {
        this.actividades = actividades;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getComentariosCount() {
        return comentariosCount;
    }

    public void setComentariosCount(int comentariosCount) {
        this.comentariosCount = comentariosCount;
    }

    public int getActividadesCount() {
        return actividadesCount;
    }

    public void setActividadesCount(int actividadesCount) {
        this.actividadesCount = actividadesCount;
    }

    public int getNotificacionSap() {
        return notificacionSap;
    }

    public void setNotificacionSap(int notificacionSap) {
        this.notificacionSap = notificacionSap;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public Persona getResponsable() {
        return responsable;
    }

    public void setResponsable(Persona responsable) {
        this.responsable = responsable;
    }
}
