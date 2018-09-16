package com.dodo.xinyue.core.delegates.bottom.bean;

import android.view.ViewGroup;

/**
 * IBottomTabBean
 *
 * @author DoDo
 * @date 2017/9/15
 */
public interface IBottomTabBean {

    int getLayoutId();

    void initView(ViewGroup container);

//    void setNormalState(ViewGroup container);
//
//    void setSelectedState(ViewGroup container);

}
