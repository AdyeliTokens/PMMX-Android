package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/24/17.
 */

public interface IWorkCenterService {

    @GET(ApisPreference.GET_WORKCENTER + "/{id}")
    Call<WorkCenter> getWorkCenter(@Query("id") int id);

}
