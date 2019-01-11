package com.dodo.xinyue.conan.view.dialog.loading;

import com.dodo.xinyue.conan.view.dialog.callback.ITimeout;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanLoadingDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/28
 */
public class ConanLoadingDialogBuilder extends BaseDialogBuilder<ConanLoadingDialogBuilder,ConanLoadingDialog> {

    private ITimeout mTimeout = null;

    public final ConanLoadingDialogBuilder timeout(ITimeout timeout) {
        this.mTimeout = timeout;
        return this;
    }

    @Override
    public ConanLoadingDialog build() {
        return new ConanLoadingDialog(
                getDialogPublicParamsBean(),
                mTimeout
        );
    }
}
