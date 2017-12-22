package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ispmmx on 7/27/17.
 */

public interface IPersonaService {

    @GET(ApisPreference.GET_PERSONA + "/{id}")
    Call<Persona> getPersonaApi(@Query("id") int id);
}
