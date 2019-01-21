package com.dodo.xinyue.conan.module.message.data;

import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
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

    private List<JiGuangMessage> mData;

    @Override
    public ArrayList<MulEntity> convert() {
//        ENTITIES.clear();//TODO 清空数据源会影响adapter的addData(Collection<> data)方法
        /**
         * 问题记录
         *
         * 使用adapter的addData(Collection<> data)方法批量添加数据的时候，不能使用ENTITIES.clear()清空原数据源，会造成两份重复的新数据
         * addData内部使用的list.addAll()进行新老数据的合并，所以新数据源必须new一个新的对象
         */
        int size = mData.size();
        for (int i = 0; i < size; i++) {
            final MulEntity entity = MulEntity.builder()
                    .setItemType(MessageCenterItemType.ITEM_MESSAGE_DETAIL)
                    .setBean(mData.get(i))
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }

    public final MessageDetailDataConverter setData(List<JiGuangMessage> data) {
        this.mData = data;
        return this;
    }

    /**
     * 追加新数据
     * 适用于分页加载
     */
    public final List<MulEntity> addData(List<JiGuangMessage> newData) {
        final List<MulEntity> entities = new ArrayList<>();
        final int size = newData.size();
        for (int i = 0; i < size; i++) {
            final MulEntity entity = MulEntity.builder()
                    .setItemType(MessageCenterItemType.ITEM_MESSAGE_DETAIL)
                    .setBean(newData.get(i))
                    .build();
            entities.add(entity);
        }
        return entities;
    }

}
