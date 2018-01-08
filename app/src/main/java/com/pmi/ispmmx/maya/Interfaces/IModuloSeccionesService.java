package com.pmi.ispmmx.maya.Interfaces;


import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IModuloSeccionesService {

    @GET(ApisPreference.ApiModuloSecciones)
    Call<List<ModuloSeccion>> getModuloSeccionesApi();

}
