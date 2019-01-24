package com.dodo.xinyue.conan.view.dialog.image;

import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanImageDialogBuilder
 *
 * @author DoDo
 * @date 2019/1/23
 */
public class ConanImageDialogBuilder extends BaseDialogBuilder<ConanImageDialogBuilder, ConanImageDialog> {

    private String mImage;

    ConanImageDialogBuilder() {
        coverStatusBar(true);
        coverNavigationBar(true);
    }

    public final ConanImageDialogBuilder image(String image) {
        this.mImage = image;
        return this;
    }

    @Override
    public ConanImageDialog build() {
        return new ConanImageDialog(
                getDialogPublicParamsBean(),
                mImage
        );
    }
}
