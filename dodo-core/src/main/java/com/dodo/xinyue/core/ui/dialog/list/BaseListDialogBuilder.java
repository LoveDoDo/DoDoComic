package com.dodo.xinyue.core.ui.dialog.list;

import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;
import com.dodo.xinyue.core.ui.dialog.callback.OnItemSelectedListener;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogBean;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseListDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/4
 */
@SuppressWarnings("unchecked")
public abstract class BaseListDialogBuilder<T extends BaseDialogBuilder, K extends BaseDialog> extends BaseDialogBuilder<T, K> {

    protected final List<ListDialogBean> mDataList = new ArrayList<>();
    protected OnItemSelectedListener mOnItemSelectedListener = null;

    public final T addItem(String data) {
        return addItem(data, false);
    }

    public final T addItem(String data, boolean isSelected) {
        mDataList.add(new ListDialogBean(data, isSelected));
        return (T) this;
    }

    public final T addItems(ArrayList<ListDialogBean> data) {
        mDataList.addAll(data);
        return (T) this;
    }

    public final T addItems(String[] data, int selectedIndex) {
        final int size = data.length;
        for (int i = 0; i < size; i++) {
            mDataList.add(new ListDialogBean(data[i], i == selectedIndex));
        }
        return (T) this;
    }

    public final T onSelected(OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
        return (T) this;
    }

}
