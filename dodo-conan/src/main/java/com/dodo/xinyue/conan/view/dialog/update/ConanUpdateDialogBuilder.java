package com.dodo.xinyue.conan.view.dialog.update;

import com.dodo.xinyue.conan.view.dialog.callback.IConfirm;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanUpdateDialogBuilder
 *
 * @author DoDo
 * @date 2018/11/2
 */
public class ConanUpdateDialogBuilder extends BaseDialogBuilder<ConanUpdateDialogBuilder,ConanUpdateDialog> {

    private String mTitle = null;
    private String mContent = null;
    private String mVersionName = null;
    private String mPackageSize = null;
    private IConfirm mConfirm = null;

    public final ConanUpdateDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final ConanUpdateDialogBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public final ConanUpdateDialogBuilder versionName(String versionName) {
        this.mVersionName = versionName;
        return this;
    }

    public final ConanUpdateDialogBuilder packageSize(String packageSize) {
        this.mPackageSize = packageSize;
        return this;
    }

    public final ConanUpdateDialogBuilder confirm(IConfirm confirm) {
        this.mConfirm = confirm;
        return this;
    }

    @Override
    public ConanUpdateDialog build() {
        return new ConanUpdateDialog(
                getDialogPublicParamsBean(),
                mTitle,
                mContent,
                mVersionName,
                mPackageSize,
                mConfirm
        );
    }
}
