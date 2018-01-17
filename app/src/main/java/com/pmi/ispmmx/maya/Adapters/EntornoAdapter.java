package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Entorno;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class EntornoAdapter extends RecyclerView.Adapter<EntornoAdapter.ViewHolder> {


    private List<Entorno> entornoList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public EntornoAdapter(List<Entorno> entornoList, int layout, OnItemClickListener listener) {
        this.entornoList = entornoList;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        this.context = parent.getContext();

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(entornoList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return entornoList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Entorno entorno);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView _cvEntorno;
        private TextView _nombre;


        public ViewHolder(View itemView) {
            super(itemView);
            _cvEntorno = itemView.findViewById(R.id.cv_entorno);
            _nombre = itemView.findViewById(R.id.tv_nombre_entorno);

        }

        public void blind(final Entorno entorno, final OnItemClickListener listener) {
            _nombre.setText(entorno.getNombre());

            _cvEntorno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(entorno);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(entorno);
                }
            });
        }
    }
}
