package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.CRR;
import com.pmi.ispmmx.maya.Utils.Respuesta.RespuestaServicio;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ICRRService {
    @GET(ApisPreference.ApiCRR)
    Call<RespuestaServicio<List<CRR>>> getCRRByWorkCenter(@Query("idWorkCenter") int idWorkCenter);

    @GET(ApisPreference.ApiCRR)
    Call<RespuestaServicio<List<CRR>>> getCRRByWorkCenter(@Query("fecha") String fecha, @Query("idWorkCenter") int idWorkCenter);
}
