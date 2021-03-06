package com.dodo.xinyue.core.ui.dialog.bean;

import android.content.Context;
import android.view.Gravity;

import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.IClose;
import com.dodo.xinyue.core.ui.dialog.callback.IOpen;

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
    private int mTheme = BaseDialog.DEFAULT_THEME;//主题/样式
    private int mAnim = BaseDialog.DEFAULT_ANIM;//动画
    private int mGravity = Gravity.CENTER;//显示位置 多个用 | 分割
    private int[] mRadius = new int[]{0, 0, 0, 0};//圆角 单位dp {左上 右上 左下 右下}
    private float mWidthScale = 1f;//宽度缩放比例 0-1
    private float mHeightScale = 1f;//高度缩放比例 0-1
    private boolean mCanceledOnTouchOutside = true;//窗口外点击取消Dialog
    private boolean mCancelable = true;//返回键取消Dialog
    private boolean mCoverStatusBar = false;//覆盖顶部状态栏
    private boolean mCoverNavigationBar = false;//覆盖底部导航栏
    private boolean mFullScreen = false;//全屏 会自动覆盖状态栏和导航栏
    private boolean mBackgroundDimEnabled = true;//背景变暗
    private IOpen mIOpen = null;//回调 打开Dialog
    private IClose mIClose = null;//回调 关闭Dialog

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public int getTheme() {
        return mTheme;
    }

    public void setTheme(int theme) {
        this.mTheme = theme;
    }

    public int getAnim() {
        return mAnim;
    }

    public void setAnim(int anim) {
        this.mAnim = anim;
    }

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int[] getRadius() {
        return mRadius;
    }

    public void setTopLeftRadius(int topLeftRadius) {
        this.mRadius[0] = topLeftRadius;
    }

    public void setTopRightRadius(int topRightRadius) {
        this.mRadius[1] = topRightRadius;
    }

    public void setBottomLeftRadius(int bottomLeftRadius) {
        this.mRadius[2] = bottomLeftRadius;
    }

    public void setBottomRightRadius(int bottomRightRadius) {
        this.mRadius[3] = bottomRightRadius;
    }

    public void setRadius(int[] radius) {
        this.mRadius = radius;
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

    public boolean isFullScreen() {
        return mFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.mFullScreen = fullScreen;
    }

    public boolean isBackgroundDimEnabled() {
        return mBackgroundDimEnabled;
    }

    public void setBackgroundDimEnabled(boolean backgroundDimEnabled) {
        this.mBackgroundDimEnabled = backgroundDimEnabled;
    }

    public IOpen getIOpen() {
        return mIOpen;
    }

    public void setIOpen(IOpen iOpen) {
        this.mIOpen = iOpen;
    }

    public IClose getIClose() {
        return mIClose;
    }

    public void setIClose(IClose iClose) {
        this.mIClose = iClose;
    }
}
