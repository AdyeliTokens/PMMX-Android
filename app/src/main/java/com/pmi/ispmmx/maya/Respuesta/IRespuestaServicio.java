package com.pmi.ispmmx.maya.Respuesta;

public interface IRespuestaServicio<T> {
    boolean getEjecucionCorrecta();

    String getMensaje();

    void setMensaje(String value);

    T getRespuesta();

    void setRespuesta(T value);

}
