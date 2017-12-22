package com.pmi.ispmmx.maya.Adapters.Defectos;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Secciones.DefectoSeccion;
import com.pmi.ispmmx.maya.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ispmmx on 7/18/17.
 */

public class ListaDeDefectosAdapter extends RecyclerView.Adapter<ListaDeDefectosAdapter.ViewHolder> {

    private List<DefectoSeccion> defectoList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public ListaDeDefectosAdapter(List<DefectoSeccion> defectos, int layout, OnItemClickListener listener) {
        this.defectoList = defectos;

        Collections.sort(defectoList, new Comparator<DefectoSeccion>() {

            @Override
            public int compare(DefectoSeccion tiempoDeParo, DefectoSeccion t1) {
                String left = tiempoDeParo.getPocision() + "";
                String right = t1.getPocision() + "";
                return right.compareTo(left);
            }
        });

        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(view);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(defectoList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return defectoList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Defecto defecto, int position, View foto);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView btnMore;
        public RecyclerView recycler;
        public RecyclerView.Adapter mAdapter;
        public TextView _txtMensaje;
        public ImageView _imgMensaje;
        private RecyclerView.LayoutManager mLayoutManager;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            btnMore = itemView.findViewById(R.id.btnMore);
            recycler = itemView.findViewById(R.id.recyclerDefectos);
            _txtMensaje = itemView.findViewById(R.id.txt_mensajeError);
            _imgMensaje = itemView.findViewById(R.id.imgMensaje);
        }

        public void blind(final DefectoSeccion seccion, final OnItemClickListener listener) {
            title.setText(seccion.getTitulo());
            if (seccion.isError()) {
                _txtMensaje.setText(seccion.getMensajeError());
                _imgMensaje.setVisibility(View.VISIBLE);
                _txtMensaje.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            } else if (seccion.getElementos() == null || seccion.getElementos().size() == 0) {
                _txtMensaje.setText("Sin elementos que mostrar");
                _imgMensaje.setVisibility(View.VISIBLE);
                _txtMensaje.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            } else {
                _txtMensaje.setVisibility(View.GONE);
                _imgMensaje.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                mAdapter = new DefectoAdapter(seccion.getElementos(), R.layout.cardview_defecto, new DefectoAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Defecto defecto, int position, View foto) {
                        listener.OnItemClick(defecto, position, foto);
                    }
                });
                mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                recycler.setHasFixedSize(true);
                recycler.setItemAnimator(new DefaultItemAnimator());
                recycler.setLayoutManager(mLayoutManager);
                recycler.setAdapter(mAdapter);
                recycler.setOnTouchListener(new View.OnTouchListener() {
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


        }
    }
}
