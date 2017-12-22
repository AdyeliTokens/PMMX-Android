package com.pmi.ispmmx.maya.Interfaces;

import com.google.gson.JsonArray;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/31/17.
 */

public interface IOrigenService {

    @GET(ApisPreference.GET_ORIGEN + "/{id}")
    Call<Origen> getOrigen(@Query("id") int id);

    @GET(ApisPreference.GET_ORIGEN)
    Call<JsonArray> getModulosByWorkCenter(@Query("idWorkCenter") int idWorkCenter);

}
