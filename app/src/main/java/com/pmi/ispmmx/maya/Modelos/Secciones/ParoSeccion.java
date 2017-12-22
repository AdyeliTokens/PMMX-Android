package com.pmi.ispmmx.maya.Modelos.Secciones;

import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;

import java.util.List;

/**
 * Created by ispmmx on 8/22/17.
 */

public class ParoSeccion {

    private String titulo;
    private List<Paro> elementos;

    public ParoSeccion() {
    }

    public ParoSeccion(String titulo, List<Paro> elementos) {
        this.titulo = titulo;
        this.elementos = elementos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Paro> getElementos() {
        return elementos;
    }

    public void setElementos(List<Paro> elementos) {
        this.elementos = elementos;
    }


}
