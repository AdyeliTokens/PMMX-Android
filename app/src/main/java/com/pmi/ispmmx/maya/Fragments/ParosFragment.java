package com.pmi.ispmmx.maya.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.Paros.ParoActivoAdapter;
import com.pmi.ispmmx.maya.Adapters.Paros.ParoCerradoAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class ParosFragment extends Fragment {
    public RecyclerView.Adapter adapparosactivos;
    public RecyclerView.Adapter adapparoscerrados;
    public List<Paro> parosCerrados;
    public List<Paro> parosAbiertos;
    public boolean cerrarParos;
    public boolean asignarParos;
    public String mensajeParosActivos;
    public boolean cargaParosActivos;
    public boolean errorParosActivos;
    public String getMensajeErrorParosCerrados;
    private View view;
    private RecyclerView _rvParosActivos;
    private RecyclerView.LayoutManager _managerParosActivos;
    private RecyclerView _rvParosCerrados;
    private RecyclerView.LayoutManager _managerParosCerrados;
    private OnParoInteractionListener mListener;


    public ParosFragment() {

    }


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnParoInteractionListener) {
                mListener = (OnParoInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnParosInteractionListener");
            }
        } catch (Exception e) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paros, container, false);


        elementosUI();
        cargarEventos();

        iniciarRecycleActivos();
        iniciarRecycleCerrados();

        //Paros();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mListener.llenarParosActivos();
    }


    private void elementosUI() {
        _rvParosCerrados = view.findViewById(R.id.rv_parosCerrados);
        _rvParosActivos = view.findViewById(R.id.rv_parosActivos);

    }

    public void cargarEventos() {

    }

    private void Paros() {
        if (mListener != null) {

            mListener.llenarParosActivos();
            mListener.llenarParosCerrados();
        } else {
            //estadoErrorParosCerrados(R.drawable.ic_mood_bad_gray, "Error al cargar el mListener :(");
        }
    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///            Inicializar Datos para las RV                //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public void iniciarAdapterAbirtos() {
        adapparosactivos = new ParoActivoAdapter(getContext(),parosAbiertos, R.layout.cardview_paro_activo, new ParoActivoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Paro paro, int position) {
                mListener.abrirActivityParo(paro);
            }

            @Override
            public boolean OnItemLongClick(Paro paro, int position) {
                return true;
                //deleteParo(position);
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
        _managerParosActivos = new LinearLayoutManager(view.getContext());
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

    public void iniciarAdapterCerrados() {
        adapparoscerrados = new ParoCerradoAdapter(parosCerrados, R.layout.cardview_paro_cerrado, new ParoCerradoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Paro paro, int position) {
                mListener.abrirActivityParo(paro);
            }
        });
    }

    public void iniciarRecycleCerrados() {
        _managerParosCerrados = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        _rvParosCerrados.setHasFixedSize(false);
        _rvParosCerrados.setItemAnimator(new DefaultItemAnimator());
        _rvParosCerrados.setLayoutManager(_managerParosCerrados);
        _rvParosCerrados.setNestedScrollingEnabled(false);

        _rvParosCerrados.setAdapter(adapparoscerrados);
        _rvParosCerrados.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });


    }

    public void addParo(Paro paro) {
        parosAbiertos.add(paro);
        adapparosactivos.notifyDataSetChanged();
    }

    public void deleteParo(int position) {
        parosAbiertos.remove(position);
        adapparosactivos.notifyDataSetChanged();
    }


    public interface OnParoInteractionListener {
        void llenarParosActivos();

        void abrirActivityParo(Paro paro);

        void llenarParosCerrados();
    }


    /// Tecnico y lider de mantenimiento sap;
    /// comentario Mecanico, Lider de mantenimiento y tecnico,
    /// Medida Contenedora,Lider de mentenimiento. (como se soluciono, que se hizo, plan de accion)


}
