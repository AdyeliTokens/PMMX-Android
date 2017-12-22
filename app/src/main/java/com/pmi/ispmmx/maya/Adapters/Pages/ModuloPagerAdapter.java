package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.DefectosFragment;
import com.pmi.ispmmx.maya.Fragments.ParosFragment;


public class ModuloPagerAdapter extends FragmentStatePagerAdapter {
    ////Fragment
    private ParosFragment parosFragment;
    private DefectosFragment defectosFragment;
    private int numberOfTabs;


    public ModuloPagerAdapter(FragmentManager fm, int numberOfTabs, ParosFragment parosFragment, DefectosFragment defectosFragment) {
        super(fm);
        this.parosFragment = parosFragment;
        this.defectosFragment = defectosFragment;
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return parosFragment;
            case 1:
                return defectosFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
