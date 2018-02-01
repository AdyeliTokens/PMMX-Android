package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.Paros.ParoActivoAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;

import java.util.List;

/**
 * Created by chan jacky chan on 14/11/2017.
 */

public class ParosActivosPorWorkCenterDialogFragment extends BottomSheetDialogFragment {
    public RecyclerView.Adapter adapparosactivos;
    private OnInteractionListener mListener;
    private List<Paro> paroList;
    private WorkCenter workCenter;
    private View _view;
    private RecyclerView _rvParosActivos;
    private RecyclerView.LayoutManager _managerParosActivos;

    public static ParosActivosPorWorkCenterDialogFragment newInstance(WorkCenter workCenter, List<Paro> paroList) {
        ParosActivosPorWorkCenterDialogFragment frag = new ParosActivosPorWorkCenterDialogFragment();
        frag.paroList = paroList;
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
        _view = inflater.inflate(R.layout.bottom_sheet_paros_activos, container, false);

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
        _rvParosActivos = _view.findViewById(R.id.rv_parosActivos);

    }

    public void iniciarAdapterAbirtos() {
        adapparosactivos = new ParoActivoAdapter(_view.getContext(), paroList, R.layout.cardview_paro_activo, new ParoActivoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Paro paro, int position) {
                mListener.onClickParo(paro);
                //Toast.makeText(getContext(), "que pex", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean OnItemLongClick(Paro paro, int position) {
                return true;

            }

            @Override
            public void OnOpcionClick(Paro paro, int position, View view) {

            }
        });

    }

    private void iniciarRecycleActivos() {
        _managerParosActivos = new LinearLayoutManager(_view.getContext());
        _rvParosActivos.setHasFixedSize(false);
        _rvParosActivos.setItemAnimator(new DefaultItemAnimator());
        _rvParosActivos.setLayoutManager(_managerParosActivos);
        _rvParosActivos.setNestedScrollingEnabled(false);
        _rvParosActivos.setAdapter(adapparosactivos);
        _rvParosActivos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

    }


    public interface OnInteractionListener {

        void onClickParo(Paro paro);

    }


}
