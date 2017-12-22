package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/24/17.
 */

public interface IModuloService {

    @GET("modulo")
    Call<User> getModulo(@Query("id") String id);


}
