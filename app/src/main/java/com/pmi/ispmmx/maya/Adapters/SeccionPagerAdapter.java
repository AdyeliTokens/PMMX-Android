package com.pmi.ispmmx.maya.Adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pmi.ispmmx.maya.CardAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Desperdicio;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.R;

import java.util.ArrayList;
import java.util.List;

public class SeccionPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<ModuloSeccion> mData;
    private float mBaseElevation;
    private Context context;


    private OnItemClickListener itemClickListener;


    public SeccionPagerAdapter(List<ModuloSeccion> moduloSeccions, Context context, SeccionPagerAdapter.OnItemClickListener listener) {
        this.context = context;
        this.mData = moduloSeccions;
        mViews = new ArrayList<>();
        this.itemClickListener = listener;
        for (ModuloSeccion wc : moduloSeccions) {
            mViews.add(null);
        }

        this.itemClickListener = listener;
    }

    public SeccionPagerAdapter(OnItemClickListener listener) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.itemClickListener = listener;
    }

    public void addCardItem(ModuloSeccion item, Context context) {
        this.context = context;
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.cardview_ingresar_desperdicio, container, false);
        container.addView(view);
        bind(mData.get(position), view, itemClickListener);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final ModuloSeccion moduloSeccion, final View itemView, final OnItemClickListener listener) {
        final TextView _nombre;
        final EditText _cantidad;
        final Button _guardar;

        _nombre = itemView.findViewById(R.id.modulo_seccion_title);
        _cantidad = itemView.findViewById(R.id.cantidad_desperdicio);
        _guardar = itemView.findViewById(R.id.btn_guardar);
        _nombre.setText(moduloSeccion.getNombre());

        _guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Desperdicio desperdicio = new Desperdicio();
                String cantidad = _cantidad.getText().toString();
                desperdicio.setCantidad(Integer.parseInt(cantidad));
                desperdicio.setIdSeccion(moduloSeccion.getId());

                GenerarDesperdicio(desperdicio);
            }
        });


    }

    private void GenerarDesperdicio(Desperdicio desperdicio) {

        itemClickListener.OnClickGuardar(desperdicio);

    }

    public interface OnItemClickListener {
        void OnClickGuardar(Desperdicio desperdicio);
    }

}

