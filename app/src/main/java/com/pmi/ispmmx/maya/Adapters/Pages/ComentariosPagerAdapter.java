package com.pmi.ispmmx.maya.Adapters.Pages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pmi.ispmmx.maya.Fragments.ComentariosFragment;

/**
 * Created by chan jacky chan on 16/11/2017.
 */

public class ComentariosPagerAdapter extends FragmentStatePagerAdapter {
    private ComentariosFragment comentariosFragment;
    private int numberOfTabs;



    public ComentariosPagerAdapter(FragmentManager fm, int numberOfTabs, ComentariosFragment comentariosFragment) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.comentariosFragment = comentariosFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return comentariosFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
