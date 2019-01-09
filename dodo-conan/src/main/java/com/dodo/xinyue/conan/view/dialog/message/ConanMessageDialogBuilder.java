package com.dodo.xinyue.conan.view.dialog.message;

import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanMessageDialogBuilder
 *
 * @author DoDo
 * @date 2018/11/2
 */
public class ConanMessageDialogBuilder extends BaseDialogBuilder<ConanMessageDialogBuilder,ConanMessageDialog> {

    private String mTitle = null;
    private String mContent = null;

    public final ConanMessageDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final ConanMessageDialogBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    @Override
    public ConanMessageDialog build() {
        return new ConanMessageDialog(
                getDialogPublicParamsBean(),
                mTitle,
                mContent
        );
    }
}
