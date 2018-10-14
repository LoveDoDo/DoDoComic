package com.dodo.xinyue.core.delegates.bottom.bean;

import android.view.View;

/**
 * IBottomTabBean
 *
 * @author DoDo
 * @date 2017/9/15
 */
public interface IBottomTabBean {

    Object setTabLayout();

    void initView(View tabView);

    void onNormalState(View tabView, boolean onCreate);

    void onSelectedState(View tabView, boolean onCreate);

}
