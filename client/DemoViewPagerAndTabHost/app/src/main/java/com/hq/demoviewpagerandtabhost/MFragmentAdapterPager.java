package com.hq.demoviewpagerandtabhost;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by PC14-01 on 10/11/2016.
 */

public class MFragmentAdapterPager extends FragmentPagerAdapter {
    List<Fragment> list;

    public MFragmentAdapterPager(List<Fragment> list, FragmentManager fm) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
