package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface IPesadorService {

    @GET(ApisPreference.ApiPesador + "/{id}")
    Call<List<WorkCenter>> getWorkCenters(@Path("id") int id);
}
