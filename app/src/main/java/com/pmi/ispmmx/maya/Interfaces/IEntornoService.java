package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Entorno;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/27/17.
 */

public interface IEntornoService {

    @GET("/{id}")
    Call<Entorno> getEntornoApi(@Query("id") int id);

}
