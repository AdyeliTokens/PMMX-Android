package com.pmi.ispmmx.maya.Modelos.Entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ispmmx on 7/21/17.
 */

public class User {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("IdPersona")
    private int idPersona;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password;

    @SerializedName("Activo")
    private Boolean activo;

    @SerializedName("Persona")
    private Persona persona;


    @SerializedName("Entornos")
    private List<Entorno> entornos;


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Entorno> getEntornos() {
        return entornos;
    }

}
