package com.pmi.ispmmx.maya.Adapters.Paros;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.widget.CardView;
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
 * Created by ispmmx on 7/18/17.
 */

public class ParoActivoAdapter extends RecyclerView.Adapter<ParoActivoAdapter.ViewHolder> {
    private List<Paro> paroList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public ParoActivoAdapter(Context context , List<Paro> paros, int layout, OnItemClickListener listener) {
        this.context=context;
        this.paroList = paros;
        this.layout = layout;
        this.itemClickListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder vh;


        view = LayoutInflater.from(context).inflate(layout,parent , false);
        vh = new ViewHolder(view);


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


    public interface OnItemClickListener {
        void OnItemClick(Paro paro, int position);

        boolean OnItemLongClick(Paro paro, int position);

        void OnOpcionClick(Paro paro, int position, View view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Origen;
        public TextView reportador;
        public TextView encargado;
        public TextView fechaReporte;
        public TextView _activo;
        public ImageView imgOpciones;
        public TextView txtmensaje;
        public ImageView fondoCronometro;
        public ImageView imgWorkCenter;
        public ImageView imgModulo;
        public CardView cvParo;
        public CardView cvMensaje;
        public Chronometer time;
        public ImageView tool;


        public ViewHolder(View itemView) {
            super(itemView);

            imgOpciones = itemView.findViewById(R.id.imgOpciones);
            fondoCronometro = itemView.findViewById(R.id.img_fondoCronometro);
            reportador = itemView.findViewById(R.id.txt_reportador);
            fechaReporte = itemView.findViewById(R.id.txt_fechaReportado);
            imgWorkCenter = itemView.findViewById(R.id.img_workCenter);
            imgModulo = itemView.findViewById(R.id.img_modulo);
            time = itemView.findViewById(R.id.watchTime);
            encargado = itemView.findViewById(R.id.txt_Mecanico);
            _activo = itemView.findViewById(R.id.txt_indicador);
            txtmensaje = itemView.findViewById(R.id.txtMensaje);
            cvParo = itemView.findViewById(R.id.cv_Paro);
            cvMensaje = itemView.findViewById(R.id.cv_Mensaje);
            tool = itemView.findViewById(R.id.imgTool);
        }

        public void blind(final Paro paro, final OnItemClickListener listener) {

                cvParo.setVisibility(View.VISIBLE);
                cvMensaje.setVisibility(View.GONE);


                String nombreCompleto = paro.getReportador().getNombre() + " " + paro.getReportador().getApellido1() + " " + paro.getReportador().getApellido2();
                String modulo = paro.getOrigen().getModulo().getNombreCorto();
                String workCenter = paro.getOrigen().getWorkCenter().getNombreCorto();


                long lastSuccess = paro.getFechaReporte().getTime(); //Some Date object
                long elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
                time.setBase(lastSuccess - elapsedRealtimeOffset);

                _activo.setVisibility(View.GONE);
                if (paro.getActivo()) {
                    _activo.setBackgroundResource(R.color.colorRedScale7);
                    time.start();
                } else {
                    _activo.setBackgroundResource(R.color.colorPrimary);
                }

                encargado.setVisibility(View.GONE);
                if (paro.getIdMecanico() > 0) {
                    tool.setVisibility(View.VISIBLE);

                    //int nombreMecanico = paro.getIdMecanico();
                    //this.encargado.setText("" + nombreMecanico);
                } else {
                    tool.setVisibility(View.GONE);
                    //this.encargado.setText("");
                }


                TextDrawable colorFondo = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(0)
                        .width(70)  // width in px
                        .height(70)
                        .bold()
                        .toUpperCase()
                        .fontSize(15)/* thickness in px */
                        .endConfig()
                        .buildRound("", Color.RED);

                fondoCronometro.setImageDrawable(colorFondo);


                ColorGenerator generatorWorkCenter = ColorGenerator.MATERIAL;
                int colorWorkCenter = generatorWorkCenter.getColor(workCenter);
                TextDrawable drawableWorkCenter = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(0)
                        .width(270)  // width in px
                        .height(100)
                        .bold()
                        .toUpperCase()
                        .fontSize(50)/* thickness in px */
                        .endConfig()
                        .buildRoundRect(workCenter, colorWorkCenter, 100);
                imgWorkCenter.setImageDrawable(drawableWorkCenter);


                ColorGenerator generatorModulo = ColorGenerator.MATERIAL;
                int colorModulo = generatorModulo.getColor(modulo);
                TextDrawable drawableModulo = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(0)
                        .width(270)  // width in px
                        .height(100)
                        .bold()
                        .toUpperCase()
                        .fontSize(35)/* thickness in px */
                        .endConfig()
                        .buildRoundRect(modulo, colorModulo, 100);
                imgModulo.setImageDrawable(drawableModulo);


                this.reportador.setText(nombreCompleto);
                this.fechaReporte.setText("" + paro.getFechaApiReporte());


                cvParo.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        listener.OnItemLongClick(paro, getAdapterPosition());
                        return true;
                    }
                });
                cvParo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.OnItemClick(paro, getAdapterPosition());
                    }
                });
                imgOpciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.OnOpcionClick(paro, getAdapterPosition(), imgOpciones);
                    }
                });


        }
    }
}


