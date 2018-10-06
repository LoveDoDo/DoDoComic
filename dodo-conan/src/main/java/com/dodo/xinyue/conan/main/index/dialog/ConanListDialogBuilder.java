package com.dodo.xinyue.conan.main.index.dialog;

import com.dodo.xinyue.core.ui.dialog.list.BaseListDialogBuilder;

/**
 * ConanListDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/6
 */
public class ConanListDialogBuilder extends BaseListDialogBuilder<ConanListDialogBuilder,ConanListDialog> {

    private String mTitle = null;

    public final ConanListDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    @Override
    public ConanListDialog build() {
        if (mTitle == null) {
            mTitle = "";
        }
        return new ConanListDialog(
                getDialogPublicParamsBean(),
                mTitle,
                mDataList,
                mOnItemSelectedListener
        );
    }
}
