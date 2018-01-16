package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ILineLeaderService {

    @GET(ApisPreference.ApiLineLeader)
    Call<RespuestaServicio<List<WorkCenter>>> getWorkCenters(@Query("id") int idShiftLeaders);
}
