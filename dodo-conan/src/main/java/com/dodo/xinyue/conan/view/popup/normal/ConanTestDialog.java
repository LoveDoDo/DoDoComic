package com.dodo.xinyue.conan.view.popup.normal;

import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;

/**
 * ConanTestDialog
 *
 * @author DoDo
 * @date 2019/1/28
 */
public class ConanTestDialog extends BaseDialog{

    public ConanTestDialog(DialogPublicParamsBean bean) {
        super(bean);
    }

    public static ConanTestDialogBuilder builder() {
        return new ConanTestDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.temp_popup;
    }

    @Override
    public void onBindView(View rootView) {

    }
}
