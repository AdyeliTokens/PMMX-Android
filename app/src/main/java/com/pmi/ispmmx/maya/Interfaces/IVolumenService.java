package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.CRR;
import com.pmi.ispmmx.maya.Modelos.Entidades.PlanAttainment;
import com.pmi.ispmmx.maya.Modelos.Entidades.VolumenesDeProduccion;
import com.pmi.ispmmx.maya.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface IVolumenService {
    @GET(ApisPreference.ApiVolumenesDeProduccion)
    Call<RespuestaServicio<List<PlanAttainment>>> getPlanByWorkCenter(@Query("IdWorkCenter") int idWorkCenter);


    @POST(ApisPreference.ApiVolumenesDeProduccion)
    Call<RespuestaServicio<VolumenesDeProduccion>> postVolumen(@Body VolumenesDeProduccion volumen);

    @GET(ApisPreference.ApiVolumenesDeProduccion)
    Call<List<PlanAttainment>> getPlanAttainmentByWorkCenter(@Query("fecha") String fecha, @Query("idWorkCenter") int idWorkCenter);
}
