package com.dodo.xinyue.conan.module.message.listener;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;
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

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

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
                if (!TextUtils.isEmpty(bean.getMessageID())) {
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
                                            .content("确定清空<font color=#2ca9e1>" + title + "</font>里面的所有消息？")
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
        final long currentTime = System.currentTimeMillis();
        AsyncSession asyncSession = ConanDataBaseManager.getInstance().getMessageDao()
                .getSession().startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                Log.d("gsfgdgdbf", "删除完成，用时：" + operation.getDuration());
                DialogManager.getInstance().cancelLastDialog();
            }
        });
        List<JiGuangMessage> list = ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .list();
        asyncSession.deleteInTx(JiGuangMessage.class, list);
    }

}
