package com.pmi.ispmmx.maya.Adapters.Defectos;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_DEFECTOS;

/**
 * Created by ispmmx on 7/18/17.
 */

public class DefectoAdapter extends RecyclerView.Adapter<DefectoAdapter.ViewHolder> {

    private List<Defecto> defectoList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public DefectoAdapter(List<Defecto> defectos, int layout, OnItemClickListener listener) {
        this.defectoList = defectos;
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
        public TextView defecto;
        public TextView fecha;
        public ImageView imagen;
        public TextView tvPrioridad;


        public ViewHolder(View itemView) {
            super(itemView);
            defecto = itemView.findViewById(R.id.txt_Descripcion);
            fecha = itemView.findViewById(R.id.txt_Fecha);
            imagen = itemView.findViewById(R.id.imgDefecto);
            tvPrioridad = itemView.findViewById(R.id.txtPrioridad);
        }

        public void blind(final Defecto defecto, final OnItemClickListener listener) {
            if (defecto.getPrioridad() == 3) {
                tvPrioridad.setBackgroundColor(Color.RED);
            } else if (defecto.getPrioridad() == 2) {
                tvPrioridad.setBackgroundColor(Color.YELLOW);
            } else if (defecto.getPrioridad() == 1) {
                tvPrioridad.setBackgroundColor(Color.BLUE);
            }



            Picasso.with(context)
                    .load(URL_FOTOS_DEFECTOS + defecto.getId())
                    .fit()
                    .centerCrop()
                    //.placeholder(R.drawable.ic_watch_later_gray)
                    .error(R.drawable.ic_error_outline_gray)
                    .into(imagen);


            //String modulo = defecto.getOrigen().getModulo().getNombre();
            //String nombreCompleto = defecto.getReportador().getNombre() + " " + defecto.getReportador().getApellido1() + " " + defecto.getReportador().getApellido2();
            String descripcion = defecto.getDescripcion();

            this.defecto.setText(descripcion);
            this.fecha.setText("" + defecto.getFechaApiReporte());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(defecto, getAdapterPosition(), imagen);
                }
            });
        }
    }
}
