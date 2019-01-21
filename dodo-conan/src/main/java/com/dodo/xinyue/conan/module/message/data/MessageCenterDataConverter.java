package com.dodo.xinyue.conan.module.message.data;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;
import com.dodo.xinyue.conan.module.message.MessageCenterDelegate;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;

import java.util.ArrayList;

/**
 * MessageCenterDataConverter
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageCenterDataConverter extends DataConverter {

    private int mType;

    @Override
    public ArrayList<MulEntity> convert() {
        switch (mType) {
            case MessageCenterDelegate.TYPE_NOTICE:
                addItem(JiGuangMessage.TYPE_NOTICE);
                break;
            case MessageCenterDelegate.TYPE_OTHER:
                addItem(JiGuangMessage.TYPE_CONAN);
                addItem(JiGuangMessage.TYPE_INFERENCE);
                addItem(JiGuangMessage.TYPE_JOKE);
                addItem(JiGuangMessage.TYPE_ACTIVE);
                addItem(JiGuangMessage.TYPE_CLASSIC);
                addItem(JiGuangMessage.TYPE_NONE);
                break;
            default:
                break;
        }
        return ENTITIES;
    }

    private void addItem(int messageType) {
        int cover = getCover(messageType);
        JiGuangMessage bean = ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .orderDesc(JiGuangMessageDao.Properties.Id)//倒序
                .limit(1)//限制返回结果数
                .unique();//返回唯一结果
        if (bean == null) {
            bean = new JiGuangMessage();
            bean.setType(messageType);
        }
        final MulEntity entity = MulEntity.builder()
                .setItemType(MessageCenterItemType.ITEM_MESSAGE_CENTER)
                .setBean(bean)
                .setData(cover)
                .build();
        ENTITIES.add(entity);
    }

    private int getCover(int messageType) {
        int cover;
        switch (messageType) {
            case JiGuangMessage.TYPE_NOTICE:
                cover = R.drawable.icon_message_notice;
                break;
            case JiGuangMessage.TYPE_NONE:
            case JiGuangMessage.TYPE_INFERENCE:
            case JiGuangMessage.TYPE_JOKE:
            case JiGuangMessage.TYPE_ACTIVE:
            case JiGuangMessage.TYPE_CLASSIC:
            case JiGuangMessage.TYPE_CONAN:
                cover = R.drawable.icon_message_other;
                break;
            default:
                cover = R.drawable.icon_message_other_2;
                break;
        }
        return cover;
    }

    @Deprecated
    public long getMessageCount(int type) {
        return ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(type))
                .buildCount()
                .count();
    }

    public final MessageCenterDataConverter setType(int type) {
        this.mType = type;
        return this;
    }

}
