package com.pmi.ispmmx.maya.Adapters.CardViews;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pmi.ispmmx.maya.Adapters.OrigenAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class WorkCenterAdapter extends RecyclerView.Adapter<WorkCenterAdapter.ViewHolder> {

    private List<WorkCenter> workCenterList;

    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public WorkCenterAdapter(List<WorkCenter> workCenters, int layout, OnItemClickListener listener) {
        this.workCenterList = workCenters;
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
        holder.blind(workCenterList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {

        return workCenterList.size();

    }


    public interface OnItemClickListener {
        void OnItemClick(WorkCenter workCenter, int position);

        void OnOrigenClick(Origen origen, int position);

        boolean OnOrigenLongClick(Origen origen, int position);

        void OnBadgeDefectoClick(Origen origen, int position);

        void OnBadgeParoClick(Origen origen, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView.Adapter mAdapter;
        private TextView nombreEstacion;
        private ImageView _imgWorkCenter;
        private ImageView _imgOpciones;
        private TextView _txtTeamLeader;
        private RecyclerView _rvOrigenes;
        private RecyclerView.LayoutManager mLayoutManager;


        public ViewHolder(View itemView) {
            super(itemView);
            nombreEstacion = itemView.findViewById(R.id.tv_nombre_business_unit);
            _txtTeamLeader = itemView.findViewById(R.id.txtTeamLeader);
            _imgOpciones = itemView.findViewById(R.id.imgOpciones);
            _imgWorkCenter = itemView.findViewById(R.id.img_business_unit);
            _rvOrigenes = itemView.findViewById(R.id.rvOrigenes);
        }

        public void blind(final WorkCenter workCenter, final OnItemClickListener listener) {

            _txtTeamLeader.setText(String.format("%s %s %s", workCenter.getResponsable().getNombre(), workCenter.getResponsable().getApellido1(), workCenter.getResponsable().getApellido2()));

            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(workCenter.getNombreCorto());
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(0)
                    .width(270)  // width in px
                    .height(270)
                    .bold()
                    .toUpperCase()
                    .fontSize(60)/* thickness in px */
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


            _imgWorkCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(workCenter, getAdapterPosition());
                }
            });

        }
    }


}
