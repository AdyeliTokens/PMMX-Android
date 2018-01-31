package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.VQI;
import com.pmi.ispmmx.maya.Modelos.Infopdate;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chan jacky chan on 29/01/2018.
 */

public interface IMAYAService {

    @GET(HostPreference.URL_INFO_UPDATE)
    Call<Infopdate> getInfo();
}
