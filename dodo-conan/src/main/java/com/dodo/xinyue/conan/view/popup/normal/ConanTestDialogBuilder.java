package com.dodo.xinyue.conan.view.popup.normal;

import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanTestDialogBuilder
 *
 * @author DoDo
 * @date 2019/1/28
 */
public class ConanTestDialogBuilder extends BaseDialogBuilder<ConanTestDialogBuilder,ConanTestDialog> {
    @Override
    public ConanTestDialog build() {
        return new ConanTestDialog(
                getDialogPublicParamsBean()
        );
    }
}
