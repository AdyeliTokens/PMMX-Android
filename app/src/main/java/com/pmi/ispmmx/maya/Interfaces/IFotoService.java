package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;



public interface IFotoService {

    @Multipart
    @POST(ApisPreference.ApiFoto)
    Call<ResponseBody> postFotoDefecto(@Part MultipartBody.Part image, @Query("IdDefecto") int IdDefecto);

    @Multipart
    @POST(ApisPreference.ApiFoto)
    Call<ResponseBody> postFotoOrigen(@Part MultipartBody.Part image, @Query("IdOrigen") int IdOrigen);

    @Multipart
    @POST(ApisPreference.ApiFoto)
    Call<ResponseBody> postFotoPersona(@Part MultipartBody.Part image, @Query("IdPersona") int IdPersona);
}
