package com.dodo.xinyue.core.ui.dialog.list.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.OnItemSelectedListener;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogBean;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogItemType;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulItemClickListener;

/**
 * ListDialogItemClickListener
 *
 * @author DoDo
 * @date 2018/10/6
 */
public class ListDialogItemClickListener extends MulItemClickListener {

    private final BaseDialog mDialog;
    private final OnItemSelectedListener mOnItemSelectedListener;

    protected ListDialogItemClickListener(BaseDialog dialog,OnItemSelectedListener listener) {
        super(null);
        this.mOnItemSelectedListener = listener;
        this.mDialog = dialog;
    }

    public static ListDialogItemClickListener create(BaseDialog dialog, OnItemSelectedListener listener) {
        return new ListDialogItemClickListener(dialog,listener);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int itemViewType) {
        switch (itemViewType) {
            case ListDialogItemType.ITEM:
                final ListDialogBean bean = entity.getBean();
                final boolean selected = bean.isSelected();
                //如果当前item之前就是被选中的状态,则不回调onSelected()
                if (selected) {
                    cancelDialog();
                    return;
                }

                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onSelected(position);
                }

                cancelDialog();

                break;
            default:
                break;
        }
    }

    private void cancelDialog() {
        if (mDialog != null) {
            mDialog.cancel();
        }
    }
}
