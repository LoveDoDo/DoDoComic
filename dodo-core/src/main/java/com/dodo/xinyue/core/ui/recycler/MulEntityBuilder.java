package com.dodo.xinyue.core.ui.recycler;

import java.util.LinkedHashMap;

/**
 * MulEntityBuilder
 *
 * @author DoDo
 * @date 2017/9/10
 */
public class MulEntityBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MulEntityBuilder() {
        //清除之前的数据
        FIELDS.clear();
    }

    public final MulEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MulEntityBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MulEntityBuilder setItemType(int itemType) {
        FIELDS.put(MulFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MulEntityBuilder setSpanSize(int spanSize) {
        FIELDS.put(MulFields.SPAN_SIZE, spanSize);
        return this;
    }

    public final MulEntityBuilder setFullSpan() {
        FIELDS.put(MulFields.SPAN_SIZE, -1);
        return this;
    }

    public final MulEntityBuilder setTag(Object object) {
        FIELDS.put(MulFields.TAG, object);
        return this;
    }

    public final MulEntityBuilder setBean(Object bean) {
        FIELDS.put(MulFields.BEAN, bean);
        return this;
    }

    public final MulEntityBuilder setData(Object data) {
        FIELDS.put(MulFields.DATA, data);
        return this;
    }

    public final MulEntityBuilder setHeader(Object headerBean) {
        FIELDS.put(MulFields.ITEM_TYPE, ItemType.HEADER);
        FIELDS.put(MulFields.BEAN, headerBean);
        return this;
    }

    public final MulEntityBuilder setFooter(Object footerBean) {
        FIELDS.put(MulFields.ITEM_TYPE, ItemType.FOOTER);
        FIELDS.put(MulFields.BEAN, footerBean);
        return this;
    }

    public final MulEntityBuilder setSpace() {
        FIELDS.put(MulFields.ITEM_TYPE, ItemType.SPACE);
        return this;
    }

    public final MulEntityBuilder setId(int size) {
        FIELDS.put(MulFields.ID,size);
        return this;
    }

    public final MulEntityBuilder setBanner(Object bannerBean) {
        FIELDS.put(MulFields.ITEM_TYPE, ItemType.BANNER);
        FIELDS.put(MulFields.BEAN, bannerBean);
        return this;
    }

    public final MulEntity build() {
        if (FIELDS.get(MulFields.SPAN_SIZE) == null) {
            setSpanSize(0);
        }
        if (FIELDS.get(MulFields.CURRENT_INDEX) == null) {
            FIELDS.put(MulFields.CURRENT_INDEX, 0);
        }
        if (FIELDS.get(MulFields.TAG) == null) {
            FIELDS.put(MulFields.TAG, 0);
        }
        return new MulEntity(FIELDS);
    }

}
