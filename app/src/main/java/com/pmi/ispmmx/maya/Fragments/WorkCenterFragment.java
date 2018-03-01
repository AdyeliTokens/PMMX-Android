package com.pmi.ispmmx.maya.Fragments;


import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.CardViews.WorkCenterAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class WorkCenterFragment extends Fragment {

    public List<WorkCenter> workCenterList;
    public RecyclerView.Adapter mAdapter;
    private View view;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Animator spruceAnimator;


    private OnInteractionListener mListener;

    public static WorkCenterFragment newInstance() {
        return new WorkCenterFragment();
    }

    public WorkCenterFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            mListener = (OnInteractionListener) context;
        } else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workcenter, container, false);
        elementosUI();
        iniciarRecycle();
        inicio();
        return view;
    }

    private void inicio() {

        mListener.getWorkCenters();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //if (spruceAnimator != null) {
         //   spruceAnimator.start();
        //    mListener.refreshWorkCenters();
        //}


    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///             Finciones de Inicio del View                //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    private void elementosUI() {

        mRecycleView = view.findViewById(R.id.rv_workcenters);
        swipeRefreshLayout = view.findViewById(R.id.sw_workcenters);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListener.refreshWorkCenters();
            }
        });

    }



    private void llenarWorkCenters() {
        if (mListener != null) {
            mListener.getWorkCenters();
        } else {

        }
    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///               Iniciarlizar RV con adapter               //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public void iniciarAdapter() {

        mAdapter = new WorkCenterAdapter(workCenterList, R.layout.cardview_workcenter, new WorkCenterAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(WorkCenter workCenter, int position) {
                mListener.onClickWorkCenter(workCenter);
            }

            @Override
            public void OnOrigenClick(Origen origen, int position) {
                mListener.onClickOrigen(origen);
            }

            @Override
            public void OnImageOrigenClick(Origen origen, int position) {
                mListener.onClickImageOrigen(origen);
            }

            @Override
            public boolean OnOrigenLongClick(Origen origen, int position) {
                mListener.onLongClickOrigen(origen);
                return true;
            }

            @Override
            public void OnBadgeDefectoClick(Origen origen, int position) {
                mListener.onBadgeDefectoClick(origen, position);
            }

            @Override
            public void OnBadgeParoClick(Origen origen, int position) {
                mListener.onBadgeParoClick(origen, position);
            }
        });

        mAdapter.notifyDataSetChanged();

    }

    private void iniciarRecycle() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView.setHasFixedSize(false);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setAdapter(mAdapter);

    }

    public void llenarInformacion(WorkCenter workCenter) {

        workCenterList.clear();
        workCenterList.add(workCenter);
        mAdapter.notifyDataSetChanged();
        CerrarSwiper();
    }

    public void CerrarSwiper() {


        swipeRefreshLayout.setRefreshing(false);
    }


    public interface OnInteractionListener {
        void refreshWorkCenters();

        void getWorkCenters();

        boolean onLongClickOrigen(Origen origen);

        void onBadgeDefectoClick(Origen origen, int position);

        void onBadgeParoClick(Origen origen, int position);

        void onClickImageOrigen(Origen origen);

        void onClickOrigen(Origen origen);

        void onClickWorkCenter(WorkCenter workCenter);

        void hideViews();

        void showViews();
    }


}
