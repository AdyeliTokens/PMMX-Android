package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.BusinessUnitFragment;
import com.pmi.ispmmx.maya.Fragments.MisServiciosFragment;


public class MecanicoPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private MisServiciosFragment misServiciosFragment;
    private BusinessUnitFragment businessUnitFragment;


    public MecanicoPagerAdapter(FragmentManager fm, int numberOfTabs, MisServiciosFragment misServiciosFragment , BusinessUnitFragment businessUnitFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.misServiciosFragment = misServiciosFragment;
        this.businessUnitFragment = businessUnitFragment;


    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return misServiciosFragment;
            case 1:
                return businessUnitFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
