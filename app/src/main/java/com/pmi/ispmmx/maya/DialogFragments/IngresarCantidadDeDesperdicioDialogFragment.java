package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.R;


public class IngresarCantidadDeDesperdicioDialogFragment extends BottomSheetDialogFragment {

    private View _view;
    private Listener mListener;

    public static IngresarCantidadDeDesperdicioDialogFragment newInstance() {
        final IngresarCantidadDeDesperdicioDialogFragment fragment = new IngresarCantidadDeDesperdicioDialogFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.bottom_sheet_mostrar_marcas_para_desperdicio, container, false);
        return _view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        elementosUI();
        iniciarAdapter();
        iniciarRecycle();

    }

    private void elementosUI() {

    }

    private void iniciarRecycle() {

    }

    private void iniciarAdapter() {

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

    }



}
