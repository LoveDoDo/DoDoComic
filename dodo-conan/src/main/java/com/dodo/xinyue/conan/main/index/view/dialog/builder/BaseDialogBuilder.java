package com.dodo.xinyue.conan.main.index.view.dialog.builder;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;

import com.dodo.xinyue.conan.main.index.view.dialog.BaseDialog;
import com.dodo.xinyue.conan.main.index.view.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.conan.main.index.view.dialog.callback.ICloseDialog;
import com.dodo.xinyue.conan.main.index.view.dialog.callback.IOpenDialog;

/**
 * BaseDialogBuilder
 *
 * @author DoDo
 * @date 2018/10/4
 */
@SuppressWarnings("unchecked")
public abstract class BaseDialogBuilder<T extends BaseDialogBuilder, K extends BaseDialog> {

    private final DialogPublicParamsBean mDialogPublicParamsBean = new DialogPublicParamsBean();

    public final DialogPublicParamsBean getDialogPublicParamsBean() {
        return mDialogPublicParamsBean;
    }

    public abstract K build();

    public final T context(Context context) {
        mDialogPublicParamsBean.setContext(context);
        return (T) this;
    }

    public final T theme(@StyleRes int themeStyleId) {
        mDialogPublicParamsBean.setDialogTheme(themeStyleId);
        return (T) this;
    }

    public final T anim(@StyleRes int animStyleId) {
        mDialogPublicParamsBean.setDialogAnim(animStyleId);
        return (T) this;
    }

    public final T gravity(int gravity) {
        mDialogPublicParamsBean.setDialogGravity(gravity);
        return (T) this;
    }

    public final T radius(int radius) {
        mDialogPublicParamsBean.setDialogRadius(new int[]{radius, radius, radius, radius});
        return (T) this;
    }

    public final T topLeftRadius(int topLeftRadius) {
        mDialogPublicParamsBean.setDialogTopLeftRadius(topLeftRadius);
        return (T) this;
    }

    public final T topRightRadius(int topRightRadius) {
        mDialogPublicParamsBean.setDialogTopRightRadius(topRightRadius);
        return (T) this;
    }

    public final T bottomLeftRadius(int bottomLeftRadius) {
        mDialogPublicParamsBean.setDialogBottomLeftRadius(bottomLeftRadius);
        return (T) this;
    }

    public final T bottomRightRadius(int bottomRightRadius) {
        mDialogPublicParamsBean.setDialogBottomRightRadius(bottomRightRadius);
        return (T) this;
    }

    public final T widthScale(@FloatRange(from = 0f, to = 1f) float widthScale) {
        mDialogPublicParamsBean.setWidthScale(widthScale);
        return (T) this;
    }

    public final T heightScale(@FloatRange(from = 0f, to = 1f) float heightScale) {
        mDialogPublicParamsBean.setHeightScale(heightScale);
        return (T) this;
    }

    public final T canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mDialogPublicParamsBean.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return (T) this;
    }

    public final T cancelable(boolean cancelable) {
        mDialogPublicParamsBean.setCancelable(cancelable);
        return (T) this;
    }

    public final T coverStatusBar(boolean coverStatusBar) {
        mDialogPublicParamsBean.setCoverStatusBar(coverStatusBar);
        return (T) this;
    }

    public final T onOpen(IOpenDialog iOpenDialog) {
        mDialogPublicParamsBean.setIOpenDialog(iOpenDialog);
        return (T) this;
    }

    public final T onClose(ICloseDialog iCloseDialog) {
        mDialogPublicParamsBean.setICloseDialog(iCloseDialog);
        return (T) this;
    }

}
