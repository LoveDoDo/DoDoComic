package com.dodo.xinyue.conan.main;

import android.graphics.Color;

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
import com.dodo.xinyue.core.delegates.bottom.builder.BottomItemBuilder;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomTabBeanBuilder;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * ConanDelegate
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class ConanBottomDelegate extends BaseBottomDelegate {

    @Override
    public int setBackgroundRes() {
        return R.drawable.yueyue;
    }

    @Override
    public LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> setTabItems(BottomItemBuilder builder) {
        final LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> items = new LinkedHashMap<>();
        items.put(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-home}", "首页"))
                        .setTextSelector(R.color.selector_bottom_bar_text_color)
                        .setContainerSelector(R.drawable.selector_bottom_bar_bg)
                        .setIconSelector(R.color.selector_bottom_bar_icon_color)
                        .setIconSize(30)
                        .setTextSize(13)
                        .build()
                , new IndexDelegate()
        );
        items.put(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-movie}", "电影"))
                        .setTextSelector(R.color.selector_bottom_bar_text_color)
                        .setContainerSelector(R.drawable.selector_bottom_bar_bg)
                        .setIconSelector(R.color.selector_bottom_bar_icon_color)
                        .setIconSize(30)
                        .setTextSize(13)
                        .build()
                , new MovieDelegate()
        );
        items.put(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-music}", "音乐"))
                        .setTextSelector(R.color.selector_bottom_bar_text_color)
                        .setContainerSelector(R.drawable.selector_bottom_bar_bg)
                        .setIconSelector(R.color.selector_bottom_bar_icon_color)
                        .setIconSize(30)
                        .setTextSize(13)
                        .build()
                , new MusicDelegate()
        );
        items.put(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-mine}", "我的"))
                        .setTextSelector(R.color.selector_bottom_bar_text_color)
                        .setContainerSelector(R.drawable.selector_bottom_bar_bg)
                        .setIconSelector(R.color.selector_bottom_bar_icon_color)
                        .setIconSize(30)
                        .setTextSize(13)
                        .build()
                , new MineDelegate()
        );
        return builder.addItems(items).build();
    }

    @Override
    public int setFirstPageIndex() {
        return 0;
    }

    @Override
    public WeakHashMap<BottomBarParamsType, Object> setBottomBar(BottomBarParamsBuilder builder) {
        return builder
                .setLineHasVisible(false)
                .setBottomBarBackgroundColor(Color.TRANSPARENT)
//                .setBottomBarHeight(50)
//                .setLineBackgroundColor(Color.parseColor("#f2f2f2"))
//                .setLineBackgroundRes(R.color.main_ripple_color)
                .build();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.conan_bottom_enter);
        return fragmentAnimator;
    }
}
