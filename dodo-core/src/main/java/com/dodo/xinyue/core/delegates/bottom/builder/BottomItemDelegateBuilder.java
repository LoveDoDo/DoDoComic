package com.dodo.xinyue.core.delegates.bottom.builder;


import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;

import java.util.ArrayList;

/**
 * BottomItemDelegateBuilder
 *
 * @author DoDo
 * @date 2017/9/15
 */
public final class BottomItemDelegateBuilder {

    private final ArrayList<BaseBottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();

    public static BottomItemDelegateBuilder builder() {
        return new BottomItemDelegateBuilder();
    }

    public final BottomItemDelegateBuilder addItemDelegate(BaseBottomItemDelegate delegate) {
        ITEM_DELEGATES.add(delegate);
        return this;
    }

    public final BottomItemDelegateBuilder addItemDelegates(ArrayList<BaseBottomItemDelegate> delegates) {
        ITEM_DELEGATES.addAll(delegates);
        return this;
    }

    public final ArrayList<BaseBottomItemDelegate> build() {
        return ITEM_DELEGATES;
    }

}
