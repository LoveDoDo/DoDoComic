package com.dodo.xinyue.conan.module.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;
import com.dodo.xinyue.conan.module.message.adapter.MessageCenterPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;

/**
 * MessageCenterDelegate
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageCenterDelegate extends BaseModuleDelegate {

    public static final int TYPE_NOTICE = 0;
    public static final int TYPE_OTHER = 1;
    private static final String[] TITLES = new String[]{"公告", "其他"};
    private static final int[] TYPES = new int[]{TYPE_NOTICE, TYPE_OTHER};

    @BindView(R2.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R2.id.vpContent)
    ViewPager mViewPagerContent;

    public static MessageCenterDelegate create() {
        return new MessageCenterDelegate();
    }

    @Override
    public Object setChildLayout() {
        return R.layout.delegate_message_center;
    }

    @Override
    public String setTitle() {
        return "消息中心";
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mViewPagerContent.setOffscreenPageLimit(TITLES.length);//缓存全部
        mViewPagerContent.setAdapter(new MessageCenterPagerAdapter(getChildFragmentManager(), TITLES, TYPES));
        mTabLayout.setViewPager(mViewPagerContent);
        mTabLayout.setCurrentTab(0, false);
    }
}
