package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
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
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.BussinesUnit;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class BusinessUnitAdapter extends RecyclerView.Adapter<BusinessUnitAdapter.ViewHolder> {


    private List<BussinesUnit> bussinesUnitList;


    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public BusinessUnitAdapter(List<BussinesUnit> bussinesUnitList, int layout, OnItemClickListener listener) {
        this.bussinesUnitList = bussinesUnitList;
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
        holder.blind(bussinesUnitList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {

        return bussinesUnitList.size();

    }


    public interface OnItemClickListener {
        void OnItemClick(BussinesUnit bussinesUnit, int position);

        void OnWorkCenterClick(WorkCenter workCenter, int position);

        boolean OnWorkCenterLongClick(WorkCenter workCenter, int position);

        void OnBadgeDefectoClick(WorkCenter workCenter, int position);

        void OnBadgeParoClick(WorkCenter workCenter, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView.Adapter mAdapter;
        private TextView _nombreBusinessUnit;
        private ImageView _imgBusinessUnit;
        private ImageView _imgOpciones;
        private TextView _nombreBUM;
        private RecyclerView _rvWorkCenters;
        private RecyclerView.LayoutManager mLayoutManager;


        public ViewHolder(View itemView) {
            super(itemView);
            _nombreBusinessUnit = itemView.findViewById(R.id.tv_nombre_business_unit);
            _nombreBUM = itemView.findViewById(R.id.tv_bum);
            _imgOpciones = itemView.findViewById(R.id.imgOpciones);
            _imgBusinessUnit = itemView.findViewById(R.id.img_business_unit);
            _rvWorkCenters = itemView.findViewById(R.id.rv_work_center);

        }

        public void blind(final BussinesUnit bussinesUnit, final OnItemClickListener listener) {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(bussinesUnit.getNombreCorto());
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(0)
                    .width(270)  // width in px
                    .height(270)
                    .bold()
                    .toUpperCase()
                    .fontSize(60)/* thickness in px */
                    .endConfig()
                    .buildRound(bussinesUnit.getNombreCorto(), color);
            _imgBusinessUnit.setImageDrawable(drawable);
            _nombreBusinessUnit.setText(bussinesUnit.getNombre());


            mAdapter = new LinkUpAdapter(bussinesUnit.getWorkCenterList(), R.layout.cardview_link_up, new LinkUpAdapter.OnItemClickListener() {

                @Override
                public void OnItemClick(WorkCenter workCenter, int position) {
                    listener.OnWorkCenterClick(workCenter, position);
                }

                @Override
                public boolean OnLongClick(WorkCenter workCenter, int position) {
                    listener.OnWorkCenterLongClick(workCenter, position);
                    return true;
                }

                @Override
                public void OnBadgeDefectoClick(WorkCenter workCenter, int position) {
                    listener.OnBadgeDefectoClick(workCenter, position);
                }

                @Override
                public void OnBadgeParoClick(WorkCenter workCenter, int position) {
                    listener.OnBadgeParoClick(workCenter, position);
                }
            });

            mLayoutManager = new LinearLayoutManager(context);
            _rvWorkCenters.setHasFixedSize(true);
            _rvWorkCenters.setItemAnimator(new DefaultItemAnimator());
            _rvWorkCenters.setLayoutManager(mLayoutManager);
            _rvWorkCenters.setAdapter(mAdapter);
            _rvWorkCenters.setNestedScrollingEnabled(false);
            _rvWorkCenters.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });

            _imgBusinessUnit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(bussinesUnit, getAdapterPosition());
                }
            });

        }
    }


}

