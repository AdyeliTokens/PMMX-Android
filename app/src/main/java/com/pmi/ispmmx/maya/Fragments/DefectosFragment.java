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

import com.pmi.ispmmx.maya.Adapters.Defectos.ListaDeDefectosAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Secciones.DefectoSeccion;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class DefectosFragment extends Fragment {
    public RecyclerView.Adapter mAdapter;
    public String mensajeError;
    public boolean cargando;
    /////Listas
    //public List<List<Defecto>> defectos;
    public List<DefectoSeccion> defectos;
    //Elementos View
    private View view;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    ////
    private OnDefectoInteractionListener mListener;


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                     Constructores                       //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public DefectosFragment() {
    }


    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///                  Funciones de  CircleLife               //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnDefectoInteractionListener) {
                mListener = (OnDefectoInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnDefectoInteractionListener");
            }
        } catch (Exception e) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_defectos, container, false);

        elementosUI();
        iniciarRecycle();

        Iniciar();


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
    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///               Funciones de Incio de View                //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    private void elementosUI() {

        mRecycleView = view.findViewById(R.id.recycleListaDeDefectos);

    }

    private void Iniciar() {
        if (mListener != null) {
            //mListener.llenarDefectos();
        } else {

        }

    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    ///               Iniciarlizar RV con adapter               //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public void iniciarAdapter() {
        mAdapter = new ListaDeDefectosAdapter(defectos, R.layout.cardview_lista_defectos, new ListaDeDefectosAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Defecto defecto, int position, View foto) {
                mListener.OnClickDefecto(defecto, position, foto);
            }
        });
    }

    public void iniciarRecycle() {

        mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        //mLayoutManager =new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        mRecycleView.setOnTouchListener(new View.OnTouchListener() {
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
    ///                        Interfaces                       //
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    public interface OnDefectoInteractionListener {
        void llenarDefectos();

        void OnClickDefecto(Defecto defecto, int position, View foto);
    }


}
