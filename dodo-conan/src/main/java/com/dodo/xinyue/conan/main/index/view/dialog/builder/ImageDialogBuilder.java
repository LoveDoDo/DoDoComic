package com.dodo.xinyue.conan.main.index.view.dialog.builder;

import com.dodo.xinyue.conan.main.index.view.dialog.DoDoImageDialog;

/**
 * ImageDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/4
 */
public class ImageDialogBuilder extends BaseDialogBuilder<ImageDialogBuilder, DoDoImageDialog> {

    private int mImgId = 0;
    private String mTitle = null;

    public final ImageDialogBuilder img(int imgId) {
        this.mImgId = imgId;
        return this;
    }

    public final ImageDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    @Override
    public DoDoImageDialog build() {
        return new DoDoImageDialog(
                getDialogPublicParamsBean(),
                /** 下面是自定义参数 */
                mTitle
        );
    }

}
