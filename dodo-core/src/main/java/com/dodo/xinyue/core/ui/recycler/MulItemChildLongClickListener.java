package com.dodo.xinyue.core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * MulItemChildLongClickListener
 *
 * @author DoDo
 * @date 2019/1/21
 */
public abstract class MulItemChildLongClickListener implements BaseQuickAdapter.OnItemChildLongClickListener  {

    //用于页面跳转
    protected final DoDoDelegate DELEGATE;

    protected MulItemChildLongClickListener(DoDoDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        final MulEntity entity = (MulEntity) adapter.getData().get(position);
        final int viewId = view.getId();
        return onItemChildLongClick(adapter, view, position, entity, viewId);
    }

    /**
     * itemChild 长按点击事件
     *
     * @param adapter
     * @param view
     * @param position
     * @param entity   数据实体
     * @param viewId   用if判断
     */
    public abstract boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int viewId);

}
