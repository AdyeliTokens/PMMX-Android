package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chan jacky chan on 15/11/2017.
 */

public interface IMecanicoService {

    @GET(ApisPreference.ApiMecanico + "/{id}")
    Call<Persona> getMecanico(@Path("id") int id);
}
