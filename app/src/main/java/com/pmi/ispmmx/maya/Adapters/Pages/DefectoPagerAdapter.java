package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.DetalleDefectoFragment;

/**
 * Created by ispmmx on 8/24/17.
 */

public class DefectoPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private DetalleDefectoFragment detalle;


    public DefectoPagerAdapter(FragmentManager fm, int numberOfTabs, DetalleDefectoFragment detalle) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.detalle = detalle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return detalle;
            case 1:
                //ActividadEnParoFragment actividadEnParoFragment = new ActividadEnParoFragment();

                return null;
            /*case 2:
                DefectosFragment defectosFragment = new DefectosFragment();
                Bundle defectosArgs = new Bundle();
                defectosArgs.putInt("idWorkCenter", idWorkCenter);
                defectosFragment.setArguments(defectosArgs);

                return null;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}