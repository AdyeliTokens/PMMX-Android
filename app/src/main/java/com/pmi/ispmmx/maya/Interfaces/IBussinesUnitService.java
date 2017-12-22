package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.BussinesUnit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/24/17.
 */

public interface IBussinesUnitService {

    @GET("BussinesUnit")
    Call<List<BussinesUnit>> getBusinessUnits();


    @GET("BussinessUnit")
    Call<BussinesUnit> getBusinessUnit(@Query("id") String id);
}
