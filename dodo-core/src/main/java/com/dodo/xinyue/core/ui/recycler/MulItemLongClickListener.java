package com.dodo.xinyue.core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * MulItemLongClickListener
 *
 * @author DoDo
 * @date 2019/1/21
 */
public abstract class MulItemLongClickListener implements BaseQuickAdapter.OnItemLongClickListener {

    //用于页面跳转
    protected final DoDoDelegate DELEGATE;

    protected MulItemLongClickListener(DoDoDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        final MulEntity entity = (MulEntity) adapter.getData().get(position);
        final int itemViewType = adapter.getItemViewType(position);
        return onItemLongClick(adapter, view, position, entity, itemViewType);
    }

    /**
     * item 长按点击事件
     *
     * @param adapter
     * @param view
     * @param position
     * @param entity       数据实体
     * @param itemViewType 用switch判断
     */
    public abstract boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int itemViewType);

}
