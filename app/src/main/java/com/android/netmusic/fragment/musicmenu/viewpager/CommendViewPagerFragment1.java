package com.android.netmusic.fragment.musicmenu.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.netmusic.R;

/**
 * Created by jiaoml on 17-4-18.
 */

public class CommendViewPagerFragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.fragment_musicmenu_commend_viewpager_fragment1,null);
        return view;
    }
}
