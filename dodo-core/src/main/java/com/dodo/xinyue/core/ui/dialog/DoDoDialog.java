package com.dodo.xinyue.core.ui.dialog;

/**
 * DoDoDialog
 *
 * @author DoDo
 * @date 2018/1/11
 */
public class DoDoDialog {

    private final BaseDialog DIALOG;

    public DoDoDialog(BaseDialog dialog) {
        this.DIALOG = dialog;
    }

    public static DialogBuilder builder() {
        return new DialogBuilder();
    }

    public final void show() {
        DialogController.showDialog(DIALOG);
    }

}
