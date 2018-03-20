package com.pmi.ispmmx.maya.Interfaces;

import com.google.gson.JsonArray;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;
import com.pmi.ispmmx.maya.Utils.Respuesta.RespuestaServicio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/28/17.
 */

public interface IParoService {

    @GET(ApisPreference.GET_PARO + "/{id}")
    Call<Paro> getParo(@Query("id") int id);

    @GET(ApisPreference.GET_PARO)
    Call<List<Paro>> getParosByWorkCenter(@Query("idWorkCenter") int idWorkCenter, @Query("activo") Boolean activo, @Query("cantidad") int cantidad);

    @GET(ApisPreference.GET_PARO)
    Call<RespuestaServicio<List<Paro>>> getParosByOrigen(@Query("idOrigen") int idOrigen);

    @GET(ApisPreference.GET_PARO)
    Call<JsonArray> getParosByBussinesUnit(@Query("idBussinesUnit") int idBussinesUnit, @Query("activo") Boolean activo, @Query("cantidad") int cantidad);

    //@FormUrlEncoded
    @POST(ApisPreference.GET_PARO)
    Call<RespuestaServicio<Paro>> postParo(@Body Paro paro);

    @PUT(ApisPreference.GET_PARO + "/{id}/")
    Call<Paro> putParo(@Path("id") int idParo, @Body Paro paro);

    @PUT(ApisPreference.GET_PARO + "/{id}")
    Call<Paro> putAsignarParo(@Path("id") int idParo, @Query("idMecanico") int idMecanico);

    @PUT(ApisPreference.GET_PARO + "/{id}")
    Call<Paro> putMotivoParo(@Path("id") int idParo, @Query("idMecanico") int idMecanico, @Query("motivo") String motivo);

    @PUT(ApisPreference.GET_PARO + "/{id}")
    Call<Paro> putCambiarActivo(@Path("id") int idParo, @Query("idPersona") int idPersona, @Query("activo") Boolean activo);


}