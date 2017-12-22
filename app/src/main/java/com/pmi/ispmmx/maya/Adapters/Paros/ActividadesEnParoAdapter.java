package com.pmi.ispmmx.maya.Adapters.Paros;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.vipulasri.timelineview.TimelineView;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.ActividadEnParo;
import com.pmi.ispmmx.maya.R;

import java.util.List;

/**
 * Created by ispmmx on 8/14/17.
 */

public class ActividadesEnParoAdapter extends RecyclerView.Adapter<ActividadesEnParoAdapter.ViewHolder> {

    private List<ActividadEnParo> actividadEnParoList;
    private int layout;
    private OnItemClickListener itemClickListener;

    public ActividadesEnParoAdapter(List<ActividadEnParo> feedList, int layout, OnItemClickListener listener) {
        actividadEnParoList = feedList;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(view);

        return (ViewHolder) vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(actividadEnParoList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return (actividadEnParoList != null ? actividadEnParoList.size() : 0);
    }

    public interface OnItemClickListener {
        void OnItemClick(ActividadEnParo actividadEnParo, int position);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Origen;
        public TextView reportador;
        public TextView encargado;
        public TextView fechaReporte;
        public TextView tiempoAsignacion;
        public TextView tiempoParo;
        public TextView descripcion;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            reportador = itemView.findViewById(R.id.txt_reportador);
            fechaReporte = itemView.findViewById(R.id.txt_fechaReportado);
            image = itemView.findViewById(R.id.img_workCenter);

        }

        public void blind(final ActividadEnParo actividadEnParo, final OnItemClickListener listener) {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor("MAGOMAT ");
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(2)
                    .width(60)  // width in px
                    .height(60)
                    .bold()
                    .toUpperCase()
                    .fontSize(10)/* thickness in px */
                    .endConfig()
                    .buildRound("MAGOMAT", color);


            image.setImageDrawable(drawable);


            //this.reportador.setText(""+actividadEnParo.getEjecutante());
            //this.fechaReporte.setText(""+actividadEnParo.getFecha());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(actividadEnParo, getAdapterPosition());
                }
            });
        }
    }
}
