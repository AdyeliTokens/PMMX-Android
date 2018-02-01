package com.pmi.ispmmx.maya.Interfaces;


import com.pmi.ispmmx.maya.Modelos.Entidades.Desperdicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IDesperdicioService {

    @GET(ApisPreference.ApiDesperdicios)
    Call<List<Desperdicio>> getDesperdiciosApi();

    @POST(ApisPreference.ApiDesperdicios)
    Call<Desperdicio> postDesperdicio(@Body Desperdicio desperdicio);
}
