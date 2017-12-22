package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.R;

import java.util.List;

public class LinkUpAdapter extends RecyclerView.Adapter<LinkUpAdapter.ViewHolder> {

    private List<WorkCenter> workCenterList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public LinkUpAdapter(List<WorkCenter> origenList, int layout, OnItemClickListener listener) {

        this.workCenterList = origenList;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(view);


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

        boolean OnLongClick(WorkCenter workCenter, int position);

        void OnBadgeDefectoClick(WorkCenter workCenter, int position);

        void OnBadgeParoClick(WorkCenter workCenter, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView _imgOpciones;
        private CardView _cvLinkUp;
        private TextView _nombreLinkUp;
        private TextView _numParos;
        private TextView _numDefectos;
        private ConstraintLayout _layout;
        private CardView _badgeParos;
        private CardView _badgeDefectos;


        public ViewHolder(View itemView) {
            super(itemView);
            _badgeParos = itemView.findViewById(R.id.cv_NumParos);
            _badgeDefectos = itemView.findViewById(R.id.cv_NumDefectos);
            _cvLinkUp = itemView.findViewById(R.id.cv_link_up);
            _nombreLinkUp = itemView.findViewById(R.id.tv_nombre);
            _numParos = itemView.findViewById(R.id.txt_num_paros);
            _numDefectos = itemView.findViewById(R.id.txt_num_defectos);
            _imgOpciones = itemView.findViewById(R.id.img_opciones);

            _layout = itemView.findViewById(R.id.layoutOrigen);

        }

        public void blind(final WorkCenter workCenter, final OnItemClickListener listener) {
            _nombreLinkUp.setText(workCenter.getNombreCorto());
            if (workCenter.getDefectosActivos() > 0) {
                _numDefectos.setText("" + workCenter.getDefectosActivos() + "");
                _badgeDefectos.setVisibility(View.VISIBLE);
            } else {
                _badgeDefectos.setVisibility(View.GONE);
            }

            if (workCenter.getParosActivos() > 0) {
                _badgeParos.setVisibility(View.VISIBLE);
                _layout.setBackgroundColor(context.getResources().getColor(R.color.colorRedScale7));
                _nombreLinkUp.setTextColor(context.getResources().getColor(R.color.colorWhite));
                _numParos.setText("" + workCenter.getParosActivos() + "");
                _badgeParos.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                _numParos.setTextColor(context.getResources().getColor(R.color.colorRedScale7));
            } else {
                _badgeParos.setVisibility(View.GONE);
            }

            _imgOpciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            _badgeParos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnBadgeParoClick(workCenter, getAdapterPosition());
                }
            });
            _badgeDefectos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnBadgeDefectoClick(workCenter, getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.OnLongClick(workCenter, getAdapterPosition());
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(workCenter, getAdapterPosition());
                }
            });


        }
    }

}
