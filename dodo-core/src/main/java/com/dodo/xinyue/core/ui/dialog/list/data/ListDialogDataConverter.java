package com.dodo.xinyue.core.ui.dialog.list.data;

import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogBean;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogItemType;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ListDialogDataConverter
 *
 * @author DoDo
 * @date 2018/10/6
 */
public class ListDialogDataConverter extends DataConverter {

    private List<ListDialogBean> mData = null;

    @Override
    public ArrayList<MulEntity> convert() {

        if (mData == null) {
            return ENTITIES;
        }

        for (ListDialogBean bean : mData) {
            final MulEntity entity = MulEntity.builder()
                    .setItemType(ListDialogItemType.ITEM)
                    .setBean(bean)
                    .build();
            ENTITIES.add(entity);
        }

        return ENTITIES;
    }

    public final ListDialogDataConverter setData(List<ListDialogBean> data) {
        this.mData = data;
        return this;
    }

}
