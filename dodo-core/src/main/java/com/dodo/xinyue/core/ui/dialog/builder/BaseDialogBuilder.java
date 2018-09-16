package com.dodo.xinyue.core.ui.dialog.builder;

import android.content.Context;

import com.dodo.xinyue.core.ui.dialog.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.DialogStyle;


/**
 * BaseDialogBuilder
 *
 * @author DoDo
 * @date 2018/1/11
 */
public class BaseDialogBuilder {

    protected Context mContext = null;
    protected DialogStyle mStyle = null;
    private BaseDialog mDialog = null;

    public BaseDialogBuilder(Context context) {
        this.mContext = context;
    }

    @SuppressWarnings("unchecked")
    public final <T> T style(DialogStyle style, Class<T> clazz) {
        this.mStyle = style;
        switch (mStyle) {
            case NORMAL_1:
//                ToastUtils.showShort("NORMAL_1");
                return (T) new DialogNormalBuilder(mContext);
            case NORMAL_2:
//                ToastUtils.showShort("NORMAL_2");
                return (T) new DialogNormalBuilder(mContext);
            case IMAGE:
//                ToastUtils.showShort("IMAGE");
                return (T) new DialogImageBuilder(mContext);
            default:
                break;
        }
        return (T) new DialogDefaultBuilder(mContext);
    }

//    public final <T> T style(DialogStyleFields<T> style) {
//        switch (mStyle) {
//            case NORMAL_1:
//                ToastUtils.showShort("NORMAL_1");
//                BaseDialogBuilder builder = style.getBuilder();
//                return (T) new DialogNormalBuilder(mContext);
//            case NORMAL_2:
//                ToastUtils.showShort("NORMAL_2");
//                break;
//            case IMAGE:
//                ToastUtils.showShort("IMAGE");
//                return (T) new DialogImageBuilder(mContext);
//            default:
//                break;
//        }
//
//
//        return
//    }

}








