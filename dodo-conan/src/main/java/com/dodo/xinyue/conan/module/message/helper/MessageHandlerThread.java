package com.dodo.xinyue.conan.module.message.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.util.ConanMessageDBUtil;
import com.dodo.xinyue.conan.module.message.data.MessageCenterItemType;
import com.dodo.xinyue.core.ui.recycler.MulEntity;

import java.util.ArrayList;

/**
 * MessageHandlerThread
 *
 * @author DoDo
 * @date 2019/1/22
 */
public class MessageHandlerThread extends HandlerThread implements Handler.Callback {

    public static final String KEY_DATA = "key_data";
    public static final String KEY_DURATION = "key_duration";
    public static final String KEY_MESSAGE_TYPE = "key_message_type";
    private static final String KEY_IS_DONE = "key_is_done";
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_FAILURE = 1;

    private Handler mWorkerHandler = null;

    private ArrayList<Integer> mMessageTypes = null;
    private Handler mUIHandler = null;

    private ArrayList<MulEntity> mData = new ArrayList<>();
    private long mDuration = 0L;//查询用时

    private MessageHandlerThread(String name) {
        super(name);
    }

    MessageHandlerThread(
            ArrayList<Integer> messageTypes,
            Handler uiHandler
    ) {
        this("MessageHandlerThread");
        this.mMessageTypes = messageTypes;
        this.mUIHandler = uiHandler;
    }

    public static MessageHandlerThreadBuilder builder() {
        return new MessageHandlerThreadBuilder();
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkerHandler = new Handler(getLooper(), this);
        enqueue();
    }

    /**
     * 入列
     * <p>
     * 依次添加到WorkerHandler消息队列中
     */
    private void enqueue() {
        final int size = mMessageTypes.size();
        for (int i = 0; i < size; i++) {
            final int messageType = mMessageTypes.get(i);
            final Message msg = mWorkerHandler.obtainMessage();
            final Bundle data = new Bundle();
            data.putInt(KEY_MESSAGE_TYPE, messageType);
            if (i == size - 1) {
                data.putBoolean(KEY_IS_DONE, true);
            } else {
                data.putBoolean(KEY_IS_DONE, false);
            }
            msg.setData(data);
            mWorkerHandler.sendMessage(msg);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg == null) {
            return false;
        }
        final Bundle args = msg.getData();
        if (args == null) {
            return false;
        }

        final int messageType = args.getInt(KEY_MESSAGE_TYPE);
        final boolean isDone = args.getBoolean(KEY_IS_DONE);

        final long startTime = System.currentTimeMillis();
        addItem(messageType);
        final long endTime = System.currentTimeMillis();
        mDuration = mDuration + (endTime - startTime);

        if (isDone) {
            sendSuccessMessage();
        }

        return true;
    }

    private void sendSuccessMessage() {
        final Message successMsg = mUIHandler.obtainMessage(STATUS_SUCCESS);
        final Bundle args = new Bundle();
        args.putSerializable(KEY_DATA, mData);
        args.putLong(KEY_DURATION, mDuration);
        successMsg.setData(args);
        mUIHandler.sendMessage(successMsg);
    }

    private void sendFailureMessage() {
        mUIHandler.sendEmptyMessage(STATUS_FAILURE);
    }

    private void addItem(int messageType) {
        JiGuangMessage bean = ConanMessageDBUtil.getMessageAtFirst(messageType);
        if (bean == null) {
            bean = new JiGuangMessage();
            bean.setType(messageType);
        }
        int cover = getCover(messageType);
        final MulEntity entity = MulEntity.builder()
                .setItemType(MessageCenterItemType.ITEM_MESSAGE_CENTER)
                .setBean(bean)
                .setData(cover)
                .build();
        mData.add(entity);
    }

    private int getCover(int messageType) {
        int cover;
        switch (messageType) {
            case JiGuangMessage.TYPE_NOTICE:
                cover = R.drawable.icon_message_notice;
                break;
            case JiGuangMessage.TYPE_CONAN:
            case JiGuangMessage.TYPE_INFERENCE:
            case JiGuangMessage.TYPE_JOKE:
            case JiGuangMessage.TYPE_ACTIVE:
            case JiGuangMessage.TYPE_CLASSIC:
            case JiGuangMessage.TYPE_MOVIE:
            case JiGuangMessage.TYPE_NONE:
                cover = R.drawable.icon_message_other;
                break;
            default:
                cover = R.drawable.icon_message_other_2;
                break;
        }
        return cover;
    }

    @Override
    public boolean quitSafely() {
        if (mWorkerHandler != null) {
            mWorkerHandler.removeCallbacksAndMessages(null);
            mWorkerHandler = null;
        }
        return super.quitSafely();
    }

}
