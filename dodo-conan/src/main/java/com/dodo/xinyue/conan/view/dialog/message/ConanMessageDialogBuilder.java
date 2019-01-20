package com.dodo.xinyue.conan.view.dialog.message;

import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanMessageDialogBuilder
 *
 * @author DoDo
 * @date 2018/11/2
 */
public class ConanMessageDialogBuilder extends BaseDialogBuilder<ConanMessageDialogBuilder, ConanMessageDialog> {

    private JiGuangMessage mMessage = null;

    ConanMessageDialogBuilder() {
        radius(8);
        widthScale(0.85f);
    }

    public final ConanMessageDialogBuilder message(JiGuangMessage message) {
        this.mMessage = message;
        return this;
    }

    @Override
    public ConanMessageDialog build() {
        return new ConanMessageDialog(
                getDialogPublicParamsBean(),
                mMessage
        );
    }
}
