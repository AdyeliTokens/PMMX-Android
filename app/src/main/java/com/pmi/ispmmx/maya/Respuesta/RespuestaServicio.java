package com.pmi.ispmmx.maya.Respuesta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaServicio<T> implements IRespuestaServicio<T> {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("EjecucionCorrecta")
    @Expose
    private boolean ejecucionCorrecta;

    @SerializedName("Respuesta")
    @Expose
    private T respuesta;

    public RespuestaServicio() {
        this.mensaje = "";
        this.ejecucionCorrecta = true;
    }

    public boolean getEjecucionCorrecta() {
        if (mensaje.length() > 0) {
            return false;
        }
        return this.ejecucionCorrecta;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String value) {
        if (value == null || value.length() == 0) {
            this.ejecucionCorrecta = true;
        } else {
            this.ejecucionCorrecta = false;
        }
        this.mensaje = value;
    }

    public T getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(T value) {
        respuesta = value;
    }

}
