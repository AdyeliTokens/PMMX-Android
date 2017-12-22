package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.User;
import com.pmi.ispmmx.maya.Modelos.LoginViewModel;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by chan jacky chan on 03/09/2017.
 */

public interface ILoginService {

    @POST(ApisPreference.ApiLogin)
    Call<User> postLogin(@Body LoginViewModel model);
}
