package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Infopdate;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chan jacky chan on 29/01/2018.
 */

public interface IMAYAService {

    @GET(HostPreference.URL_INFO_UPDATE)
    Call<Infopdate> getInfo();
}
