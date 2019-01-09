package com.dodo.xinyue.conan.main.index.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dodo.xinyue.conan.main.index.IndexContentDelegate;

/**
 * IndexPagerAdapter
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;
    private String[] mUrls;

    public IndexPagerAdapter(FragmentManager fm, String[] titles, String[] urls) {
        super(fm);
        this.mTitles = titles;
        this.mUrls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        return IndexContentDelegate.create(mUrls[position]);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
