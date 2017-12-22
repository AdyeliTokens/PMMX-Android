package com.pmi.ispmmx.maya.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pmi.ispmmx.maya.Adapters.ComentarioAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Comentario;
import com.pmi.ispmmx.maya.R;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;


public class ComentariosFragment extends Fragment {
    public RecyclerView.Adapter mAdapter;
    public int idPersona;
    public List<Comentario> comentarioList;
    private OnInteractionListener mListener;
    private View _view;
    private CardView _cvEnviar;
    private EditText _etMensaje;
    private FloatingActionButton _fabEnviar;
    private RecyclerView _rvComentarios;
    private RecyclerView.LayoutManager _managerComentarios;


    public ComentariosFragment() {
    }

    public static ComentariosFragment newInstance(List<Comentario> comentarioList) {
        ComentariosFragment frag = new ComentariosFragment();
        frag.comentarioList = comentarioList;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_comentarios, container, false);

        elementosUI();
        iniciarAdapter();
        iniciarRecycle();


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
        _cvEnviar = _view.findViewById(R.id.cv_mensaje);
        _etMensaje = _view.findViewById(R.id.et_mensaje);
        _fabEnviar = _view.findViewById(R.id.fab_enviar);
        _fabEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.OnClickEnviarComentario(generarComentario());
            }
        });
        _rvComentarios = _view.findViewById(R.id.rv_comentarios);
    }


    public void iniciarAdapter() {
        mAdapter = new ComentarioAdapter(comentarioList, idPersona, R.layout.cardview_comentario, new ComentarioAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Comentario comentario, int position, View foto) {

            }
        });
    }

    private void iniciarRecycle() {
        //_managerDefectosActivos = new LinearLayoutManager(_view.getContext());
        _managerComentarios = new LinearLayoutManager(getContext(), VERTICAL, false);
        _rvComentarios.setHasFixedSize(false);
        _rvComentarios.setItemAnimator(new DefaultItemAnimator());
        _rvComentarios.setLayoutManager(_managerComentarios);
        _rvComentarios.setAdapter(mAdapter);
        _rvComentarios.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

    }

    public void bajarscroll(){
        _rvComentarios.scrollToPosition(comentarioList.size() - 1);
    }


    private Comentario generarComentario(){
        Comentario comentario = new Comentario();
        comentario.setOpinion(_etMensaje.getText().toString());
        return comentario;
    }

    public interface OnInteractionListener {
        void OnClickEnviarComentario(Comentario comentario);
    }


}
