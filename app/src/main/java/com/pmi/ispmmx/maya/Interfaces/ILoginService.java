package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.User;
import com.pmi.ispmmx.maya.Modelos.LoginViewModel;
import com.pmi.ispmmx.maya.Utils.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ILoginService {

    @POST(ApisPreference.ApiLogin+ "/ExternalLogin")
    Call<RespuestaServicio<User>> postLogin(@Body LoginViewModel model);
}
