package com.dessiapp.adapter;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;

import com.dessiapp.screen.ForYouFragment;
import com.dessiapp.screen.PeoplesFragment;
import com.dessiapp.screen.ProfileFragment1;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ForYouFragment homeFragment = new ForYouFragment();
                return homeFragment;
            case 1:
                PeoplesFragment sportFragment = new PeoplesFragment();
                return sportFragment;
            case 2:
                ProfileFragment1 movieFragment = new ProfileFragment1();
                return movieFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}