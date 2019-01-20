package com.dodo.xinyue.conan.module.message.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dodo.xinyue.conan.module.message.MessageCenterContentDelegate;

/**
 * MessageCenterPagerAdapter
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageCenterPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;
    private int[] mTypes;

    public MessageCenterPagerAdapter(FragmentManager fm, String[] titles, int[] types) {
        super(fm);
        this.mTitles = titles;
        this.mTypes = types;
    }

    @Override
    public Fragment getItem(int position) {
        return MessageCenterContentDelegate.create(mTypes[position]);
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
