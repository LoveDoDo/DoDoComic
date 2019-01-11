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

    @Deprecated
    public final boolean canShow(BaseDialog dialog) {
        if (mDialog != null) {
            return false;
        }
        bindDialog(dialog);
        return true;
    }

    @Deprecated
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

    public void bindDialog(BaseDialog dialog) {
        mDialog = dialog;
    }

    public void unbindDialog() {
        mDialog = null;
    }

    /**
     * 隐藏上一个Dialog，保证每个时刻只有一个Dialog显示
     */
    public void cancelLastDialog() {
        if (mDialog != null) {
            mDialog.cancel();
            unbindDialog();
        }

    }

}
