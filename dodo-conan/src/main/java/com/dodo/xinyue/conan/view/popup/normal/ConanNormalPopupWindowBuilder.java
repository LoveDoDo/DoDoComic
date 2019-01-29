package com.dodo.xinyue.conan.view.popup.normal;

import com.dodo.xinyue.core.ui.popup.base.BasePopupWindowBuilder;

/**
 * ConanNormalPopupWindowBuilder
 *
 * @author DoDo
 * @date 2019/1/25
 */
public class ConanNormalPopupWindowBuilder extends BasePopupWindowBuilder<ConanNormalPopupWindowBuilder,ConanNormalPopupWindow> {
    @Override
    public ConanNormalPopupWindow build() {
        return new ConanNormalPopupWindow(
                getPopupWindowPublicParamsBean()
        );
    }
}
