package com.pmi.ispmmx.maya.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.LinkUpAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.BussinesUnit;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class AreaFragment extends Fragment {

    public List<WorkCenter> workCenters;
    public RecyclerView.Adapter mAdapter;
    private View view;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnInteractionListener mListener;

    public AreaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work_center, container, false);
        elementosUI();
        iniciarRecycle();

        mListener.getWorkCenters();
        return view;
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
        mRecycleView = view.findViewById(R.id.rv_work_center);

    }

    public void iniciarAdapter() {
        mAdapter = new LinkUpAdapter(workCenters, R.layout.cardview_link_up, new LinkUpAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(WorkCenter workCenter, int position) {

            }

            @Override
            public boolean OnLongClick(WorkCenter workCenter, int position) {

                return true;
            }

            @Override
            public void OnBadgeDefectoClick(WorkCenter workCenter, int position) {

            }

            @Override
            public void OnBadgeParoClick(WorkCenter workCenter, int position) {

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

        void getWorkCenters();

        void onBadgeDefectoClick(WorkCenter workCenter, int position);

        void onBadgeParoClick(WorkCenter workCenter, int position);

        void onClickWorkCenter(WorkCenter workCenter);

        void onClickBusinessUnit(BussinesUnit bussinesUnit);

        boolean onLongClickWorkCenter(WorkCenter workCenter);
    }
}

