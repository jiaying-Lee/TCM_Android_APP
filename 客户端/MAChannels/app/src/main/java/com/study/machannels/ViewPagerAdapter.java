package com.study.machannels;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentList.get(position);

        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
