package com.pmi.ispmmx.maya.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.BusinessUnitAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.BussinesUnit;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class BusinessUnitFragment extends Fragment {
    public List<BussinesUnit> bussinesUnitList;
    public RecyclerView.Adapter mAdapter;
    private View view;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnInteractionListener mListener;

    public BusinessUnitFragment() {

    }

    public static BusinessUnitFragment newInstance(String param1, String param2) {
        BusinessUnitFragment fragment = new BusinessUnitFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business_unit, container, false);
        elementosUI();
        iniciarRecycle();
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            mListener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void elementosUI() {
        mRecycleView = view.findViewById(R.id.rv_business_units);

    }


    public void iniciarAdapter() {
        mAdapter = new BusinessUnitAdapter(bussinesUnitList, R.layout.cardview_business_unit, new BusinessUnitAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(BussinesUnit bussinesUnit, int position) {
                mListener.onClickBusinessUnit(bussinesUnit);
            }

            @Override
            public void OnWorkCenterClick(WorkCenter workCenter, int position) {
                mListener.onClickWorkCenter(workCenter);
            }

            @Override
            public boolean OnWorkCenterLongClick(WorkCenter workCenter, int position) {
                mListener.onLongClickWorkCenter(workCenter);
                return true;
            }

            @Override
            public void OnBadgeDefectoClick(WorkCenter workCenter, int position) {
                mListener.onBadgeDefectoClick(workCenter, position);
            }

            @Override
            public void OnBadgeParoClick(WorkCenter workCenter, int position) {
                mListener.onBadgeParoClick(workCenter, position);
            }


        });
        mAdapter.notifyDataSetChanged();

    }

    private void iniciarRecycle() {
        mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecycleView.setHasFixedSize(false);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setAdapter(mAdapter);
    }

    public interface OnInteractionListener {

        void onBadgeDefectoClick(WorkCenter workCenter, int position);

        void onBadgeParoClick(WorkCenter workCenter, int position);

        void onClickWorkCenter(WorkCenter workCenter);

        void onClickBusinessUnit(BussinesUnit bussinesUnit);

        boolean onLongClickWorkCenter(WorkCenter workCenter);
    }
}
