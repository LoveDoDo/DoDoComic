package com.dodo.xinyue.conan.main;

import android.os.Bundle;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.bottom.ConanTabBean;
import com.dodo.xinyue.conan.main.index.IndexDelegate;
import com.dodo.xinyue.conan.main.mine.MineDelegate;
import com.dodo.xinyue.conan.main.movie.MovieDelegate;
import com.dodo.xinyue.conan.main.music.MusicDelegate;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomDelegate;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsBuilder;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsType;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomTabBeanBuilder;
import com.dodo.xinyue.core.delegates.bottom.options.BottomTabBeanOptions;

import java.util.ArrayList;
import java.util.WeakHashMap;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * ConanDelegate
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class ConanBottomDelegate extends BaseBottomDelegate {

    //寻宝模式 双击间隔时间 默认2秒
    private static final long WAIT_TIME = 2000L;
    //寻宝模式 记录上次点击的时间
    private long mLastTouchTime = 0;
    //寻宝模式 点击次数
    private int mTouchCount = 1;
    //寻宝模式 目标次数
    private int mTargetCount = 8;

    //TODO 相同大小的iconSize在布局中不一定一样大(找了好久的bug...)

    private static final BottomTabBeanOptions TAB_OPTIONS =
            new BottomTabBeanOptions()
                    .setTextSelector(R.color.selector_bottom_bar_text_color)
                    .setContainerSelector(R.drawable.selector_bottom_bar_bg)
                    .setIconSelector(R.color.selector_bottom_bar_icon_color)
                    .setIconSize(30)
                    .setTextSize(13)
                    .setTabGravity(Gravity.BOTTOM);

    @Override
    public ArrayList<BaseBottomTabBean> setTabBeans() {
        final ArrayList<BaseBottomTabBean> tabBeans = new ArrayList<>();
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-home}", "首页", R.raw.home))
                        .setOptions(TAB_OPTIONS)
                        .build()
        );
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-movie}", "电影", R.raw.movie))
                        .setOptions(TAB_OPTIONS)
                        .build()
        );
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-music}", "音乐", R.raw.music))
                        .setOptions(TAB_OPTIONS)
                        .build()
        );
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-mine}", "我的", R.raw.mine))
                        .setOptions(TAB_OPTIONS)
                        .build()
        );
        return tabBeans;
    }

    @Override
    public int setBackgroundRes() {
        return R.drawable.yueyue;
    }

    @Override
    public ArrayList<BaseBottomItemDelegate> setItemDelegates() {
        final ArrayList<BaseBottomItemDelegate> delegates = new ArrayList<>();
        delegates.add(new IndexDelegate());
        delegates.add(new MovieDelegate());
        delegates.add(new MusicDelegate());
        delegates.add(new MineDelegate());
        return delegates;
    }

    @Override
    public WeakHashMap<BottomBarParamsType, Object> setBottomBar(BottomBarParamsBuilder builder) {
        return builder
//                .setLineHasVisible(true)
//                .setLineBackgroundColor(Color.GREEN)
                .setTabContainerHeight(64)
//                .setTabContainerHeight(180)
//                .setBigTabContainerBackgroundRes(R.color.bilibili_color)
//                .setLineBackgroundColor(Color.parseColor("#f2f2f2"))
//                .setLineBackgroundRes(R.color.main_ripple_color)
                .build();
    }

    @Override
    public boolean onTabSelected(int position, boolean isRepeat) {
        if (isRepeat) {
            if (position == 3) {
                if (System.currentTimeMillis() - mLastTouchTime < WAIT_TIME) {
                    mLastTouchTime = System.currentTimeMillis();
                    mTouchCount++;
                    if (mTouchCount > 3) {
                        ToastUtils.showShort("再按 " + (mTargetCount - mTouchCount) + " 次开启寻宝模式");
                    }
                    if (mTouchCount == mTargetCount) {
                        ToastUtils.showShort("恭喜你开启寻宝模式");
                        mTouchCount = 0;
                    }
                } else {
                    mLastTouchTime = System.currentTimeMillis();
                    mTouchCount = 1;
                }
            }

        }
        return super.onTabSelected(position, isRepeat);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
//        fragmentAnimator.setEnter(R.anim.conan_bottom_enter);
        return fragmentAnimator;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        //测试用
        getProxyActivity().removeWindowBackground();
    }
}
