package com.pmi.ispmmx.maya.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmi.ispmmx.maya.Utils.CircleTransform;
import com.pmi.ispmmx.maya.Modelos.Entidades.Marca;
import com.pmi.ispmmx.maya.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pmi.ispmmx.maya.Utils.Config.HostPreference.URL_FOTOS_MARCAS;


public class MarcaAdapter extends RecyclerView.Adapter<MarcaAdapter.ViewHolder> {

    private List<Marca> marcaList;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MarcaAdapter(List<Marca> marcaList, int layout, OnItemClickListener listener) {
        this.marcaList = marcaList;
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
        holder.blind(marcaList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return marcaList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Marca marca);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView _cvMarca;
        private TextView _nombre;
        private ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            _cvMarca = itemView.findViewById(R.id.cv_marca);
            _nombre = itemView.findViewById(R.id.tv_nombre_marca);
            image = itemView.findViewById(R.id.img_foto);
        }

        public void blind(final Marca marca, final OnItemClickListener listener) {
            _nombre.setText(marca.getNombre());
            Picasso.with(context)
                    .load(URL_FOTOS_MARCAS + marca.getId())
                    .transform(new CircleTransform())
                    .error(R.drawable.ic_error_outline_gray)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(image);

            //ColorGenerator generator = ColorGenerator.MATERIAL;
            //_cvEntorno.setCardBackgroundColor(generator.getColor(entorno.getNombre()));
            _cvMarca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(marca);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(marca);
                }
            });
        }
    }
}

