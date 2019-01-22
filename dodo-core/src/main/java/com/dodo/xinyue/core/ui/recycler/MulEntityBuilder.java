package com.dodo.xinyue.core.ui.recycler;

import java.util.LinkedHashMap;

/**
 * MulEntityBuilder
 *
 * @author DoDo
 * @date 2017/9/10
 */
public class MulEntityBuilder {

    private final LinkedHashMap<Object, Object> FIELDS;//去掉static关键字,避免序列化时出现ConcurrentModificationException异常

    /**
     *  java.util.ConcurrentModificationException
     *
     * 该异常表示迭代器迭代过程中，迭代的对象发生了改变，如数据项增加或删除。
     * [解决方案]：由于迭代对象不是线程安全，在迭代的过程中，会检查modCount是否和初始modCount即expectedModCount一致，如果不一致，则认为数据有变化，迭代终止并抛出异常。常出现的场景是，两个线程同时对集合进行操作，线程1对集合进行遍历，而线程2对集合进行增加、删除操作，此时将会发生ConcurrentModificationException异常。
     * 具体方法：多线程访问时要增加同步锁，或者建议使用线程安全的集合：
     * 1. 使用ConcurrentHashMap替换HashMap，CopyOnWriteArrayList替换ArrayList；
     * 2. 或者使用使用Vector替换ArrayList，Vector是线程安全的。Vector的缺点：大量数据操作时，由于线程安全，性能比ArrayList低.
     */

    public MulEntityBuilder() {
        //清除之前的数据
//        FIELDS.clear();

        FIELDS = new LinkedHashMap<>();

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
        FIELDS.put(MulFields.ID, size);
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
