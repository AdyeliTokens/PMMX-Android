package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.AreaFragment;


public class RiperPagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private AreaFragment areaFragment;

    public RiperPagerAdapter(FragmentManager fm, int numberOfTabs, AreaFragment areaFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.areaFragment = areaFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return areaFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
