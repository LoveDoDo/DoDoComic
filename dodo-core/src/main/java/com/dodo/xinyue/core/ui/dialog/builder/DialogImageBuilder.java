package com.dodo.xinyue.core.ui.dialog.builder;

import android.content.Context;

import com.dodo.xinyue.core.ui.dialog.DialogStyle;
import com.dodo.xinyue.core.ui.dialog.DoDoDialog;


/**
 * DialogImageBuilder
 *
 * @author DoDo
 * @date 2018/1/18
 */
public class DialogImageBuilder extends BaseDialogBuilder {

    private String mContent = null;

    DialogImageBuilder(Context context) {
        super(context);
        this.mStyle = DialogStyle.IMAGE;
    }

    public final DialogImageBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public final DoDoDialog build() {

        return null;
    }

}
