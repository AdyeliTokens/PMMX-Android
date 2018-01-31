package com.pmi.ispmmx.maya.Adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Feed;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Feed> feedList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public FeedAdapter(List<Feed> feedList, int layout, OnItemClickListener listener) {
        this.feedList = feedList;
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
        holder.blind(feedList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public interface OnItemClickListener {
        void OnItemClickFalla(Paro paro);

        void OnItemClickDefecto(Defecto defecto);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView _cvElementoActividadFalla;
        private TextView _fechaFalla;
        private TextView _descripcionFalla;

        private CardView _cvElementoActividadDefecto;
        private TextView _fechaDefecto;
        private TextView _descripcionDefecto;


        public ViewHolder(View itemView) {
            super(itemView);
            _cvElementoActividadFalla = itemView.findViewById(R.id.cv_elemento_feed_actividad_falla);
            _descripcionFalla = itemView.findViewById(R.id.tv_descripcion_falla);
            _fechaFalla = itemView.findViewById(R.id.tv_fecha_falla);

            _cvElementoActividadDefecto = itemView.findViewById(R.id.cv_elemento_feed_actividad_defecto);
            _descripcionDefecto = itemView.findViewById(R.id.tv_descripcion_defecto);
            _fechaDefecto = itemView.findViewById(R.id.tv_fecha_defecto);


        }

        public void blind(final Feed feed, final OnItemClickListener listener) {
            if (feed.getActividadEnDefecto() != null) {
                _cvElementoActividadFalla.setVisibility(View.GONE);
                _cvElementoActividadDefecto.setVisibility(View.VISIBLE);
                _descripcionDefecto.setText(feed.getActividadEnDefecto().getDescripcion());

                _fechaDefecto.setText(feed.getFecha().toString());
                _cvElementoActividadDefecto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.OnItemClickDefecto(feed.getActividadEnDefecto().getDefecto());
                    }
                });

            } else if (feed.getActividadEnParo() != null) {
                _cvElementoActividadFalla.setVisibility(View.VISIBLE);
                _cvElementoActividadDefecto.setVisibility(View.GONE);
                _descripcionFalla.setText(feed.getActividadEnParo().getDescripcion());

                _fechaFalla.setText(feed.getFecha().toString());
                _cvElementoActividadFalla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.OnItemClickFalla(feed.getActividadEnParo().getParo());
                    }
                });
            }


        }
    }
}

