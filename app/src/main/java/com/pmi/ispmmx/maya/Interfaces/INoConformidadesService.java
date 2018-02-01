package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chan jacky chan on 08/12/2017.
 */

public interface INoConformidadesService {

    @GET(ApisPreference.ApiNoConformidades)
    Call<List<ModuloSeccion>> getVQIByWorkCenterYFecha(@Query("fecha") String fecha, @Query("IdWorkCenter") int idWorkCenter);

}
