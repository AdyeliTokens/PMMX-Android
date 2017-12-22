package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.NoConformidad;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class NoConformidadAdapter extends RecyclerView.Adapter<NoConformidadAdapter.ViewHolder> {
    private List<NoConformidad> noConformidadList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public NoConformidadAdapter(List<NoConformidad> noConformidadList, int layout, OnItemClickListener listener) {
        this.noConformidadList = noConformidadList;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(noConformidadList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return noConformidadList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(NoConformidad noConformidad, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _tvDescripcion;
        private TextView _tvVQI;


        public ViewHolder(View itemView) {
            super(itemView);
            _tvDescripcion = itemView.findViewById(R.id.tv_descripcion);
            _tvVQI = itemView.findViewById(R.id.tv_vqi);

        }

        public void blind(final NoConformidad noConformidad, final OnItemClickListener listener) {
            _tvDescripcion.setText(noConformidad.getDescrcipcion());
            _tvVQI.setText(noConformidad.getCalificacion_vqi() + "");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

