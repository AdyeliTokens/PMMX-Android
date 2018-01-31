package com.pmi.ispmmx.maya.Adapters.Pages;



import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.WorkCenterFragment;


public class ShiftLeaderPageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private WorkCenterFragment workCenterFragment;

    public ShiftLeaderPageAdapter(FragmentManager fm, int numberOfTabs, WorkCenterFragment workCenterFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.workCenterFragment = workCenterFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
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