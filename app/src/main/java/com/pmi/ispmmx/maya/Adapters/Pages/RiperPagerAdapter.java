package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.BusinessUnitFragment;
import com.pmi.ispmmx.maya.Fragments.IndicadoresFragment;
import com.pmi.ispmmx.maya.Fragments.LookBookFragment;
import com.pmi.ispmmx.maya.Fragments.WorkCenterFragment;


public class RiperPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private BusinessUnitFragment businessUnitFragment;

    public RiperPagerAdapter(FragmentManager fm, int numberOfTabs, BusinessUnitFragment businessUnitFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.businessUnitFragment = businessUnitFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
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
