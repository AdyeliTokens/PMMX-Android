package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pmi.ispmmx.maya.Adapters.MarcaAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Marca;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class MarcasDialogFragment extends BottomSheetDialogFragment {

    private View _view;
    private CardView _cvTitle;
    private TextView _title;
    private TextView _subTitle;

    private Listener mListener;

    private List<Marca> marcasList;
    private WorkCenter workCenter;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView _rvLista;
    private RecyclerView.LayoutManager _mLayout;

    public static MarcasDialogFragment newInstance(WorkCenter workCenter, List<Marca> entornoList) {
        final MarcasDialogFragment fragment = new MarcasDialogFragment();
        fragment.marcasList = entornoList;
        fragment.workCenter = workCenter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.bottom_sheet_marcas, container, false);
        return _view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        elementosUI();
        iniciarAdapter();
        iniciarRecycle();

    }

    private void elementosUI() {
        _cvTitle = _view.findViewById(R.id.cv_title);
        _rvLista = _view.findViewById(R.id.rv_marcas);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(workCenter.getNombreCorto());
        _cvTitle.setCardBackgroundColor(color);
        _title = _view.findViewById(R.id.title);
        _title.setText(workCenter.getNombre());
        _subTitle = _view.findViewById(R.id.subtitle);
        _subTitle.setText(workCenter.getNombreCorto());
    }

    private void iniciarRecycle() {
        _mLayout = new LinearLayoutManager(_view.getContext());
        _rvLista.setHasFixedSize(false);
        _rvLista.setItemAnimator(new DefaultItemAnimator());
        _rvLista.setLayoutManager(_mLayout);
        _rvLista.setNestedScrollingEnabled(false);
        _rvLista.setAdapter(mAdapter);
    }

    private void iniciarAdapter() {
        mAdapter = new MarcaAdapter(marcasList, R.layout.cardview_marca, new MarcaAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Marca marca) {
                mListener.onMarcaClicked(workCenter, marca);
                cerrar();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    private void cerrar() {
        this.dismiss();
    }

    public interface Listener {
        void onMarcaClicked(WorkCenter workCenter, Marca marca);
    }
}
