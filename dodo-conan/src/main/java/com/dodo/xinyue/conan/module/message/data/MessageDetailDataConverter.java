package com.dodo.xinyue.conan.module.message.data;

import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageDetailDataConverter
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageDetailDataConverter extends DataConverter {

    private int mType;

    @Override
    public ArrayList<MulEntity> convert() {
        List<JiGuangMessage> list = ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(mType))
                .orderDesc(JiGuangMessageDao.Properties.Id)//倒序
                .list();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            final MulEntity entity = MulEntity.builder()
                    .setItemType(MessageCenterItemType.ITEM_MESSAGE_DETAIL)
                    .setBean(list.get(i))
                    .build();
            ENTITIES.add(entity);
        }

        return ENTITIES;
    }

    public long getMessageCount(int type) {
        return ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(type))
                .buildCount()
                .count();
    }

    public final MessageDetailDataConverter setType(int type) {
        this.mType = type;
        return this;
    }

}
