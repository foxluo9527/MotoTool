package com.motoll.one.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;//fragment管理员
    private FragmentManager fm;//fragment数组

    /**
     * 构造函数
     * @param fm fragment管理员对象
     */
    public TabPageAdapter(FragmentManager fm) {
        super(fm);
        this.fm=fm;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    public void select() {

    }

}