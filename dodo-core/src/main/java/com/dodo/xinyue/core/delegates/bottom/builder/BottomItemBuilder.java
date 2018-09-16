package com.dodo.xinyue.core.delegates.bottom.builder;


import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;

import java.util.LinkedHashMap;

/**
 * BottomItemBuilder
 *
 * @author DoDo
 * @date 2017/9/7
 */
public final class BottomItemBuilder {

    private final LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> ITEMS = new LinkedHashMap<>();

    public static BottomItemBuilder builder() {
        return new BottomItemBuilder();
    }

    public final BottomItemBuilder addItem(BaseBottomTabBean bean, BaseBottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final BottomItemBuilder addItems(LinkedHashMap<BaseBottomTabBean,BaseBottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> build() {
        return ITEMS;
    }

}
