package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.FeedFragment;
import com.pmi.ispmmx.maya.Fragments.IndicadoresFragment;
import com.pmi.ispmmx.maya.Fragments.WorkCenterFragment;


public class OperadorPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private WorkCenterFragment workCenterFragment;
    private IndicadoresFragment indicadoresFragment;
    private FeedFragment feedFragment;

    public OperadorPagerAdapter(FragmentManager fm, int numberOfTabs, FeedFragment feedFragment, IndicadoresFragment indicadoresFragment, WorkCenterFragment workCenterFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.workCenterFragment = workCenterFragment;
        this.indicadoresFragment=indicadoresFragment;
        this.feedFragment = feedFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return feedFragment;
            case 1:
                return workCenterFragment ;
            case 2:
                return indicadoresFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
