package com.dodo.xinyue.core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 备用，方便扩展
 *
 * @author DoDo
 * @date 2017/9/10
 */
public class MulHolder extends BaseViewHolder {

    public MulHolder(View view) {
        super(view);
    }

    //TODO 简单工厂模式

    public static MulHolder create(View view) {
        return new MulHolder(view);
    }
}
