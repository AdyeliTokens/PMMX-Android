package com.pmi.ispmmx.maya.Interfaces;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.BussinesUnit;
import com.pmi.ispmmx.maya.Utils.Config.ApisPreference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chan jacky chan on 01/11/2017.
 */

public interface IMecanicoPorBusinessUnitsService {

    @GET(ApisPreference.ApiMecanicoPorBusinessUnit)
    Call<BussinesUnit> getBusinessUnitByMecanico(@Query("idMecanico") int idMecanico);
}
