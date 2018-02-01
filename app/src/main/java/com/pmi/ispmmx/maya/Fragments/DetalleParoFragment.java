package com.pmi.ispmmx.maya.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmi.ispmmx.maya.Adapters.Paros.TiempoDeParoAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.TiempoDeParo;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.util.Date;
import java.util.List;


public class DetalleParoFragment extends Fragment {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public boolean puedeCerrarse;
    public boolean puedeAsignarce;
    public boolean puedeComentarce;
    private SharedPreferences pref;
    // UI
    private View view;
    private TextView _nombreOperador;
    private TextView _nombreMecanico;
    private TextView _tvMotivo;

    private CardView _cvTiempoParo;
    private CardView _cvCerrar;
    private CardView _cvAsignarmeParo;
    private CardView _cvAgregarMotivo;
    private CardView _cvActividades;
    private CardView _cvComentarios;
    private CardView _cvMotivo;


    private ImageView _imgMecanico;
    private ImageView _imgOperador;
    private ImageView _imgEditMotivo;

    private Chronometer _time;

    private RecyclerView _rvTiempoDeParos;

    private LinearLayoutManager mLayoutManager;
    private TiempoDeParoAdapter mAdapter;

    private int idParo;
    private String nombreModulo;
    private String nombreWorkCenter;
    private Paro paro;
    private List<TiempoDeParo> tiempoDeParos;

    private OnDetalleParoInteractionListener mListener;


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                      Constructor                        //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public DetalleParoFragment() {
        // Required empty public constructor
    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                Funciones de CircleLive                  //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnDetalleParoInteractionListener) {
                mListener = (OnDetalleParoInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnParosInteractionListener");
            }
        } catch (Exception e) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detalle_paro, container, false);
        pref = getContext().getSharedPreferences(OperadorPreference.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);


        elementosUI();


        mListener.llenarParo();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void elementosUI() {
        _cvTiempoParo = view.findViewById(R.id.cv_TiempoParo);
        _nombreOperador = view.findViewById(R.id.txt_operador);
        _nombreMecanico = view.findViewById(R.id.txt_mecanico);
        _tvMotivo = view.findViewById(R.id.tv_motivo);
        _time = view.findViewById(R.id.txt_tiempoParo);
        _imgMecanico = view.findViewById(R.id.img_mecanico);
        _imgOperador = view.findViewById(R.id.img_operador);
        _rvTiempoDeParos = view.findViewById(R.id.rv_tiempoDeParos);


        _cvAgregarMotivo = view.findViewById(R.id.cv_AgregarMotivoDeParo);
        _cvAgregarMotivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickMotivoParo("");
            }
        });
        _cvAsignarmeParo = view.findViewById(R.id.cv_AsignarmeParo);
        _cvAsignarmeParo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAsignarParo();
            }
        });
        _cvCerrar = view.findViewById(R.id.cv_Cerrar);
        _cvCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickCerrarParo(paro);
            }
        });

    }

    public void llenarInformacionDeParo(Paro paro) {
        if (paro.getMotivo().length() == 0) {
            _tvMotivo.setText("Sin un motivo asignado aun :(");
        } else {
            _tvMotivo.setText(paro.getMotivo());
        }


        String nombreOperador = paro.getReportador().getNombre() + " " +
                paro.getReportador().getApellido1() + " " +
                paro.getReportador().getApellido2();
        _nombreOperador.setText(nombreOperador);


        if (paro.getTiempoDeParos() != null) {
            tiempoDeParos = paro.getTiempoDeParos();


            long tiempoTotal = 0;
            Boolean activo = false;
            if (tiempoDeParos.size() > 1) {
                long lastSuccess = paro.getFechaApiReporte().getTime(); //Some Date object
                long elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
                _time.setBase(lastSuccess - elapsedRealtimeOffset);

            } else {
                for (TiempoDeParo tiempo : tiempoDeParos) {
                    long lastSuccess = tiempo.getInicio().getTime();
                    long elapsedRealtimeOffset;

                    try {
                        Date fin = tiempo.getFin();

                        elapsedRealtimeOffset = fin.getTime() - SystemClock.elapsedRealtime();

                        tiempoTotal = (tiempoTotal) + (lastSuccess - elapsedRealtimeOffset);

                    } catch (Exception e) {
                        elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
                        tiempoTotal = (tiempoTotal) + (lastSuccess - elapsedRealtimeOffset);
                        activo = true;

                    }

                }
                _time.setBase(tiempoTotal - SystemClock.elapsedRealtime());
            }


            if (activo) {
                _time.start();
                _cvTiempoParo.setCardBackgroundColor(getResources().getColor(R.color.colorRedScale7));
            } else {
                _time.stop();
                _cvTiempoParo.setCardBackgroundColor(getResources().getColor(R.color.colorGrayScale6));
            }
            llenarTiempoDeParos();
        }

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(0)
                .width(70)  // width in px
                .height(70)
                .bold()
                .toUpperCase()
                .fontSize(30)/* thickness in px */
                .endConfig()
                .buildRound(nombreOperador.substring(0, 1), R.color.colorBlueScale2);


        _imgOperador.setImageDrawable(drawable);


        int idMecanico = paro.getIdMecanico();
        if (idMecanico > 0) {
            _nombreMecanico.setText("" + idMecanico);
        }

        if (puedeCerrarse) {
            _cvCerrar.setVisibility(View.VISIBLE);
        }
        if (puedeAsignarce) {
            _cvAsignarmeParo.setVisibility(View.VISIBLE);
        }
        if (puedeComentarce) {
            _cvAgregarMotivo.setVisibility(View.VISIBLE);
        }

    }

    public void llenarMecanico() {
        String nombreMecanico = pref.getString(OperadorPreference.NOMBRE_PERSONA_SHARED_PREF, "No disponible");
        String apellido1 = pref.getString(OperadorPreference.APELLIDO1_PERSONA_SHARED_PREF, "No disponible");
        String apellido2 = pref.getString(OperadorPreference.APELLIDO2_PERSONA_SHARED_PREF, "No disponible");


        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(0)
                .width(70)  // width in px
                .height(70)
                .bold()
                .toUpperCase()
                .fontSize(30)/* thickness in px */
                .endConfig()
                .buildRound(nombreMecanico.substring(0, 1), R.color.colorBlueScale2);


        _imgMecanico.setImageDrawable(drawable);
        _nombreMecanico.setText(nombreMecanico + " " + apellido1 + " " + apellido2);
    }

    private void llenarTiempoDeParos() {
        mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new TiempoDeParoAdapter(tiempoDeParos, R.layout.cardview_tiempodeparos, new TiempoDeParoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(TiempoDeParo tiempoDeParo, int position) {
                /*Intent intent = new Intent(getActivity(), DefectoActivity.class);
                intent.putExtra("idDefecto", tiempoDeParo.getId());
                startActivity(intent);*/
            }
        });
        mAdapter.notifyDataSetChanged();
        _rvTiempoDeParos.setHasFixedSize(true);
        _rvTiempoDeParos.setItemAnimator(new DefaultItemAnimator());
        _rvTiempoDeParos.setLayoutManager(mLayoutManager);
        _rvTiempoDeParos.setAdapter(mAdapter);
        _rvTiempoDeParos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //if (FAB_Status) {
                //  hideFAB();
                //FAB_Status = false;
                //}
                return false;
            }
        });

    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                     Interfaces                          //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public interface OnDetalleParoInteractionListener {
        void llenarParo();

        void onClickAsignarParo();

        void onClickMotivoParo(String motivo);

        void onClickCerrarParo(Paro paro);
    }


}
