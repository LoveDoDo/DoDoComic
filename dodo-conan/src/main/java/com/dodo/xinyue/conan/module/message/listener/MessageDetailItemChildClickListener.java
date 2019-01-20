package com.dodo.xinyue.conan.module.message.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.view.dialog.message.ConanMessageDialog;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulItemChildClickListener;

/**
 * MessageDetailItemClickListener
 *
 * @author DoDo
 * @date 2019/1/19
 */
public class MessageDetailItemChildClickListener extends MulItemChildClickListener {

    private MessageDetailItemChildClickListener(DoDoDelegate delegate) {
        super(delegate);
    }

    public static MessageDetailItemChildClickListener create(DoDoDelegate delegate) {
        return new MessageDetailItemChildClickListener(delegate);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int viewId) {
        if (viewId == R.id.llContainer) {
            openMessage(entity);
        } else if (viewId == R.id.tvOpen) {
            openMessage(entity);
        }
    }

    private void openMessage(MulEntity entity) {
        final JiGuangMessage bean = entity.getBean();
        ConanMessageDialog.builder()
                .message(bean)
                .build()
                .pendingShow();
    }

}
