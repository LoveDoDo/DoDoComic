package com.dodo.xinyue.conan.main.index.view.dialog.list;

import com.dodo.xinyue.conan.main.index.view.dialog.builder.BaseDialogBuilder;
import com.dodo.xinyue.conan.main.index.view.dialog.callback.OnItemSelectedListener;
import com.dodo.xinyue.conan.main.index.view.dialog.list.bean.ListDialogBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ListDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/4
 */
public class ListDialogBuilder extends BaseDialogBuilder<ListDialogBuilder, DoDoListDialog> {

    private String mTitle = "";
    private final List<ListDialogBean> mDataList = new ArrayList<>();
    private OnItemSelectedListener mOnItemSelectedListener = null;

    public final ListDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final ListDialogBuilder addItem(String data, boolean isSelected) {
        mDataList.add(new ListDialogBean(data, isSelected));
        return this;
    }

    public final ListDialogBuilder addItems(ArrayList<ListDialogBean> data) {
        mDataList.addAll(data);
        return this;
    }

    public final ListDialogBuilder addItems(String[] data,int selectedIndex) {
        final int size = data.length;
        for (int i = 0; i < size; i++) {
            mDataList.add(new ListDialogBean(data[i], i == selectedIndex));
        }
        return this;
    }

    public final ListDialogBuilder onSelected(OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
        return this;
    }

    @Override
    public DoDoListDialog build() {
        return new DoDoListDialog(
                getDialogPublicParamsBean(),
                /** 下面是自定义参数 */
                mTitle,
                mDataList,
                mOnItemSelectedListener
        );
    }

}
