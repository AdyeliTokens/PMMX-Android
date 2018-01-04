package com.pmi.ispmmx.maya.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pmi.ispmmx.maya.Adapters.SeccionPagerAdapter;
import com.pmi.ispmmx.maya.CardPagerAdapter;
import com.pmi.ispmmx.maya.Interfaces.IDefectoService;
import com.pmi.ispmmx.maya.Interfaces.IFotoService;
import com.pmi.ispmmx.maya.Interfaces.IParoService;
import com.pmi.ispmmx.maya.Interfaces.IShiftLeaderService;
import com.pmi.ispmmx.maya.Interfaces.IVQIService;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.ModuloSeccion;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.Origen;
import com.pmi.ispmmx.maya.Modelos.Entidades.Maquinaria.WorkCenter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Marca;
import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.ShadowTransformer;
import com.pmi.ispmmx.maya.Utils.Config.HostPreference;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IngresarCantidadDeDesperdicioDialogFragment extends BottomSheetDialogFragment {

    private View _view;
    private CardView _cvTitle;
    private Listener mListener;
    private WorkCenter workCenter;
    private TextView _title;
    private TextView _subTitle;
    private ViewPager mViewPager;
    private SeccionPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    public static IngresarCantidadDeDesperdicioDialogFragment newInstance(WorkCenter workCenter, Marca marca) {
        final IngresarCantidadDeDesperdicioDialogFragment fragment = new IngresarCantidadDeDesperdicioDialogFragment();
        fragment.workCenter = workCenter;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.bottom_sheet_ingresar_cantidad_para_desperdicio, container, false);
        return _view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        elementosUI();
        iniciarAdapter();
        iniciarRecycle();

    }

    private void elementosUI() {
        _cvTitle= _view.findViewById(R.id.cv_title);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(workCenter.getNombreCorto());
        _cvTitle.setBackgroundColor(color);
        _title = _view.findViewById(R.id.title);
        _title.setText(workCenter.getNombre());
        _subTitle = _view.findViewById(R.id.subtitle);
        _subTitle.setText(workCenter.getNombreCorto());



        mViewPager = _view.findViewById(R.id.viewPager);
        mCardAdapter = new SeccionPagerAdapter(new SeccionPagerAdapter.OnItemClickListener() {

        });

        mCardAdapter.addCardItem(new ModuloSeccion(), getContext());
        mCardAdapter.addCardItem(new ModuloSeccion(), getContext());
        mCardAdapter.addCardItem(new ModuloSeccion(), getContext());
        mCardAdapter.addCardItem(new ModuloSeccion(), getContext());

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        //mViewPager.setOffscreenPageLimit(3);
        //mCardShadowTransformer.enableScaling(true);
        //mFragmentCardShadowTransformer.enableScaling(true);


    }

    private void iniciarRecycle() {

    }

    private void iniciarAdapter() {

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

    public interface Listener {

    }



}
