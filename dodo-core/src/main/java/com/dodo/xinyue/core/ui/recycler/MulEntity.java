package com.dodo.xinyue.core.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * 每一个独立的item实体
 *
 * @author DoDo
 * @date 2017/9/10
 */
public class MulEntity implements MultiItemEntity, Serializable {

    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MUL_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
            new SoftReference<>(MUL_FIELDS, ITEM_QUEUE);

    MulEntity(LinkedHashMap<Object, Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }

    public static MulEntityBuilder builder() {
        return new MulEntityBuilder();
    }

    /**
     * dapter会从这里获取到item类型
     */
    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MulFields.ITEM_TYPE);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key) {
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?, ?> getFields() {
        return FIELDS_REFERENCE.get();
    }

    @SuppressWarnings("UnusedReturnValue")
    public final MulEntity setField(Object key, Object value) {
        FIELDS_REFERENCE.get().put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <T> T getBean() {
        return (T) FIELDS_REFERENCE.get().get(MulFields.BEAN);
    }

}
