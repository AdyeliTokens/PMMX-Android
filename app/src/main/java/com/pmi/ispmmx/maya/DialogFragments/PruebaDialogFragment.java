package com.pmi.ispmmx.maya.DialogFragments;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.R;

/**
 * Created by chan jacky chan on 21/11/2017.
 */

public class PruebaDialogFragment extends BottomSheetDialogFragment {
    public static PruebaDialogFragment newInstance() {
        PruebaDialogFragment frag = new PruebaDialogFragment();

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View _view = inflater.inflate(R.layout.bottom_sheet_paros_activos, container, false);


        return _view;
    }
}
