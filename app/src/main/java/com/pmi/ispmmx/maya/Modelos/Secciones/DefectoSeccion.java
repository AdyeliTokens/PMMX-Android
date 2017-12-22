package com.pmi.ispmmx.maya.Modelos.Secciones;

import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;

import java.util.List;

/**
 * Created by ispmmx on 8/22/17.
 */

public class DefectoSeccion {

    private int pocision;
    private String titulo;
    private List<Defecto> elementos;
    private boolean error;
    private String mensajeError;
    private boolean wait;

    public DefectoSeccion() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Defecto> getElementos() {
        return elementos;
    }

    public void setElementos(List<Defecto> elementos) {
        this.elementos = elementos;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public int getPocision() {
        return pocision;
    }

    public void setPocision(int pocision) {
        this.pocision = pocision;
    }
}
