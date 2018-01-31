package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Feed;
import com.pmi.ispmmx.maya.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chan jacky chan on 22/01/2018.
 */

public interface IFeedService {

    @GET(ApisPreference.ApiFeed)
    Call<RespuestaServicio<List<Feed>>> getFeedPorWorkCenter(@Query("idWorkCenter") int idWorkCenter);
}
