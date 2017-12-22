package com.pmi.ispmmx.maya.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.Defectos.DefectoAdapter;
import com.pmi.ispmmx.maya.Adapters.Paros.ParoActivoAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisServiciosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisServiciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisServiciosFragment extends Fragment {


    public List<Paro> paroList;
    public List<Defecto> defectoList;
    public RecyclerView.Adapter mAdapterParos;
    public RecyclerView.Adapter mAdapterDefectos;
    private View _view;
    private RecyclerView _rvParosActivos;
    private RecyclerView _rvDefectosActivos;
    private RecyclerView.LayoutManager _mParosActivos;
    private RecyclerView.LayoutManager _mDefectosActivos;


    private OnFragmentInteractionListener mListener;

    public MisServiciosFragment() {
    }

    public static MisServiciosFragment newInstance(List<Defecto> defectoList, List<Paro> paroList) {
        MisServiciosFragment fragment = new MisServiciosFragment();
        fragment.paroList = paroList;
        fragment.defectoList = defectoList;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_mis_servicios, container, false);

        elementosUI();

        iniciarAdapterParosAbirtos();
        iniciarAdapterDefectosAbirtos();
        iniciarRecycleParosActivos();
        iniciarRecycleDefectosActivos();

        return _view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void elementosUI() {
        _rvParosActivos = _view.findViewById(R.id.rv_parosActivos);
        _rvDefectosActivos = _view.findViewById(R.id.rv_defectosActivos);

    }

    public void iniciarAdapterParosAbirtos() {
        mAdapterParos = new ParoActivoAdapter(getContext(),paroList, R.layout.cardview_paro_activo, new ParoActivoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Paro paro, int position) {
                mListener.onClickParo(paro);
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
        mAdapterParos.notifyDataSetChanged();


    }

    private void iniciarRecycleParosActivos() {
        _mParosActivos = new LinearLayoutManager(_view.getContext());
        _rvParosActivos.setHasFixedSize(false);
        _rvParosActivos.setItemAnimator(new DefaultItemAnimator());
        _rvParosActivos.setLayoutManager(_mParosActivos);
        _rvParosActivos.setNestedScrollingEnabled(false);
        _rvParosActivos.setAdapter(mAdapterParos);


    }

    public void iniciarAdapterDefectosAbirtos() {
        mAdapterDefectos = new DefectoAdapter(defectoList, R.layout.cardview_defecto, new DefectoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Defecto defecto, int position, View foto) {
                mListener.onClickDefecto(defecto, position, foto);
            }
        });
    }

    private void iniciarRecycleDefectosActivos() {
        //_managerDefectosActivos = new LinearLayoutManager(_view.getContext());

        _mDefectosActivos = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        _rvDefectosActivos.setHasFixedSize(false);
        _rvDefectosActivos.setItemAnimator(new DefaultItemAnimator());
        _rvDefectosActivos.setLayoutManager(_mDefectosActivos);
        _rvDefectosActivos.setNestedScrollingEnabled(false);
        _rvDefectosActivos.setAdapter(mAdapterDefectos);
        _rvDefectosActivos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

    }


    public interface OnFragmentInteractionListener {
        void onClickDefecto(Defecto defecto, int position, View foto);

        void onClickParo(Paro paro);
    }
}
