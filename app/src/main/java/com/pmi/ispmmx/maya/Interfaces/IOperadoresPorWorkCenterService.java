package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Utils.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chan jacky chan on 01/11/2017.
 */

public interface IOperadoresPorWorkCenterService {

    @GET(ApisPreference.ApiOperadoresPorWorkCenter)
    Call<RespuestaServicio<WorkCenter>> getModulosByWorkCenter(@Query("idOperador") int idOperador);

}
