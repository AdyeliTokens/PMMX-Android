package com.pmi.ispmmx.maya.DialogFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


    private TextView defectosMecanicos;
    private CardView cvdefectosMecanicos;

    private TextView fallasMecanicos;
    private CardView cvfallasMecanicos;

    private CardView cvDefectosMecanicosPrincipal;
    private CardView cvDefectosElectricosPrincipal;

    private CardView cvFallasMecanicosPrincipal;
    private CardView cvFallasElectricosPrincipal;

    private Button btnVerDetalle;


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
        _title = _view.findViewById(R.id.title);
        _subTitle = _view.findViewById(R.id.subtitle);
        _fabAgregarDefecto = _view.findViewById(R.id.fab_agregar_defecto_mecanico);
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

        cvdefectosMecanicos = _view.findViewById(R.id.cv_NumDefectos_mecanicos);
        defectosMecanicos = _view.findViewById(R.id.txt_num_defectos_mecanicos);

        fallasMecanicos = _view.findViewById(R.id.txt_num_paros_mecanicas);
        cvfallasMecanicos = _view.findViewById(R.id.cv_NumParos_Mecanicos);

        _imgOrigenBig = _view.findViewById(R.id.imgOrigenBig);
        _imgOrigenBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickImagenOrigen(origen);
            }
        });

        cvDefectosMecanicosPrincipal = _view.findViewById(R.id.cv_defectos_mecanico_principal);
        cvDefectosMecanicosPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnClickDefectosMecanicosPrincipal(origen);
            }
        });

        cvFallasMecanicosPrincipal = _view.findViewById(R.id.cv_fallas_mecanicos_principal);
        cvFallasMecanicosPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnClicFallasMecanicosPrincipal(origen);
            }
        });
        btnVerDetalle = _view.findViewById(R.id.button_ver_detalle);
        btnVerDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickVerDetalle(origen);
            }
        });

    }

    private void llenarInformacion() {
        _title.setText(origen.getModulo().getNombre());
        _subTitle.setText(origen.getModulo().getNombreCorto());
        if (origen.getDefectosActivos() > 0) {
            defectosMecanicos.setText("" + origen.getDefectosActivos() + "");
            cvdefectosMecanicos.setVisibility(View.VISIBLE);
        } else {
            cvdefectosMecanicos.setVisibility(View.GONE);
        }

        if (origen.getParosActivos() > 0) {
            //cvFallasMecanicosPrincipal.setVisibility(View.VISIBLE);
            cvfallasMecanicos.setVisibility(View.VISIBLE);
            fallasMecanicos.setText("" + origen.getParosActivos() + "");
        } else {
            cvfallasMecanicos.setVisibility(View.GONE);
        }

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

        void OnClickDefectosMecanicosPrincipal(Origen origen);

        void OnClickDefectosElectricosPrincipal(Origen origen);

        void OnClicFallasMecanicosPrincipal(Origen origen);

        void OnClickFallasElectricosPrincipal(Origen origen);

        void onClickParosActivos(Origen origen);

        void onClickVerDetalle(Origen origen);

    }

}
