package com.dodo.xinyue.conan.module.message.listener;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.listener.IHandleMessage;
import com.dodo.xinyue.conan.database.util.ConanMessageDBUtil;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.module.message.data.MessageCenterItemType;
import com.dodo.xinyue.conan.view.dialog.list.ConanSimpleListDialog;
import com.dodo.xinyue.conan.view.dialog.loading.ConanLoadingDialog;
import com.dodo.xinyue.conan.view.dialog.normal.ConanNormalDialog;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogBean;
import com.dodo.xinyue.core.ui.dialog.manager.DialogManager;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulFields;
import com.dodo.xinyue.core.ui.recycler.MulItemLongClickListener;
import com.dodo.xinyue.core.util.log.DoDoLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageCenterItemLongClickListener
 *
 * @author DoDo
 * @date 2019/1/21
 */
public class MessageCenterItemLongClickListener extends MulItemLongClickListener {

    protected MessageCenterItemLongClickListener(DoDoDelegate delegate) {
        super(delegate);
    }

    public static MessageCenterItemLongClickListener create(DoDoDelegate delegate) {
        return new MessageCenterItemLongClickListener(delegate);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int itemViewType) {
        final JiGuangMessage bean = entity.getField(MulFields.BEAN);

        switch (adapter.getItemViewType(position)) {
            case MessageCenterItemType.ITEM_MESSAGE_CENTER:
                final int type = bean.getType();
                final boolean isQuiet = ApiHelper.getMessageIsQuiet(type);
                final String isQuietStr = isQuiet ? "解除免打扰" : "免打扰";
                final ArrayList<ListDialogBean> data = new ArrayList<>();
                data.add(new ListDialogBean(isQuietStr, false));
                if (bean.getMessageID() != 0) {
                    data.add(new ListDialogBean("清空消息", false));
                }
                ConanSimpleListDialog.builder()
                        .addItems(data)
                        .onSelected(selectedIndex -> {
                            switch (selectedIndex) {
                                case 0:
                                    ApiHelper.setMessageQuiet(type, !isQuiet);
                                    adapter.notifyItemChanged(position);
                                    break;
                                case 1:
                                    String title = ApiHelper.getMessageNickName(type);
                                    ConanNormalDialog.builder()
                                            .title("清空消息")
                                            .content("确定清空<font color=#2ca9e1>" + title + "</font>？")
                                            .isHtml(true)
                                            .confirm(() -> clearMessage(type))
                                            .build()
                                            .show();
                                    break;
                                default:
                                    break;
                            }
                        })
                        .build()
                        .show();
                break;
            default:
                break;
        }
        return true;
    }

    private void clearMessage(int messageType) {
        ConanLoadingDialog.builder()
                .anim(-1)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .build()
                .show();
        ConanMessageDBUtil.deleteAllMessageAsync(messageType, new IHandleMessage() {
            @Override
            public void onSuccess(List<JiGuangMessage> result, long duration) {
                DoDoLogger.d("清空消息成功");
                ToastUtils.showShort("操作成功");
                DialogManager.getInstance().cancelLastDialog();
            }

            @Override
            public void onFailure() {
                DoDoLogger.d("清空消息失败");
                ToastUtils.showShort("操作失败");
                DialogManager.getInstance().cancelLastDialog();
            }
        });
    }

}
