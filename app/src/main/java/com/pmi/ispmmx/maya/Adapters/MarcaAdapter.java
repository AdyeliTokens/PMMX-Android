package com.pmi.ispmmx.maya.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Marca;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class MarcaAdapter extends RecyclerView.Adapter<MarcaAdapter.ViewHolder> {

    private List<Marca> marcaList;
    private int layout;
    private OnItemClickListener itemClickListener;

    public MarcaAdapter(List<Marca> marcaList,  int layout, OnItemClickListener listener) {
        this.marcaList = marcaList;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(view);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(marcaList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return marcaList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Marca marca);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView _cvMarca;
        private TextView _nombre;


        public ViewHolder(View itemView) {
            super(itemView);
            _cvMarca = itemView.findViewById(R.id.cv_marca);
            _nombre = itemView.findViewById(R.id.tv_nombre_marca);

        }

        public void blind(final Marca marca , final OnItemClickListener listener) {
            _nombre.setText(marca.getNombre());
            //ColorGenerator generator = ColorGenerator.MATERIAL;
            //_cvEntorno.setCardBackgroundColor(generator.getColor(entorno.getNombre()));
            _cvMarca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(marca);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(marca);
                }
            });
        }
    }
}

