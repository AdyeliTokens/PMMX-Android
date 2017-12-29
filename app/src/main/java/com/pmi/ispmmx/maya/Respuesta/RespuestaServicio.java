package com.pmi.ispmmx.maya.Respuesta;

public class RespuestaServicio<T> implements IRespuestaServicio<T> {
    private String mensaje;
    private boolean ejecucionCorrecta;
    private T respuesta;

    public RespuestaServicio() {
        this.mensaje = "";
        this.ejecucionCorrecta = true;
    }

    public boolean getEjecucionCorrecta() {
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
