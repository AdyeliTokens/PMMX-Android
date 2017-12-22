package com.pmi.ispmmx.maya.Interfaces;


import com.pmi.ispmmx.maya.Modelos.Entidades.User;
import com.pmi.ispmmx.maya.Modelos.LoginViewModel;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUserService {

    @GET(ApisPreference.GET_USER + "/{id}")
    Call<User> getUserById(@Query("id") String id);

    @GET(ApisPreference.GET_USER)
    Call<User> getUser(@Query("Email") String email, @Query("Password") String password);

    @POST(ApisPreference.GET_USER)
    Call<User> getUser(@Body LoginViewModel login);

}


