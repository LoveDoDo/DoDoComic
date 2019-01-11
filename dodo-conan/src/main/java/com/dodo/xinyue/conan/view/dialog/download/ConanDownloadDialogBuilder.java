package com.dodo.xinyue.conan.view.dialog.download;

import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanDownloadDialogBuilder
 *
 * @author DoDo
 * @date 2019/1/11
 */
public class ConanDownloadDialogBuilder extends BaseDialogBuilder<ConanDownloadDialogBuilder, ConanDownloadDialog> {

    private String mPackageSize = null;

    public final ConanDownloadDialogBuilder packageSize(String packageSize) {
        this.mPackageSize = packageSize;
        return this;
    }

    @Override
    public ConanDownloadDialog build() {
        return new ConanDownloadDialog(
                getDialogPublicParamsBean(),
                mPackageSize
        );
    }
}
