package com.dodo.xinyue.conan.main.index.view.dialog.custom;

import com.dodo.xinyue.conan.main.index.view.dialog.builder.BaseDialogBuilder;

/**
 * CustomImageDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/4
 */
public class CustomImageDialogBuilder extends BaseDialogBuilder<CustomImageDialogBuilder, ConanDialog> {

    private String mTitle = null;

    public final CustomImageDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final CustomImageDialogBuilder test() {
        return this;
    }

    @Override
    public ConanDialog build() {
        return new ConanDialog(
                getDialogPublicParamsBean(),
                mTitle
        );
    }
}
