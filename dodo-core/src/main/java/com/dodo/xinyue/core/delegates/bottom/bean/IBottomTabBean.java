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

    Object setBigTabLayout();

    void initView(View tabView);

//    void setNormalState(ViewGroup container);
//
//    void setSelectedState(ViewGroup container);

}
