package com.dodo.xinyue.conan.main.index.dialog.adapter;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogBean;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogItemType;
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
public class ConanListDialogAdapter extends MulAdapter {

    protected ConanListDialogAdapter(List<MulEntity> data) {
        super(data, null);
    }

    public static ConanListDialogAdapter create(List<MulEntity> data) {
        return new ConanListDialogAdapter(data);
    }

    public static ConanListDialogAdapter create(DataConverter converter) {
        return new ConanListDialogAdapter(converter.convert());
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(ListDialogItemType.ITEM, R.layout.item_list_dialog)
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
