package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.VolumenesDeProduccion;
import com.pmi.ispmmx.maya.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface IVolumenService {
    @POST(ApisPreference.ApiVolumenesDeProduccion)
    Call<RespuestaServicio<VolumenesDeProduccion>> postVolumen(@Body VolumenesDeProduccion volumen);
}
