package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.IndicadoresFragment;
import com.pmi.ispmmx.maya.Fragments.LookBookFragment;
import com.pmi.ispmmx.maya.Fragments.WorkCenterFragment;


public class OperadorPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private WorkCenterFragment workCenterFragment;
    private IndicadoresFragment indicadoresFragment;
    private LookBookFragment lookBookFragment;

    public OperadorPagerAdapter(FragmentManager fm, int numberOfTabs,LookBookFragment lookBookFragment,IndicadoresFragment indicadoresFragment, WorkCenterFragment workCenterFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.workCenterFragment = workCenterFragment;
        this.indicadoresFragment=indicadoresFragment;
        this.lookBookFragment = lookBookFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return lookBookFragment;
            case 1:
                return indicadoresFragment;
            case 2:
                return workCenterFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
