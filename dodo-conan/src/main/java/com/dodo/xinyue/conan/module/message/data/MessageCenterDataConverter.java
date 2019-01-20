package com.dodo.xinyue.conan.module.message.data;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;
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
        int cover;
        switch (mType) {
            case JiGuangMessage.TYPE_NOTICE:
                cover = R.drawable.icon_message_notice;
                break;
            case JiGuangMessage.TYPE_NONE:
                cover = R.drawable.icon_message_other_2;
                break;
            default:
                cover = R.drawable.conan_logo_2;
                break;
        }
        JiGuangMessage bean = ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(mType))
                .orderDesc(JiGuangMessageDao.Properties.Id)//倒序
                .limit(1)//限制返回结果数
                .unique();//返回唯一结果
        final MulEntity entity = MulEntity.builder()
                .setItemType(MessageCenterItemType.ITEM_MESSAGE_CENTER)
                .setBean(bean)
                .setData(cover)
                .build();
        ENTITIES.add(entity);
        return ENTITIES;
    }

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
