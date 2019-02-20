package com.dodo.xinyue.core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * MulItemChildClickListener
 *
 * @author DoDo
 * @date 2018/2/6
 */
public abstract class MulItemChildClickListener implements BaseQuickAdapter.OnItemChildClickListener {

    //用于页面跳转
    protected final DoDoDelegate DELEGATE;

    protected MulItemChildClickListener(DoDoDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        final MulEntity entity = (MulEntity) adapter.getData().get(position);
        final int viewId = view.getId();
        onItemChildClick(adapter, view, position, entity, viewId);
    }

    /**
     * itemChild点击事件
     *
     * @param adapter
     * @param view
     * @param position
     * @param entity   数据实体
     * @param viewId   用if判断
     */
    public abstract void onItemChildClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int viewId);
}
