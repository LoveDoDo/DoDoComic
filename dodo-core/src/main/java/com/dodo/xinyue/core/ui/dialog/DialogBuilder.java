package com.dodo.xinyue.core.ui.dialog;

/**
 * DialogBuilder
 *
 * @author DoDo
 * @date 2018/1/19
 */
public class DialogBuilder {

    private BaseDialog mDialog = null;
//    private

    DialogBuilder() {
    }

    public final DialogBuilder dialog(BaseDialog dialog) {
        this.mDialog = dialog;
        return this;
    }

    public final DoDoDialog build() {
        return new DoDoDialog(
                mDialog
        );
    }


}
