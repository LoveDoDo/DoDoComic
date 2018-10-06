package com.dodo.xinyue.conan.test;

import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * TestImageDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/6
 */
public class TestImageDialogBuilder extends BaseDialogBuilder<TestImageDialogBuilder, TestImageDialog> {

    private String mTitle = null;

    public final TestImageDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    @Override
    public TestImageDialog build() {
        if (mTitle == null) {
            mTitle = "";
        }
        return new TestImageDialog(getDialogPublicParamsBean(), mTitle);
    }
}
