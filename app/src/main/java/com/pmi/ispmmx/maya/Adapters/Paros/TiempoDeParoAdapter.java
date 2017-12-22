package com.pmi.ispmmx.maya.Adapters.Paros;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.TiempoDeParo;
import com.pmi.ispmmx.maya.R;

import java.util.Date;
import java.util.List;

/**
 * Created by chan jacky chan on 30/08/2017.
 */

public class TiempoDeParoAdapter extends RecyclerView.Adapter<TiempoDeParoAdapter.ViewHolder> {

    private List<TiempoDeParo> tiempoDeParos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public TiempoDeParoAdapter(List<TiempoDeParo> paros, int layout, OnItemClickListener listener) {
        this.tiempoDeParos = paros;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(tiempoDeParos.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return tiempoDeParos.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(TiempoDeParo tiempoDeParo, int position);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView fondoCronometro;
        public Chronometer time;


        public ViewHolder(View itemView) {
            super(itemView);
            fondoCronometro = itemView.findViewById(R.id.img_tiempoParo);
            time = itemView.findViewById(R.id.watchTime);
        }

        public void blind(final TiempoDeParo tiempoDeParo, final OnItemClickListener listener) {
            long lastSuccess = tiempoDeParo.getInicio().getTime();
            long elapsedRealtimeOffset;
            int color;

            try {
                Date fin = tiempoDeParo.getFin();

                elapsedRealtimeOffset = fin.getTime() - SystemClock.elapsedRealtime();
                time.setBase(lastSuccess - elapsedRealtimeOffset);
                color = context.getResources().getColor(R.color.colorGrayScale6);
            } catch (Exception e) {
                elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
                time.setBase(lastSuccess - elapsedRealtimeOffset);
                time.start();
                color = context.getResources().getColor(R.color.colorRedScale7);
            }


            TextDrawable colorFondo = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(0)
                    .bold()
                    .toUpperCase()
                    .fontSize(15)
                    .endConfig()
                    .buildRound("", color);

            fondoCronometro.setImageDrawable(colorFondo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(tiempoDeParo, getAdapterPosition());
                }
            });
        }
    }
}

