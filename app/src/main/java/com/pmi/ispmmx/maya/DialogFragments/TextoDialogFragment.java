package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pmi.ispmmx.maya.R;

/**
 * Created by chan jacky chan on 13/11/2017.
 */

public class TextoDialogFragment extends BottomSheetDialogFragment {

    private OnInteractionListener mListener;


    private View _view;
    private TextView _title;
    private EditText _texto;
    private FloatingActionButton _fabAgregarTexto;
    private FloatingActionButton _fabBorrarTexto;
    private FloatingActionButton _fabMicrofono;


    public static TextoDialogFragment newInstance() {
        TextoDialogFragment frag = new TextoDialogFragment();

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
        _view = inflater.inflate(R.layout.bottom_sheet_texto, container, false);

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
        _title = _view.findViewById(R.id.title);
        _texto = _view.findViewById(R.id.texto_para_ingresar);
        _fabAgregarTexto = _view.findViewById(R.id.fab_agregar_texto);
        _fabAgregarTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAgregarTexto(_texto.getText() + "");
            }
        });
        _fabBorrarTexto = _view.findViewById(R.id.fab_agregar_falla);
        _fabBorrarTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickBorrarTexto();
            }
        });
        _fabMicrofono = _view.findViewById(R.id.fab_microfono);
        _fabMicrofono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickMicrofono();
            }
        });


    }


    private void llenarInformacion() {
        _title.setText("");
    }

    public interface OnInteractionListener {

        void onClickAgregarTexto(String texto);

        void onClickBorrarTexto();

        void onClickMicrofono();
    }

}

