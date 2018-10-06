package com.dodo.xinyue.conan.main.index.view.dialog.list.adapter;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.main.index.view.dialog.list.bean.ListDialogBean;
import com.dodo.xinyue.conan.main.index.view.dialog.list.bean.ListDialogItemType;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.ItemTypeBuilder;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulHolder;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * ThumbPreviewAdapter
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ListDialogAdapter extends MulAdapter {

    protected ListDialogAdapter(List<MulEntity> data) {
        super(data, null);
    }

    public static ListDialogAdapter create(List<MulEntity> data) {
        return new ListDialogAdapter(data);
    }

    public static ListDialogAdapter create(DataConverter converter) {
        return new ListDialogAdapter(converter.convert());
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(ListDialogItemType.ITEM, R.layout.item_dialog_list)
                .build();
    }

    @Override
    public void handle(MulHolder holder, MulEntity entity) {
        switch (holder.getItemViewType()) {
            case ListDialogItemType.ITEM:
                setItem(holder, entity);
                break;
            default:
                break;
        }
    }

    private void setItem(MulHolder holder, MulEntity entity) {
        final ListDialogBean bean = entity.getBean();
        final String title = bean.getTitle();
        final boolean selected = bean.isSelected();

        holder.setText(R.id.tvTitle, title)
                .setGone(R.id.tvSelected, selected);

    }
}
