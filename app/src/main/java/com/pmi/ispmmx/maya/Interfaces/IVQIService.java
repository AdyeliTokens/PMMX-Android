package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Modelos.Entidades.VQI;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IVQIService {
    @GET(ApisPreference.ApiVQI)
    Call<List<VQI>> getVQIByWorkCenter(@Query("idWorkCenter") int idWorkCenter);

    @GET(ApisPreference.ApiVQI)
    Call<List<VQI>> getVQIByWorkCenter(@Query("fecha") String fecha, @Query("idWorkCenter") int idWorkCenter);

}
