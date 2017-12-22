package com.pmi.ispmmx.maya.Interfaces;

import com.google.gson.JsonArray;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IDefectoService {

    @GET(ApisPreference.ApiDefecto + "/{id}")
    Call<Defecto> getDefecto(@Path("id") int id);

    @PUT(ApisPreference.ApiDefecto + "/{id}")
    Call<Defecto> putDefectoByActivo(@Path("id") int id, @Query("activo") boolean activo);

    @PUT(ApisPreference.ApiDefecto + "/{id}")
    Call<Defecto> putDefectoByResponsable(@Path("id") int id, @Query("idResponsable") int idResponsable);

    @PUT(ApisPreference.ApiDefecto + "/{id}")
    Call<Defecto> putDefectoBySAP(@Path("id") int id, @Query("SAP") String sap);

    @PUT(ApisPreference.ApiDefecto + "/{id}")
    Call<Defecto> putDefectoByFechaEstimada(@Path("id") int id, @Query("dia") int dia, @Query("mes") int mes, @Query("year") int year);

    @GET(ApisPreference.ApiDefecto)
    Call<List<Defecto>> getDefectosByWorkCenter(@Query("idWorkCenter") int idWorkCenter, @Query("activo") Boolean activo, @Query("diasDesde") int diasDesde, @Query("diasHasta") int diasHasta, @Query("cantidad") int cantidad);

    @GET(ApisPreference.ApiDefecto)
    Call<JsonArray> getDefectosByBussinesUnit(@Query("idBussinesUnit") int idBussinesUnit, @Query("activo") Boolean activo, @Query("diasDesde") int diasDesde, @Query("diasHasta") int diasHasta, @Query("cantidad") int cantidad);

    @GET(ApisPreference.ApiDefecto)
    Call<List<Defecto>> getDefectosByOrigen(@Query("idOrigen") int idOrigen, @Query("activo") Boolean activo, @Query("diasDesde") int diasDesde, @Query("diasHasta") int diasHasta, @Query("cantidad") int cantidad);

    @POST(ApisPreference.ApiDefecto)
    Call<Defecto> postDefecto(@Body Defecto defecto);

    @Multipart
    @POST("/")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);


}
