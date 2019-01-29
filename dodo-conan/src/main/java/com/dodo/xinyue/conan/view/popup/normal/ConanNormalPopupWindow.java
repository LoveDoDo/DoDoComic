package com.dodo.xinyue.conan.view.popup.normal;

import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.view.dialog.callback.IConfirm;
import com.dodo.xinyue.conan.view.dialog.normal.ConanNormalDialog;
import com.dodo.xinyue.core.ui.popup.base.BasePopupWindow;
import com.dodo.xinyue.core.ui.popup.bean.PopupWindowPublicParamsBean;

import butterknife.OnClick;

/**
 * ConanNormalPopupWindow
 *
 * @author DoDo
 * @date 2019/1/25
 */
public class ConanNormalPopupWindow extends BasePopupWindow {

    @OnClick(R2.id.tvTitle)
    void onTvTitleClicked() {
//        dismiss();
        ConanNormalDialog.builder()
                .title("哈哈")
                .content("test")
                .confirm(new IConfirm() {
                    @Override
                    public void onConfirm() {
                        ConanNormalPopupWindow.this.dismiss();
                    }
                })
                .build()
                .show();
    }

    ConanNormalPopupWindow(PopupWindowPublicParamsBean bean) {
        super(bean);
    }

    public static ConanNormalPopupWindowBuilder builder() {
        return new ConanNormalPopupWindowBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.temp_popup;
    }

    @Override
    public void onBindView(View rootView) {

    }
}
