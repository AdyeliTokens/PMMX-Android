package com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria;

import com.google.gson.annotations.SerializedName;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;

import java.util.List;

/**
 * Created by ispmmx on 7/31/17.
 */

public class Origen {
    @SerializedName("Id")
    private int id;

    @SerializedName("IdModulo")
    private int idModulo;

    @SerializedName("IdWorkCenter")
    private int idWorkCenter;

    @SerializedName("Modulo")
    private Modulo modulo;

    @SerializedName("WorkCenter")
    private WorkCenter workCenter;

    @SerializedName("Paros")
    private List<Paro> paroList;

    @SerializedName("Orden")
    private int orden;

    @SerializedName("Defectos")
    private List<Defecto> defectoList;

    @SerializedName("ParosActivos")
    private int parosActivos;

    @SerializedName("DefectosActivos")
    private int DefectosActivos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public int getIdWorkCenter() {
        return idWorkCenter;
    }

    public void setIdWorkCenter(int idWorkCenter) {
        this.idWorkCenter = idWorkCenter;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public WorkCenter getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(WorkCenter workCenter) {
        this.workCenter = workCenter;
    }

    public List<Paro> getParoList() {
        return paroList;
    }

    public void setParoList(List<Paro> paroList) {
        this.paroList = paroList;
    }

    public List<Defecto> getDefectoList() {
        return defectoList;
    }

    public void setDefectoList(List<Defecto> defectoList) {
        this.defectoList = defectoList;
    }

    public int getParosActivos() {
        return parosActivos;
    }

    public void setParosActivos(int parosActivos) {
        this.parosActivos = parosActivos;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getDefectosActivos() {
        return DefectosActivos;
    }

    public void setDefectosActivos(int defectosActivos) {
        DefectosActivos = defectosActivos;
    }
}
