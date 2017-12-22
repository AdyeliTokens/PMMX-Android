package com.pmi.ispmmx.maya.Adapters.Paros;

import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;

import java.util.List;

/**
 * Created by ispmmx on 8/29/17.
 */

public class ParoCerradoAdapter extends RecyclerView.Adapter<ParoCerradoAdapter.ViewHolder> {

    private List<Paro> paroList;
    private int layout;
    private OnItemClickListener itemClickListener;


    public ParoCerradoAdapter(List<Paro> paros, int layout, OnItemClickListener listener) {
        this.paroList = paros;
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
        holder.blind(paroList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return paroList.size();
    }

    private TextDrawable getDrawableforImage(String descripcion) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(descripcion);
        TextDrawable drawableWorkCenter = TextDrawable.builder()
                .beginConfig()
                .withBorder(0)
                .width(70)  // width in px
                .height(70)
                .bold()
                .toUpperCase()
                .fontSize(15)/* thickness in px */
                .endConfig()
                .buildRound(descripcion, color);

        return drawableWorkCenter;
    }

    public interface OnItemClickListener {
        void OnItemClick(Paro paro, int position);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fecha;
        public ImageView fondoCronometro;
        public ImageView imgWorkCenter;
        public ImageView imgModulo;
        public Chronometer time;

        public ViewHolder(View itemView) {
            super(itemView);
            fondoCronometro = itemView.findViewById(R.id.img_tiempoParo);
            imgWorkCenter = itemView.findViewById(R.id.img_workCenter);
            imgModulo = itemView.findViewById(R.id.img_modulo);
            time = itemView.findViewById(R.id.watchTime);
            fecha = itemView.findViewById(R.id.txt_Fecha);
        }

        public void blind(final Paro paro, final OnItemClickListener listener) {
            String modulo = paro.getOrigen().getModulo().getNombreCorto();
            String workCenter = paro.getOrigen().getWorkCenter().getNombreCorto();


            long lastSuccess = paro.getFechaApiReporte().getTime(); //Some Date object
            long elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            time.setBase(lastSuccess - elapsedRealtimeOffset);


            TextDrawable colorFondo = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(0)
                    .bold()
                    .toUpperCase()
                    .fontSize(15)
                    .endConfig()
                    .buildRound("", R.color.colorRedScale7);

            fondoCronometro.setImageDrawable(colorFondo);


            ColorGenerator generatorWorkCenter = ColorGenerator.MATERIAL;
            int colorWorkCenter = generatorWorkCenter.getColor(workCenter);
            TextDrawable drawableWorkCenter = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(0)
                    .bold()
                    .toUpperCase()
                    .fontSize(50)
                    .endConfig()
                    .buildRoundRect(workCenter, colorWorkCenter, 100);
            imgWorkCenter.setImageDrawable(drawableWorkCenter);


            ColorGenerator generatorModulo = ColorGenerator.MATERIAL;
            int colorModulo = generatorModulo.getColor(modulo);
            TextDrawable drawableModulo = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(0)
                    .bold()
                    .toUpperCase()
                    .fontSize(35)/* thickness in px */
                    .endConfig()
                    .buildRoundRect(modulo, colorModulo, 100);
            imgModulo.setImageDrawable(drawableModulo);

            fecha.setText("" + paro.getFechaApiReporte());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(paro, getAdapterPosition());
                }
            });
        }
    }
}
