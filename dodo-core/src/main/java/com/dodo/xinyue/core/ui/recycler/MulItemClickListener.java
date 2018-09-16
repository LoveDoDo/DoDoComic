package com.dodo.xinyue.core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * MulItemClickListener
 *
 * @author DoDo
 * @date 2018/2/6
 */
public abstract class MulItemClickListener implements BaseQuickAdapter.OnItemClickListener {

    //用于页面跳转
    protected final DoDoDelegate DELEGATE;

    protected MulItemClickListener(DoDoDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MulEntity entity = (MulEntity) adapter.getData().get(position);
        final int itemViewType = adapter.getItemViewType(position);
        onItemClick(adapter, view, position, entity, itemViewType);
    }

    /**
     * item点击事件
     *
     * @param adapter
     * @param view
     * @param position
     * @param entity       数据实体
     * @param itemViewType 用switch判断
     */
    public abstract void onItemClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int itemViewType);

}
