package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.Defectos.DefectoAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.R;

import java.util.List;

/**
 * Created by chan jacky chan on 14/11/2017.
 */

public class DefectosActivosPorWorkCenterDialogFragment extends BottomSheetDialogFragment {
    public RecyclerView.Adapter mAdapter;
    private OnInteractionListener mListener;
    private List<Defecto> defectoList;
    private WorkCenter workCenter;
    private View _view;
    private RecyclerView _rvDefectosActivos;
    private RecyclerView.LayoutManager _managerDefectosActivos;
    private FloatingActionButton _fabAgregarDefecto;


    public static DefectosActivosPorWorkCenterDialogFragment newInstance(WorkCenter workCenter, List<Defecto> defectoList) {
        DefectosActivosPorWorkCenterDialogFragment frag = new DefectosActivosPorWorkCenterDialogFragment();
        frag.defectoList = defectoList;
        frag.workCenter = workCenter;
        return frag;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        _view = inflater.inflate(R.layout.bottom_sheet_defectos_activos, container, false);

        elementosUI();
        iniciarAdapterAbirtos();
        iniciarRecycleActivos();

        return _view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void elementosUI() {
        _rvDefectosActivos = _view.findViewById(R.id.rv_defectosActivos);
        _fabAgregarDefecto = _view.findViewById(R.id.fab_agregar_texto);

    }

    public void iniciarAdapterAbirtos() {
        mAdapter = new DefectoAdapter(defectoList, R.layout.cardview_defecto, new DefectoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Defecto defecto, int position, View foto) {
                mListener.onClickDefecto(defecto, position, foto);
            }
        });
    }

    private void iniciarRecycleActivos() {
        //_managerDefectosActivos = new LinearLayoutManager(_view.getContext());

        _managerDefectosActivos = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        _rvDefectosActivos.setHasFixedSize(false);
        _rvDefectosActivos.setItemAnimator(new DefaultItemAnimator());
        _rvDefectosActivos.setLayoutManager(_managerDefectosActivos);
        _rvDefectosActivos.setNestedScrollingEnabled(false);
        _rvDefectosActivos.setAdapter(mAdapter);


    }


    public interface OnInteractionListener {

        void onClickDefecto(Defecto defecto, int position, View foto);

    }


}

