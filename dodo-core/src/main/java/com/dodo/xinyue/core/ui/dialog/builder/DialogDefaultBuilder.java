package com.dodo.xinyue.core.ui.dialog.builder;

import android.content.Context;

import com.dodo.xinyue.core.ui.dialog.DialogStyle;
import com.dodo.xinyue.core.ui.dialog.DoDoDialog;


/**
 * DialogDefaultBuilder
 *
 * @author DoDo
 * @date 2018/1/18
 */
public class DialogDefaultBuilder extends BaseDialogBuilder {

    private Context mContext = null;
    private DialogStyle mStyle = null;
    private String mTitle = null;
    private String mContent = null;

    public DialogDefaultBuilder(Context context) {
        super(context);
        this.mContext = context;
        this.mStyle = DialogStyle.NORMAL_1;
    }

    public final DialogDefaultBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final DialogDefaultBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public final DoDoDialog build() {
        return null;
    }

}
