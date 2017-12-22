package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Puesto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/27/17.
 */

public interface IPuestoService {

    @GET("/{id}")
    Call<Puesto> getPuestoApi(@Query("id") int id);
}
