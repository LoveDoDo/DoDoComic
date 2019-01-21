package com.dodo.xinyue.conan.view.dialog.list;

import com.dodo.xinyue.core.ui.dialog.list.BaseListDialogBuilder;

/**
 * ConanSimpleListDialogBuilder
 *
 * @author DoDo
 * @date 2019/1/21
 */
public class ConanSimpleListDialogBuilder extends BaseListDialogBuilder<ConanSimpleListDialogBuilder,ConanSimpleListDialog> {

    ConanSimpleListDialogBuilder() {
        radius(12);
        widthScale(0.8f);
    }

    @Override
    public ConanSimpleListDialog build() {
        return new ConanSimpleListDialog(
                getDialogPublicParamsBean(),
                mDataList,
                mOnItemSelectedListener
        );
    }

}
