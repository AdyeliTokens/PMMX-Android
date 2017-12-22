package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Modelos.Entidades.NoConformidad;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class NoConformidadesPorSeccionAdapter extends RecyclerView.Adapter<NoConformidadesPorSeccionAdapter.ViewHolder> {

    private List<ModuloSeccion> moduloSeccionList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public NoConformidadesPorSeccionAdapter(List<ModuloSeccion> moduloSeccions, int layout, OnItemClickListener listener) {
        this.moduloSeccionList = moduloSeccions;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        ViewHolder vh;


        view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(moduloSeccionList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return moduloSeccionList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(ModuloSeccion moduloSeccion, int position);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView _cvSeccion;
        public TextView _tvTituloSeccion;
        public RecyclerView _rvNoConformidades;
        public RecyclerView.LayoutManager mLayoutManager;
        public RecyclerView.Adapter mAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            _cvSeccion = itemView.findViewById(R.id.cv_seccion_no_conformidad);
            _tvTituloSeccion = itemView.findViewById(R.id.title_seccion);
            _rvNoConformidades = itemView.findViewById(R.id.rv_no_conformidades);


        }

        public void blind(final ModuloSeccion moduloSeccion, final OnItemClickListener listener) {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(moduloSeccion.getNombre());
            _cvSeccion.setCardBackgroundColor(color);
            _tvTituloSeccion.setText(moduloSeccion.getNombre());
            mAdapter = new NoConformidadAdapter(moduloSeccion.getNoconformidad(), R.layout.cardview_no_conformidad, new NoConformidadAdapter.OnItemClickListener() {

                @Override
                public void OnItemClick(NoConformidad noConformidad, int position) {

                }
            });

            mLayoutManager = new LinearLayoutManager(context);
            _rvNoConformidades.setHasFixedSize(true);
            _rvNoConformidades.setItemAnimator(new DefaultItemAnimator());
            _rvNoConformidades.setLayoutManager(mLayoutManager);
            _rvNoConformidades.setAdapter(mAdapter);
            _rvNoConformidades.setNestedScrollingEnabled(false);






            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(moduloSeccion, getAdapterPosition());
                }
            });

        }
    }
}
