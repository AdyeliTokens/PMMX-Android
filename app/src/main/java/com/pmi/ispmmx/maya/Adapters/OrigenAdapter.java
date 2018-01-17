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

import com.pmi.ispmmx.maya.Utils.CircleTransform;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_ORIGENES;

/**
 * Created by chan jacky chan on 15/09/2017.
 */


public class OrigenAdapter extends RecyclerView.Adapter<OrigenAdapter.ViewHolder> {

    private List<Origen> origenList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public OrigenAdapter(List<Origen> origenList, int layout, OnItemClickListener listener) {
        Collections.sort(origenList, new Comparator<Origen>() {

            @Override
            public int compare(Origen origen, Origen t1) {

                return origen.getOrden() - t1.getOrden();
            }
        });
        this.origenList = origenList;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(origenList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return origenList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Origen origen, int position);
        boolean OnLongClick(Origen origen, int position);

        void OnBadgeDefectoClick(Origen origen, int position);
        void OnBadgeParoClick(Origen origen,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private ImageView imgOpciones;
        private CardView cvOrigen;
        private CardView badgeParos;
        private CardView badgeDefectos;
        private TextView nombreOrigen;
        private TextView numParos;
        private TextView numDefectos;
        private TextView seccion;




        public ViewHolder(View itemView) {
            super(itemView);
            badgeParos = itemView.findViewById(R.id.cv_NumParos);
            badgeDefectos = itemView.findViewById(R.id.cv_NumDefectos);
            cvOrigen = itemView.findViewById(R.id.cv_Origen);
            nombreOrigen = itemView.findViewById(R.id.txtNombreOrigen);
            numParos = itemView.findViewById(R.id.txt_num_paros);
            numDefectos = itemView.findViewById(R.id.txt_num_defectos);
            imgOpciones =  itemView.findViewById(R.id.img_opciones);
            seccion = itemView.findViewById(R.id.txt_session_modulo);
            layout = itemView.findViewById(R.id.layoutOrigen);

        }

        public void blind(final Origen origen, final OnItemClickListener listener) {
            Picasso.with(context)
                    .load(URL_FOTOS_ORIGENES + origen.getId())
                    .transform(new CircleTransform())
                    .error(R.drawable.ic_error_outline_gray)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(imgOpciones);

            nombreOrigen.setText(origen.getModulo().getNombreCorto());
            if(origen.getModulo().getSeccion()!= null){
                seccion.setText(origen.getModulo().getSeccion().getNombre());
            }else{
                seccion.setText("Sin seccion asignada!!");;
            }

            if(origen.getDefectosActivos()>0){
                numDefectos.setText("" + origen.getDefectosActivos() + "");
                badgeDefectos.setVisibility(View.VISIBLE);
            }
            else{
                badgeDefectos.setVisibility(View.GONE);
            }

            if (origen.getParosActivos() > 0) {
                badgeParos.setVisibility(View.VISIBLE);
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorRedScale7));
                nombreOrigen.setTextColor(context.getResources().getColor(R.color.colorWhite));
                numParos.setText("" + origen.getParosActivos() + "");
                badgeParos.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                numParos.setTextColor(context.getResources().getColor(R.color.colorRedScale7));
            } else {
                badgeParos.setVisibility(View.GONE);
            }

            imgOpciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            badgeParos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnBadgeParoClick(origen, getAdapterPosition());
                }
            });
            badgeDefectos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnBadgeDefectoClick(origen, getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.OnLongClick(origen, getAdapterPosition());
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(origen, getAdapterPosition());
                }
            });


        }
    }


}