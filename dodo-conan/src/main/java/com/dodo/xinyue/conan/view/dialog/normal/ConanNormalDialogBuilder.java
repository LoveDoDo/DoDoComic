package com.dodo.xinyue.conan.view.dialog.normal;

import com.dodo.xinyue.conan.view.dialog.callback.IConfirm;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanNormalDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/28
 */
public class ConanNormalDialogBuilder extends BaseDialogBuilder<ConanNormalDialogBuilder,ConanNormalDialog> {

    private String mTitle = null;
    private String mContent = null;
    private IConfirm mConfirm = null;
    private boolean mOnlyOneButton = false;//是否只显示确定按钮

    ConanNormalDialogBuilder() {
        radius(12);
        widthScale(0.85f);
    }

    public final ConanNormalDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final ConanNormalDialogBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public final ConanNormalDialogBuilder confirm(IConfirm confirm) {
        this.mConfirm = confirm;
        return this;
    }

    public final ConanNormalDialogBuilder onlyOneButton(boolean onlyOneButton) {
        this.mOnlyOneButton = onlyOneButton;
        return this;
    }

    @Override
    public ConanNormalDialog build() {
        return new ConanNormalDialog(
                getDialogPublicParamsBean(),
                mTitle,
                mContent,
                mConfirm,
                mOnlyOneButton
        );
    }

}
