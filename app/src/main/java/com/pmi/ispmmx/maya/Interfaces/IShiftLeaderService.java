package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IShiftLeaderService {

    @GET(ApisPreference.ApiShiftLeader)
    Call<List<WorkCenter>> getCelularByShiftLeader(@Query("idShiftLeaders") int idShiftLeaders);

}
