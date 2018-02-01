package com.pmi.ispmmx.maya;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pmi.ispmmx.maya.Adapters.OrigenAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<WorkCenter> mData;
    private float mBaseElevation;
    private Context context;

    private OnItemClickListener itemClickListener;


    public CardPagerAdapter(List<WorkCenter> workCenters, Context context, OnItemClickListener listener) {
        this.context = context;
        this.mData = workCenters;
        mViews = new ArrayList<>();
        this.itemClickListener = listener;
        for (WorkCenter wc : workCenters) {
            mViews.add(null);
        }

        this.itemClickListener = listener;
    }

    public CardPagerAdapter(OnItemClickListener listener) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.itemClickListener = listener;
    }

    public void addCardItem(WorkCenter item, Context context) {
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
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.cardview_workcenter, container, false);
        container.addView(view);
        bind(mData.get(position), view, itemClickListener);
        CardView cardView = view.findViewById(R.id.cv_WorkCenter);

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

    private void bind(WorkCenter workCenter, View itemView, final OnItemClickListener listener) {

        RecyclerView.Adapter mAdapter;
        TextView nombreEstacion;
        ImageView _imgWorkCenter;
        ImageView _imgOpciones;
        TextView _txtTeamLeader;
        RecyclerView _rvOrigenes;
        RecyclerView.LayoutManager mLayoutManager;

        nombreEstacion = itemView.findViewById(R.id.tv_nombre_business_unit);
        _txtTeamLeader = itemView.findViewById(R.id.txtTeamLeader);
        _imgOpciones = itemView.findViewById(R.id.imgOpciones);
        _imgWorkCenter = itemView.findViewById(R.id.img_business_unit);
        _rvOrigenes = itemView.findViewById(R.id.rvOrigenes);


        _txtTeamLeader.setText(String.format("%s %s %s", workCenter.getResponsable().getNombre(), workCenter.getResponsable().getApellido1(), workCenter.getResponsable().getApellido2()));

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(workCenter.getNombreCorto());
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(0)
                .width(270)
                .height(270)
                .bold()
                .toUpperCase()
                .fontSize(60)
                .endConfig()
                .buildRound(workCenter.getNombreCorto(), color);
        _imgWorkCenter.setImageDrawable(drawable);
        nombreEstacion.setText(workCenter.getNombre());

        mAdapter = new OrigenAdapter(workCenter.getOrigenes(), R.layout.cardview_origen, new OrigenAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(Origen origen, int position) {
                listener.OnOrigenClick(origen, position);
            }

            @Override
            public void OnImageItemClick(Origen origen, int position) {
                listener.OnImageOrigenItem(origen, position);
            }

            @Override
            public boolean OnLongClick(Origen origen, int position) {
                listener.OnOrigenLongClick(origen, position);
                return true;
            }

            @Override
            public void OnBadgeDefectoClick(Origen origen, int position) {
                listener.OnBadgeDefectoClick(origen, position);
            }

            @Override
            public void OnBadgeParoClick(Origen origen, int position) {
                listener.OnBadgeParoClick(origen, position);
            }
        });

        mLayoutManager = new LinearLayoutManager(context) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                //initSpruce(_rvOrigenes);
            }
        };
        _rvOrigenes.setHasFixedSize(true);
        _rvOrigenes.setItemAnimator(new DefaultItemAnimator());
        _rvOrigenes.setLayoutManager(mLayoutManager);
        _rvOrigenes.setAdapter(mAdapter);
        _rvOrigenes.setNestedScrollingEnabled(false);
        _rvOrigenes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //if (FAB_Status) {
                //  hideFAB();
                //FAB_Status = false;
                //}
                return false;
            }
        });

        _imgWorkCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.OnItemClick(workCenter, getAdapterPosition());
            }
        });
    }

    public interface OnItemClickListener {
        void OnImageOrigenItem(Origen origen, int position);

        void OnItemClick(WorkCenter workCenter, int position);

        void OnOrigenClick(Origen origen, int position);

        boolean OnOrigenLongClick(Origen origen, int position);

        void OnBadgeDefectoClick(Origen origen, int position);

        void OnBadgeParoClick(Origen origen, int position);
    }

}
