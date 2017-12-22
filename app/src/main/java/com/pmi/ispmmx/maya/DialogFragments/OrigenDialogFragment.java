package com.pmi.ispmmx.maya.DialogFragments;

/**
 * Created by chan jacky chan on 06/11/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_ORIGENES;

public class OrigenDialogFragment extends BottomSheetDialogFragment {
    public Origen origen;
    private Context context;

    private OnInteractionListener mListener;


    private View _view;
    private TextView _title;
    private TextView _subTitle;
    private FloatingActionButton _fabAgregarDefecto;
    private FloatingActionButton _fabAgregarFalla;
    private FloatingActionButton _fabAgregarFoto;
    private ImageView _imgOrigenBig;


    public static OrigenDialogFragment newInstance(Origen origen) {
        OrigenDialogFragment frag = new OrigenDialogFragment();
        frag.origen = origen;
        return frag;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            mListener = (OnInteractionListener) context;
            this.context = context;
        } else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        _view = inflater.inflate(R.layout.bottom_sheet_origen, container, false);


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
        _subTitle = _view.findViewById(R.id.subtitle);
        _fabAgregarDefecto = _view.findViewById(R.id.fab_agregar_texto);
        _fabAgregarDefecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAgregarDefecto(origen);
            }
        });
        _fabAgregarFalla = _view.findViewById(R.id.fab_agregar_falla);
        _fabAgregarFoto = _view.findViewById(R.id.fab_agregar_foto);
        _fabAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickCamaraOrigen(origen, _imgOrigenBig);
            }
        });
        _fabAgregarFalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAgregarFalla(origen);
            }
        });
        _imgOrigenBig = _view.findViewById(R.id.imgOrigenBig);
        _imgOrigenBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickImagenOrigen(origen);
            }
        });


    }

    private void llenarInformacion() {
        _title.setText(origen.getModulo().getNombre());
        _subTitle.setText(origen.getModulo().getNombreCorto());

        Picasso.with(context)
                .load(URL_FOTOS_ORIGENES + origen.getId())
                .error(R.drawable.ic_error_outline_gray)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(_imgOrigenBig);


    }

    public interface OnInteractionListener {

        void onClickAgregarFalla(Origen origen);

        void onClickAgregarDefecto(Origen origen);

        void onClickImagenOrigen(Origen origen);

        void onClickCamaraOrigen(Origen origen, ImageView image);

        void OnClickDefectosActivos(Origen origen);

        void onClickParosActivos(Origen origen);

    }

}
