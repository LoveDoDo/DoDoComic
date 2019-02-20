package com.dodo.xinyue.core.ui.recycler;

import android.support.annotation.LayoutRes;

import java.util.LinkedHashMap;

/**
 * ItemTypeBuilder
 *
 * @author DoDo
 * @date 2017/9/22
 */
public final class ItemTypeBuilder {

    private final LinkedHashMap<Integer, Integer> ITEM_TYPES = new LinkedHashMap<>();

    public static ItemTypeBuilder builder() {
        return new ItemTypeBuilder();
    }

    public final ItemTypeBuilder addItemType(int type, @LayoutRes int layoutResId) {
        ITEM_TYPES.put(type, layoutResId);
        return this;
    }

    public final ItemTypeBuilder addItemTypes(LinkedHashMap<Integer, Integer> itemTypes) {
        ITEM_TYPES.putAll(itemTypes);
        return this;
    }

    public final LinkedHashMap<Integer, Integer> build() {
        return ITEM_TYPES;
    }

}
