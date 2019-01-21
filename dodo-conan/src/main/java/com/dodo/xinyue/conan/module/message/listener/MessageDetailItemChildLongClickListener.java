package com.dodo.xinyue.conan.module.message.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.view.dialog.list.ConanSimpleListDialog;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.dialog.callback.OnItemSelectedListener;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulItemChildLongClickListener;

/**
 * MessageDetailItemChildLongClickListener
 *
 * @author DoDo
 * @date 2019/1/21
 */
public class MessageDetailItemChildLongClickListener extends MulItemChildLongClickListener {

    protected MessageDetailItemChildLongClickListener(DoDoDelegate delegate) {
        super(delegate);
    }

    public static MessageDetailItemChildLongClickListener create(DoDoDelegate delegate) {
        return new MessageDetailItemChildLongClickListener(delegate);
    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int viewId) {
        final JiGuangMessage bean = entity.getBean();
        if (viewId == R.id.rlContainer) {
            ConanSimpleListDialog.builder()
                    .addItem("删除")
                    .onSelected(new OnItemSelectedListener() {
                        @Override
                        public void onSelected(int selectedIndex) {
                            switch (selectedIndex) {
                                case 0:
                                    deleteMessage(bean);
                                    adapter.remove(position);
                                    break;
                                default:
                                    break;
                            }
                        }
                    })
                    .build()
                    .show();
        }
        return true;
    }

    private void deleteMessage(JiGuangMessage bean) {
        ConanDataBaseManager.getInstance().getMessageDao().delete(bean);
    }

}
