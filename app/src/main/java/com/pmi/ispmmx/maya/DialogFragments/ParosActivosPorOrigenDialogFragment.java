package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.Paros.ParoActivoAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class ParosActivosPorOrigenDialogFragment extends BottomSheetDialogFragment {
    public RecyclerView.Adapter adapparosactivos;
    public FloatingActionButton _fabAgregarFalla;
    private ParosActivosPorOrigenDialogFragment.OnInteractionListener mListener;
    private Context context;
    private List<Paro> paroList;
    private Origen origen;
    private View _view;
    private RecyclerView _rvParosActivos;
    private RecyclerView.LayoutManager _managerParosActivos;

    public static ParosActivosPorOrigenDialogFragment newInstance(Origen origen, List<Paro> paroList) {
        ParosActivosPorOrigenDialogFragment frag = new ParosActivosPorOrigenDialogFragment();
        frag.paroList = paroList;
        frag.origen = origen;
        return frag;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            this.context = context;
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


    private void elementosUI() {
        _rvParosActivos = _view.findViewById(R.id.rv_parosActivos);
        _fabAgregarFalla = _view.findViewById(R.id.fab_agregar_falla);
        _fabAgregarFalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAgregarFalla(origen);
            }
        });

    }

    public void iniciarAdapterAbirtos() {
        adapparosactivos = new ParoActivoAdapter(_view.getContext(), paroList, R.layout.cardview_paro_activo, new ParoActivoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Paro paro, int position) {
                mListener.onClickParo(paro);
            }

            @Override
            public boolean OnItemLongClick(Paro paro, int position) {

                return true;
            }

            @Override
            public void OnOpcionClick(Paro paro, int position, View view) {
                //PopupMenu popupMenu = new PopupMenu(getContext(), view);
                //popupMenu.inflate(R.menu.menu_scrolling);
                //popupMenu.show();
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


    }


    public interface OnInteractionListener {

        void onClickParo(Paro paro);

        void onClickAgregarFalla(Origen origen);


    }


}
