package com.dodo.xinyue.conan.view.dialog.list.adapter;

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
 * ConanSimpleListDialogAdapter
 *
 * @author DoDo
 * @date 2019/1/21
 */
public class ConanSimpleListDialogAdapter extends MulAdapter {

    protected ConanSimpleListDialogAdapter(List<MulEntity> data) {
        super(data, null);
    }

    public static ConanSimpleListDialogAdapter create(List<MulEntity> data) {
        return new ConanSimpleListDialogAdapter(data);
    }

    public static ConanSimpleListDialogAdapter create(DataConverter converter) {
        return new ConanSimpleListDialogAdapter(converter.convert());
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(ListDialogItemType.ITEM, R.layout.item_simple_list_dialog)
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

        holder.setText(R.id.tvTitle, title);
    }

}
