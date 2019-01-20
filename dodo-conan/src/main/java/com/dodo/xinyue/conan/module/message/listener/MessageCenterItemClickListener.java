package com.dodo.xinyue.conan.module.message.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.module.message.MessageDetailDelegate;
import com.dodo.xinyue.conan.module.message.data.MessageCenterItemType;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulFields;
import com.dodo.xinyue.core.ui.recycler.MulItemClickListener;

/**
 * MessageCenterItemClickListener
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageCenterItemClickListener extends MulItemClickListener {

    private MessageCenterItemClickListener(DoDoDelegate delegate) {
        super(delegate);
    }

    public static MessageCenterItemClickListener create(DoDoDelegate delegate) {
        return new MessageCenterItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int itemViewType) {
        final JiGuangMessage bean = entity.getField(MulFields.BEAN);

        switch (adapter.getItemViewType(position)) {
            case MessageCenterItemType.ITEM_MESSAGE_CENTER:
                int type = bean.getType();
                DELEGATE.start(MessageDetailDelegate.create(type));
                break;
            default:
                break;
        }
    }

}
