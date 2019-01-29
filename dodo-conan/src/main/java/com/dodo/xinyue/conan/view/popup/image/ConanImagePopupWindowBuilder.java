package com.dodo.xinyue.conan.view.popup.image;

import com.dodo.xinyue.core.ui.popup.base.BasePopupWindowBuilder;

/**
 * ConanImagePopupWindowBuilder
 *
 * @author DoDo
 * @date 2019/1/28
 */
public class ConanImagePopupWindowBuilder extends BasePopupWindowBuilder<ConanImagePopupWindowBuilder,ConanImagePopupWindow> {

    private String mImage;

    public ConanImagePopupWindowBuilder() {
        fullScreen(true);
    }

    public final ConanImagePopupWindowBuilder image(String image){
        this.mImage = image;
        return this;
    }

    @Override
    public ConanImagePopupWindow build() {
        return new ConanImagePopupWindow(
                getPopupWindowPublicParamsBean(),
                mImage
        );
    }
}
