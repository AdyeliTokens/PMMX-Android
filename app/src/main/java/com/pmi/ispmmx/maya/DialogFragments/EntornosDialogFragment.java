package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.EntornoAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Entorno;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class EntornosDialogFragment extends BottomSheetDialogFragment {

    private View _view;
    private Listener mListener;
    private List<Entorno> entornoList;

    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton _fabAgregarEntorno;
    private RecyclerView _rvListaEntornos;
    private RecyclerView.LayoutManager _mLayout;

    public static EntornosDialogFragment newInstance(List<Entorno> entornoList) {
        final EntornosDialogFragment fragment = new EntornosDialogFragment();
        fragment.entornoList = entornoList;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.bottom_sheet_entornos, container, false);
        return _view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        elementosUI();
        iniciarAdapter();
        iniciarRecycle();
    }

    private void elementosUI() {
        _rvListaEntornos = _view.findViewById(R.id.list_entornos);


    }

    private void iniciarRecycle() {

        _mLayout = new LinearLayoutManager(_view.getContext());
        _rvListaEntornos.setHasFixedSize(false);
        _rvListaEntornos.setItemAnimator(new DefaultItemAnimator());
        _rvListaEntornos.setLayoutManager(_mLayout);
        _rvListaEntornos.setNestedScrollingEnabled(false);
        _rvListaEntornos.setAdapter(mAdapter);
    }

    private void iniciarAdapter() {
        mAdapter = new EntornoAdapter(entornoList, R.layout.cardview_entorno, new EntornoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Entorno entorno) {
                mListener.onEntornoClicked(entorno);
            }

        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onEntornoClicked(Entorno entorno);
    }


}
