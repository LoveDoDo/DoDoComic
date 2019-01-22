package com.dodo.xinyue.dodocomic.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.core.app.DoDo;

import cn.jpush.android.api.JPushInterface;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * 极光推送 自定义的广播接收器
 *
 * @author DoDo
 * @date 2018/10/30
 */
public class ComicPushReceiver extends BroadcastReceiver {

    private static final String TAG = "ComicPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        final String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        switch (action) {
            case JPushInterface.ACTION_NOTIFICATION_RECEIVED:
                //通知类型的推送
                removeNotification(context, bundle);
                handleMessage(bundle, action);
                break;
            case JPushInterface.ACTION_MESSAGE_RECEIVED:
                //自定义消息类型的推送
                handleMessage(bundle, action);
                break;
            default:
                break;

        }
    }

    /**
     * 处理消息
     * <p>
     * 通知类型
     * {
     * "cn.jpush.android.ALERT": "哈哈",
     * "cn.jpush.android.ALERT_TYPE": "7",
     * "cn.jpush.android.EXTRA": "{\"a\":\"1\",\"b\":\"2\"}",
     * "cn.jpush.android.MSG_ID": "3452864355",
     * "cn.jpush.android.NOTIFICATION_CONTENT_TITLE": "柯南迷",
     * "cn.jpush.android.NOTIFICATION_ID": 189268494
     * }
     * <p>
     * 自定义类型
     * {
     * "cn.jpush.android.APPKEY": "e62be49c55d9048cdac93a9f",
     * "cn.jpush.android.CONTENT_TYPE": "",
     * "cn.jpush.android.EXTRA": "{\"a\":\"1\",\"b\":\"2\"}",
     * "cn.jpush.android.MESSAGE": "haha",
     * "cn.jpush.android.MSG_ID": "3453168298",
     * "cn.jpush.android.TITLE": ""
     * }
     */
    private void handleMessage(Bundle bundle, String action) {
        final String dataJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final JSONObject data = JSON.parseObject(dataJson);
        final int type = getMessageType(data);
        //TODO 这里需要判断一下是否需要接收消息并存入数据库

        final long messageID = Long.valueOf(bundle.getString(JPushInterface.EXTRA_MSG_ID));
        String content = TextUtils.equals(action, JPushInterface.ACTION_NOTIFICATION_RECEIVED) ?
                bundle.getString(JPushInterface.EXTRA_ALERT) : bundle.getString(JPushInterface.EXTRA_MESSAGE);
        if (!TextUtils.isEmpty(content)) {
            content = content.trim();
        }
        final String title = getMessageTitle(data);
        final String extraData = getExtraData(data);
        final boolean read = false;
        final long timestamp = System.currentTimeMillis();

        final JiGuangMessage message = new JiGuangMessage();
        message.setMessageID(messageID);
        message.setType(type);
        message.setTitle(title);
        message.setContent(content);
        message.setExtraData(extraData);
        message.setRead(read);
        message.setTimestamp(timestamp);
        //插入数据库
        ConanDataBaseManager.getInstance().getMessageDao().insertOrReplace(message);

        EventBusActivityScope.getDefault(DoDo.getActivity()).postSticky(message);

    }

    private void test() {
//        ConanLoadingDialog.builder()
//                .build()
//                .show();
//
//        final List<JiGuangMessage> messages = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            final JiGuangMessage message = new JiGuangMessage();
//            message.setMessageID(CommonUtil.random());
//            message.setType(type);
//            message.setTitle((i + 1) + "");
//            message.setContent(content);
//            message.setExtraData(extraData);
//            message.setRead(read);
//            message.setTimestamp(timestamp);
//
//            messages.add(message);
//        }
//        ConanMessageDBUtil.insertOrReplaceInTxAsync(messages, new IHandleMessage() {
//            @Override
//            public void onSuccess(List<JiGuangMessage> result, long duration) {
//                ConanNormalDialog.builder()
//                        .title("加入完成")
//                        .content("用时：" + duration)
//                        .onlyOneButton(true)
//                        .build()
//                        .show();
//            }
//
//            @Override
//            public void onFailure() {
//                ConanNormalDialog.builder()
//                        .title("加入失败")
//                        .content("")
//                        .onlyOneButton(true)
//                        .build()
//                        .show();
//            }
//        });
    }

    private int getMessageType(JSONObject data) {
        int type = JiGuangMessage.TYPE_NONE;//默认
        if (data != null) {
            type = data.getIntValue("type");
        }
        return type;
    }

    private String getMessageTitle(JSONObject data) {
        String title = null;
        if (data != null) {
            title = data.getString("title");
        }
        if (TextUtils.isEmpty(title)) {
            final int type = getMessageType(data);
            title = ApiHelper.getMessageDefaultTitle(type);
        }
        return title;
    }

    private String getExtraData(JSONObject data) {
        return data != null ? JSON.toJSONString(data) : JSON.toJSONString(new JSONObject());
    }

    @Deprecated
    private String getCustomAttr(Bundle bundle) {
        final String dataJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String customAttr = null;
        final JSONObject data = JSON.parseObject(dataJson);
        if (data != null) {
            int aaa = data.getIntValue("aaa");//aaa替换为具体字段名
            String bbb = data.getString("bbb");//bbb替换为具体字段名
            final JSONObject json = new JSONObject();
            json.put("aaa", aaa);
            json.put("bbb", bbb);
            customAttr = json.toJSONString();
        }
        return customAttr;
    }

    /**
     * 移除通知
     */
    private void removeNotification(Context context, Bundle bundle) {
        final int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        JPushInterface.clearNotificationById(context, notificationId);//还是会振动一下，控制台要取消振动
//        JPushInterface.clearAllNotifications(context);
    }

}
