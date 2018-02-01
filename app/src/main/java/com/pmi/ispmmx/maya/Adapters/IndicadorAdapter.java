package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.pmi.ispmmx.maya.Modelos.Entidades.Indicador;
import com.pmi.ispmmx.maya.R;

import java.util.List;

/**
 * Created by chan jacky chan on 28/11/2017.
 */

public class IndicadorAdapter extends RecyclerView.Adapter<IndicadorAdapter.ViewHolder> {


    private List<Indicador> indicadorList;


    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public IndicadorAdapter(List<Indicador> indicadorList, int layout, OnItemClickListener listener) {
        this.indicadorList = indicadorList;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        ViewHolder vh;


        view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        vh = new ViewHolder(view);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(indicadorList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {

        return indicadorList.size();

    }


    public interface OnItemClickListener {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LineChart _chart;
        private TextView _nombreIndicador;


        public ViewHolder(View itemView) {
            super(itemView);
            _chart = itemView.findViewById(R.id.chart);
            _nombreIndicador = itemView.findViewById(R.id.tv_nombre_indicador);
        }

        public void blind(final Indicador indicador, final OnItemClickListener listener) {
            _nombreIndicador.setText(indicador.getNombre());
            /*LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate();*/

        }
    }


}

