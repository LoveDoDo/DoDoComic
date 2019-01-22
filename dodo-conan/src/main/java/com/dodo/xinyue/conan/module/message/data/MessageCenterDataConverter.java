package com.dodo.xinyue.conan.module.message.data;

import android.os.Handler;
import android.os.Message;

import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.module.message.MessageCenterDelegate;
import com.dodo.xinyue.conan.module.message.callback.IConvertMessage;
import com.dodo.xinyue.conan.module.message.helper.MessageHandlerThread;
import com.dodo.xinyue.core.ui.recycler.MulEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageCenterDataConverter
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageCenterDataConverter implements Handler.Callback {

    private final Handler mUIHandler = new Handler(this);
    private MessageHandlerThread mMessageHandlerThread = null;
    private IConvertMessage mIConvertMessage = null;

    /**
     * 异步查询并转换数据
     */
    public final void convertAsync(int type, IConvertMessage listener) {
        this.mIConvertMessage = listener;
        final ArrayList<Integer> messageTypes = new ArrayList<>();
        switch (type) {
            case MessageCenterDelegate.TYPE_NOTICE:
                messageTypes.add(JiGuangMessage.TYPE_NOTICE);
                break;
            case MessageCenterDelegate.TYPE_OTHER:
                messageTypes.add(JiGuangMessage.TYPE_CONAN);
                messageTypes.add(JiGuangMessage.TYPE_INFERENCE);
                messageTypes.add(JiGuangMessage.TYPE_JOKE);
                messageTypes.add(JiGuangMessage.TYPE_ACTIVE);
                messageTypes.add(JiGuangMessage.TYPE_CLASSIC);
                messageTypes.add(JiGuangMessage.TYPE_NONE);
                break;
            default:
                break;
        }

        mMessageHandlerThread = MessageHandlerThread.builder()
                .messageTypes(messageTypes)
                .handler(mUIHandler)
                .build();
        mMessageHandlerThread.start();//因为HandlerThread本身就是个线程,所以使用start启动
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean handleMessage(Message msg) {
        if (msg == null || msg.getData() == null) {
            return false;
        }

        switch (msg.what) {
            case MessageHandlerThread.STATUS_SUCCESS:
                final List<MulEntity> data = (List<MulEntity>) msg.getData().getSerializable(MessageHandlerThread.KEY_DATA);
                final long duration = msg.getData().getLong(MessageHandlerThread.KEY_DURATION);
                if (mIConvertMessage != null) {
                    mIConvertMessage.onCompleted(data, duration);
                }
                break;
            case MessageHandlerThread.STATUS_FAILURE:
                if (mIConvertMessage != null) {
                    mIConvertMessage.onCompleted(null, 0);
                }
                break;
            default:
                if (mIConvertMessage != null) {
                    mIConvertMessage.onCompleted(null, 0);
                }
                break;
        }
        return true;
    }

    /**
     * 安全关闭异步任务
     */
    public final void quitSafely() {
        mUIHandler.removeCallbacksAndMessages(null);
        if (mMessageHandlerThread != null) {
            mMessageHandlerThread.quitSafely();
        }
//        mUIHandler = null;
    }
}
