package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.R;

/**
 * Created by chan jacky chan on 27/11/2017.
 */

public class UsuariosRecordadosDialogFragment extends BottomSheetDialogFragment {

    private OnInteractionListener mListener;


    private View _view;


    public static UsuariosRecordadosDialogFragment newInstance() {
        UsuariosRecordadosDialogFragment frag = new UsuariosRecordadosDialogFragment();

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
        _view = inflater.inflate(R.layout.bottom_sheet_usuarios_recordados, container, false);

        elementosUI();
        llenarInformacion();

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
        //_toolbar = _view.findViewById(R.id.toolbar);



    }


    private void llenarInformacion() {

    }

    public interface OnInteractionListener {


    }

}

