package com.pmi.ispmmx.maya.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.pmi.ispmmx.maya.Adapters.Paros.ActividadesEnParoAdapter;
import com.pmi.ispmmx.maya.Interfaces.IParoService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.ActividadEnParo;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActividadEnParoFragment extends Fragment {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private RecyclerView mRecyclerView;
    private ActividadesEnParoAdapter mActividadesEnParoAdapter;
    private View view;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ActividadEnParoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_actividad_en_paro, container, false);
        elementosUI();
        //getActividades(2);
        /*List<ActividadEnParo> actividades = new ArrayList<ActividadEnParo>();
        actividades.add(1 , new ActividadEnParo());
        actividades.add(2 , new ActividadEnParo());
        llenarActividades(actividades);*/

        return view;
    }


    private void elementosUI() {
        mSwipeRefreshLayout = view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }

    private void getActividades(int idParo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostPreference.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IParoService paroService = retrofit.create(IParoService.class);
        Call<JsonArray> listParoCall = paroService.getParosByOrigen(idParo);
        listParoCall.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    List<ActividadEnParo> actividades = new ArrayList<ActividadEnParo>();
                    actividades.add(1, new ActividadEnParo());
                    actividades.add(2, new ActividadEnParo());
                    llenarActividades(actividades);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    private void llenarActividades(List<ActividadEnParo> actividades) {
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new ActividadesEnParoAdapter(actividades, R.layout.item_timeline, new ActividadesEnParoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ActividadEnParo actividad, int position) {

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //if (FAB_Status) {
                //  hideFAB();
                //FAB_Status = false;
                //}
                return false;
            }
        });

    }


}
