package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Modelos.Entidades.Entorno;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.CircleTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_ENTORNOS;

public class EntornoAdapter extends RecyclerView.Adapter<EntornoAdapter.ViewHolder> {


    private List<Entorno> entornoList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public EntornoAdapter(List<Entorno> entornoList, int layout, OnItemClickListener listener) {
        this.entornoList = entornoList;
        this.layout = layout;
        this.itemClickListener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        this.context = parent.getContext();

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.blind(entornoList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return entornoList.size();
    }


    public interface OnItemClickListener {
        void OnItemClick(Entorno entorno);

        void OnClickDelete(Entorno entorno);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView _cvEntorno;
        private TextView _nombre;
        private ImageView image;
        private ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            _cvEntorno = itemView.findViewById(R.id.cv_entorno);
            _nombre = itemView.findViewById(R.id.tv_nombre_entorno);
            image = itemView.findViewById(R.id.img_foto);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }

        public void blind(final Entorno entorno, final OnItemClickListener listener) {
            Picasso.with(context)
                    .load(URL_FOTOS_ENTORNOS + entorno.getId())
                    .transform(new CircleTransform())
                    .error(R.drawable.ic_error_outline_gray)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(image);

            _nombre.setText(entorno.getNombre());
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClickDelete(entorno);
                }
            });
            _cvEntorno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(entorno);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(entorno);
                }
            });
        }
    }
}
