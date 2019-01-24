package com.dodo.xinyue.core.ui.dialog.bean;

import android.content.Context;
import android.view.Gravity;

import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.ICloseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.IOpenDialog;

/**
 * DialogPublicParamsBean
 *
 * @author DoDo
 * @date 2018/10/5
 */
public class DialogPublicParamsBean {

    /**
     * 公共参数
     */
    private Context mContext = DoDo.getActivity();//上下文 不能传Application,必须传Activity
    private int mDialogTheme = BaseDialog.DEFAULT_THEME;//主题/样式
    private int mDialogAnim = BaseDialog.DEFAULT_ANIM;//动画
    private int mDialogGravity = Gravity.CENTER;//显示位置 多个用 | 分割
    private int[] mDialogRadius = new int[]{0, 0, 0, 0};//圆角 单位dp {左上 右上 左下 右下}
    private float mWidthScale = 1f;//宽度缩放比例 0-1
    private float mHeightScale = 1f;//高度缩放比例 0-1
    private boolean mCanceledOnTouchOutside = true;//窗口外点击取消Dialog
    private boolean mCancelable = true;//返回键取消Dialog
    private boolean mCoverStatusBar = true;//覆盖顶部状态栏
    private boolean mCoverNavigationBar = true;//覆盖底部导航栏
    private boolean mBackgroundDimEnabled = true;//背景变暗
    private IOpenDialog mIOpenDialog = null;//回调 打开Dialog
    private ICloseDialog mICloseDialog = null;//回调 关闭Dialog

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public int getDialogTheme() {
        return mDialogTheme;
    }

    public void setDialogTheme(int dialogTheme) {
        this.mDialogTheme = dialogTheme;
    }

    public int getDialogAnim() {
        return mDialogAnim;
    }

    public void setDialogAnim(int dialogAnim) {
        this.mDialogAnim = dialogAnim;
    }

    public int getDialogGravity() {
        return mDialogGravity;
    }

    public void setDialogGravity(int dialogGravity) {
        this.mDialogGravity = dialogGravity;
    }

    public int[] getDialogRadius() {
        return mDialogRadius;
    }

    public void setDialogTopLeftRadius(int topLeftRadius) {
        this.mDialogRadius[0] = topLeftRadius;
    }

    public void setDialogTopRightRadius(int topRightRadius) {
        this.mDialogRadius[1] = topRightRadius;
    }

    public void setDialogBottomLeftRadius(int bottomLeftRadius) {
        this.mDialogRadius[2] = bottomLeftRadius;
    }

    public void setDialogBottomRightRadius(int bottomRightRadius) {
        this.mDialogRadius[3] = bottomRightRadius;
    }

    public void setDialogRadius(int[] dialogRadius) {
        this.mDialogRadius = dialogRadius;
    }

    public float getWidthScale() {
        return mWidthScale;
    }

    public void setWidthScale(float widthScale) {
        this.mWidthScale = widthScale;
    }

    public float getHeightScale() {
        return mHeightScale;
    }

    public void setHeightScale(float heightScale) {
        this.mHeightScale = heightScale;
    }

    public boolean isCanceledOnTouchOutside() {
        return mCanceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    public boolean isCancelable() {
        return mCancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
    }

    public boolean isCoverStatusBar() {
        return mCoverStatusBar;
    }

    public void setCoverStatusBar(boolean coverStatusBar) {
        this.mCoverStatusBar = coverStatusBar;
    }

    public boolean isCoverNavigationBar() {
        return mCoverNavigationBar;
    }

    public void setCoverNavigationBar(boolean coverNavigationBar) {
        this.mCoverNavigationBar = coverNavigationBar;
    }

    public boolean isBackgroundDimEnabled() {
        return mBackgroundDimEnabled;
    }

    public void setBackgroundDimEnabled(boolean backgroundDimEnabled) {
        this.mBackgroundDimEnabled = backgroundDimEnabled;
    }

    public IOpenDialog getIOpenDialog() {
        return mIOpenDialog;
    }

    public void setIOpenDialog(IOpenDialog iOpenDialog) {
        this.mIOpenDialog = iOpenDialog;
    }

    public ICloseDialog getICloseDialog() {
        return mICloseDialog;
    }

    public void setICloseDialog(ICloseDialog iCloseDialog) {
        this.mICloseDialog = iCloseDialog;
    }
}
