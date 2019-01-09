package com.dodo.xinyue.core.ui.dialog.manager;

import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;

/**
 * DialogManager
 *
 * @author DoDo
 * @date 2018/10/14
 */
public class DialogManager {

    private BaseDialog mDialog = null;

    private static final class Holder {
        private static final DialogManager INSTANCE = new DialogManager();
    }

    public static DialogManager getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean canShow(BaseDialog dialog) {
        if (mDialog != null) {
            return false;
        }
        bindDialog(dialog);
        return true;
    }

    public final boolean canCancel() {
        if (mDialog == null) {
            return false;
        }
        if (!mDialog.isShowing()) {
            return false;
        }
        unbindDialog();
        return true;
    }

    private void bindDialog(BaseDialog dialog) {
        mDialog = dialog;
    }

    private void unbindDialog() {
        mDialog = null;
    }

}
