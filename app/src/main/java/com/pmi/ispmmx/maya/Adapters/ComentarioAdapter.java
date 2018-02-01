package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Comentario;
import com.pmi.ispmmx.maya.R;

import java.util.List;

/**
 * Created by chan jacky chan on 16/11/2017.
 */

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {


    private List<Comentario> comentarioList;
    private int idPersona;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public ComentarioAdapter(List<Comentario> comentarioList, int idPersona, int layout, OnItemClickListener listener) {
        this.comentarioList = comentarioList;
        this.layout = layout;
        this.idPersona = idPersona;
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
        holder.blind(comentarioList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return comentarioList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Comentario comentario, int position, View foto);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView _cvRecibido;
        private CardView _cvEnviados;
        private TextView _comentarioRecibido;
        private TextView _comentarioEnviado;
        private TextView _remitente;


        public ViewHolder(View itemView) {
            super(itemView);
            _cvRecibido = itemView.findViewById(R.id.cv_mensaje_recibido);
            _cvEnviados = itemView.findViewById(R.id.cv_mensaje_enviado);
            _comentarioRecibido = itemView.findViewById(R.id.tv_mensaje_recibido);
            _comentarioEnviado = itemView.findViewById(R.id.tv_mensaje_enviado);
            _remitente = itemView.findViewById(R.id.tv_remitente);
        }

        public void blind(final Comentario comentario, final OnItemClickListener listener) {
            if (comentario.getIdComentador() == idPersona) {


                _cvRecibido.setVisibility(View.GONE);
                _cvEnviados.setVisibility(View.VISIBLE);
                _comentarioEnviado.setText(comentario.getOpinion());
            } else {
                _cvRecibido.setVisibility(View.VISIBLE);
                _cvEnviados.setVisibility(View.GONE);
                _comentarioRecibido.setText(comentario.getOpinion());
                _remitente.setText(comentario.getComentador().getNombre() + " " + comentario.getComentador().getApellido1() + " " + comentario.getComentador().getApellido2());
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
