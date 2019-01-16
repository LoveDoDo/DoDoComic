package com.dodo.xinyue.core.ui.dialog.manager;

import com.dodo.xinyue.core.ui.dialog.ILoadingDialog;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;

import java.util.ArrayList;

/**
 * DialogManager
 *
 * @author DoDo
 * @date 2018/10/14
 */
public class DialogManager {

    private BaseDialog mDialog = null;
    private final ArrayList<BaseDialog> PENDING_DIALOGS = new ArrayList<>();//缓存Dialog,延时显示

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

    /**
     * 延时显示
     */
    public void pendingShow(BaseDialog dialog) {
        if (mDialog == null) {
            //当前没有Dialog正在显示
            dialog.show();
            return;
        }
        if (mDialog instanceof ILoadingDialog) {
            mDialog.cancel();
            dialog.show();
            return;
        }
        PENDING_DIALOGS.add(dialog);
    }

    /**
     * 检查是否有缓存Dialog,如果有则显示
     */
    public void checkPending() {
        if (PENDING_DIALOGS.size() == 0) {
            return;
        }
        PENDING_DIALOGS.get(0).show();
        PENDING_DIALOGS.remove(0);
    }

}
