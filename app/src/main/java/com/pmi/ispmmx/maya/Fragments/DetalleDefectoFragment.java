package com.pmi.ispmmx.maya.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Persona;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.CircleTransform;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_PERSONAS;

public class DetalleDefectoFragment extends Fragment {

    public Boolean cerrarDefecto;
    public Boolean asignarDefecto;
    private SharedPreferences pref;
    private View view;
    private TextView _tvFechaEstimada;
    private TextView _tvNombreCompletoReportador;
    private TextView _tvDescripcion;
    private TextView _tvPuesto;
    private TextView _tvAsignado;
    private TextView _tvFechaReporte;
    private TextView _tvActivoMensaje;
    private TextView _tvSAP;
    private TextView _tvComentariosCount;
    private TextView _tvActividadesCount;

    private CardView cv_Descripcion;
    private CardView cv_Tiempos;
    private CardView cv_Prioridad;
    private CardView cv_SAP;
    private CardView cv_Comentarios;
    private CardView cv_Actividades;
    private CardView cv_Prioridad_Baja;
    private CardView cv_Prioridad_Media;
    private CardView cv_Prioridad_Alta;
    private CardView cv_Cerrar;
    private CardView cv_Responsable;
    private CardView cv_AsignarmeParo;
    private CardView cv_Activo;
    private CardView cv_Cerrado;

    private ImageView _imgReportante;
    private OnFragmentDefectoInteractionListener mListener;


    public DetalleDefectoFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalle_defecto, container, false);
        pref = getContext().getSharedPreferences(OperadorPreference.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        elementosUI();
        mListener.llenarDefecto();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentDefectoInteractionListener) {
            mListener = (OnFragmentDefectoInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnParosInteractionListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void elementosUI() {

        _tvFechaEstimada = view.findViewById(R.id.txt_fechaEstimado);
        _tvDescripcion = view.findViewById(R.id.txt_descripcion);
        _tvNombreCompletoReportador = view.findViewById(R.id.txt_nombreCompleto);
        _tvPuesto = view.findViewById(R.id.txt_Puesto);
        _tvFechaReporte = view.findViewById(R.id.txt_fechaReporte);
        _tvActivoMensaje = view.findViewById(R.id.txt_ActivoMensaje);
        _tvSAP = view.findViewById(R.id.tv_SAP);
        _tvComentariosCount = view.findViewById(R.id.tvComentariosCount);
        _tvActividadesCount = view.findViewById(R.id.tvActividadesCount);
        _tvAsignado = view.findViewById(R.id.tv_Asignado);

        cv_Activo = view.findViewById(R.id.cv_Activo);
        cv_Descripcion = view.findViewById(R.id.cv_Descripcion);
        cv_Descripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cv_Tiempos = view.findViewById(R.id.cv_Tiempos);
        cv_Tiempos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
        cv_Prioridad = view.findViewById(R.id.cv_Prioridad);
        cv_Prioridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cv_SAP = view.findViewById(R.id.cv_SAP);
        cv_SAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickSap();
            }
        });
        cv_Prioridad_Alta = view.findViewById(R.id.cv_prioridad_alta);
        cv_Prioridad_Media = view.findViewById(R.id.cv_prioridad_media);
        cv_Prioridad_Baja = view.findViewById(R.id.cv_prioridad_baja);
        cv_Comentarios = view.findViewById(R.id.cv_Comentarios);
        cv_Comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onComentariosClick();
            }
        });
        cv_Actividades = view.findViewById(R.id.cv_Actividades);
        cv_Actividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onActividadesClick();
            }
        });
        cv_Cerrar = view.findViewById(R.id.cv_Cerrar);
        cv_Cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCerrarDefectoClick();
            }
        });
        cv_AsignarmeParo = view.findViewById(R.id.cv_AsignarmeParo);
        cv_AsignarmeParo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAsignarceDEfectoClick();
            }
        });
        cv_Cerrado = view.findViewById(R.id.cv_Cerrado);
        cv_Responsable = view.findViewById(R.id.cv_Responsable);

        _imgReportante = view.findViewById(R.id.img_reportadorPor);
        _imgReportante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickReportante();
            }
        });
    }


    public void llenarInformacionDeDefecto(Defecto defecto) {
        _tvSAP.setText(defecto.getNotificacionSap() + "");


        try {
            _tvFechaEstimada.setText(defecto.getFechaApiEstimada());
        } catch (Exception e) {
            _tvFechaEstimada.setText("Ingresar Fecha Estimada!!");
        }
        if (defecto.getPrioridad() == 1) {
            cv_Prioridad_Baja.setVisibility(View.VISIBLE);
            cv_Prioridad_Media.setVisibility(View.GONE);
            cv_Prioridad_Alta.setVisibility(View.GONE);
        } else if (defecto.getPrioridad() == 2) {
            cv_Prioridad_Baja.setVisibility(View.GONE);
            cv_Prioridad_Media.setVisibility(View.VISIBLE);
            cv_Prioridad_Alta.setVisibility(View.GONE);
        } else if (defecto.getPrioridad() == 3) {
            cv_Prioridad_Baja.setVisibility(View.GONE);
            cv_Prioridad_Media.setVisibility(View.GONE);
            cv_Prioridad_Alta.setVisibility(View.VISIBLE);
        }


        _tvDescripcion.setText(defecto.getDescripcion());
        String nombreCompleto = defecto.getReportador().getNombre() + " "
                + defecto.getReportador().getApellido1() + " "
                + defecto.getReportador().getApellido2();

        Picasso.with(getContext())
                .load(URL_FOTOS_PERSONAS + defecto.getIdReportador())
                .transform(new CircleTransform())
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.drawable.ic_error_outline_gray)
                .into(_imgReportante);

        _tvNombreCompletoReportador.setText(nombreCompleto);
        _tvPuesto.setText(defecto.getReportador().getPuesto().getNombre());
        _tvFechaReporte.setText(defecto.getFechaApiReporte().toString());
        _tvSAP.setText(defecto.getNotificacionSap() + "");
        _tvActividadesCount.setText(defecto.getActividadesCount() + "");
        _tvComentariosCount.setText(defecto.getComentariosCount() + "");

        if (defecto.getIdResponsable() > 0) {

            mListener.llenarResponsable();
        } else {
            cv_Responsable.setVisibility(View.GONE);
        }


        if (defecto.getActivo()) {
            cv_Activo.setVisibility(View.VISIBLE);
            cv_Cerrado.setVisibility(View.GONE);
            if (cerrarDefecto) {
                cv_Cerrar.setVisibility(View.VISIBLE);
            } else {
                cv_Cerrar.setVisibility(View.GONE);
            }
        } else {
            cv_Activo.setVisibility(View.GONE);
            cv_Cerrado.setVisibility(View.VISIBLE);
            cv_Cerrar.setVisibility(View.GONE);
        }

        if (!asignarDefecto) {
            cv_AsignarmeParo.setVisibility(View.GONE);
        }
        if (defecto.getIdResponsable() == pref.getInt(OperadorPreference.ID_PERSONA_SHARED_PREF, 0)) {
            cv_AsignarmeParo.setVisibility(View.GONE);
        }

    }

    public void llenarInformacionResponsable(Persona persona) {
        _tvAsignado.setText(String.format("%s %s %s", persona.getNombre(), persona.getApellido1(), persona.getApellido2()));
        cv_Responsable.setVisibility(View.VISIBLE);
    }


    private void datePicker() {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date fechaestimada = new Date();
                fechaestimada.setYear(year);
                fechaestimada.setMonth(monthOfYear);
                fechaestimada.setDate(dayOfMonth);

                mListener.onFechaEstimadaClick(dayOfMonth, monthOfYear, year);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public interface OnFragmentDefectoInteractionListener {
        void llenarDefecto();

        void llenarResponsable();

        void onCerrarDefectoClick();

        void onFechaEstimadaClick(int dia, int mes, int year);

        void onAsignarceDEfectoClick();

        void onComentariosClick();

        void onActividadesClick();

        void onClickSap();

        void onClickReportante();
    }
}


