package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Comentario;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by chan jacky chan on 16/11/2017.
 */

public interface IComentariosService {

    @GET(ApisPreference.GET_PARO)
    Call<List<Comentario>> getComentarios();

    @GET(ApisPreference.ApiComentarios)
    Call<List<Comentario>> getComentariosByDefecto(@Query("idDefecto") int idDefecto);

    @POST(ApisPreference.ApiComentarios)
    Call<List<Comentario>> postComentario(@Body Comentario comentario);

}
