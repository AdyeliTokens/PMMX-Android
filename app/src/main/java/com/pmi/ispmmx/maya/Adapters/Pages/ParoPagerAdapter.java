package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.ActividadEnParoFragment;
import com.pmi.ispmmx.maya.Fragments.DetalleParoFragment;

/**
 * Created by ispmmx on 8/16/17.
 */

public class ParoPagerAdapter extends FragmentStatePagerAdapter {
    private DetalleParoFragment detalleParoFragment;
    private ActividadEnParoFragment actividadEnParoFragment;
    private int numberOfTabs;
    private int idParo;


    public ParoPagerAdapter(FragmentManager fm, int numberOfTabs, DetalleParoFragment detalleParoFragment, ActividadEnParoFragment actividadEnParoFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.detalleParoFragment = detalleParoFragment;
        this.actividadEnParoFragment = actividadEnParoFragment;
        this.idParo = idParo;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return detalleParoFragment;
            case 1:
                return actividadEnParoFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
