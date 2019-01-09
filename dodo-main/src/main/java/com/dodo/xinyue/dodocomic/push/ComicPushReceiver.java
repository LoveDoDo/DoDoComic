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
        final String messageID = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final String title = getMessageTitle(bundle);
        final String content = TextUtils.equals(action, JPushInterface.ACTION_NOTIFICATION_RECEIVED) ?
                bundle.getString(JPushInterface.EXTRA_ALERT) : bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final int type = getMessageType(bundle);
        final String updateInfo = type == JiGuangMessage.TYPE_UPDATE ? getMessageUpdateInfo(bundle) : null;
        final boolean read = false;
        final long timestamp = System.currentTimeMillis();

        final JiGuangMessage message = new JiGuangMessage();
        message.setMessageID(messageID);
        message.setTitle(title);
        message.setContent(content);
        message.setType(type);
        message.setUpdateInfo(updateInfo);
        message.setRead(read);
        message.setTimestamp(timestamp);
        //插入数据库
        ConanDataBaseManager.getInstance().getMessageDao().insertOrReplace(message);

        EventBusActivityScope.getDefault(DoDo.getActivity()).postSticky(message);
    }

    private String getMessageTitle(Bundle bundle) {
        final String dataJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String title = null;
        final JSONObject data = JSON.parseObject(dataJson);
        if (data != null) {
            title = data.getString("title");
        }
        if (TextUtils.isEmpty(title)) {
            final int type = getMessageType(bundle);
            switch (type) {
                case JiGuangMessage.TYPE_NOTICE:
                    title = "公告";
                    break;
                case JiGuangMessage.TYPE_UPDATE:
                    title = "柯南迷更新啦~";
                    break;
                default:
                    title = "新消息";
                    break;
            }
        }
        return title;
    }

    private int getMessageType(Bundle bundle) {
        final String dataJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
        int type = JiGuangMessage.TYPE_NONE;
        final JSONObject data = JSON.parseObject(dataJson);
        if (data != null) {
            type = data.getIntValue("type");
        }
        return type;
    }

    private String getMessageUpdateInfo(Bundle bundle) {
        final String dataJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String updateInfo = null;
        final JSONObject data = JSON.parseObject(dataJson);
        if (data != null) {
            int versionCode = data.getIntValue("versionCode");
            String versionName = data.getString("versionName");
            String packageSize = data.getString("packageSize");
            String downloadPath = data.getString("downloadPath");
            final JSONObject json = new JSONObject();
            json.put("versionCode", versionCode);
            json.put("versionName", versionName);
            json.put("packageSize", packageSize);
            json.put("downloadPath", downloadPath);
            updateInfo = json.toJSONString();
        }
        return updateInfo;
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
