package com.dodo.xinyue.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.dodo.xinyue.core.app.DoDo;

/**
 * 测量工具类
 *
 * @author DoDo
 * @date 2017/8/31
 */
public final class DimenUtil {

    /**
     * 获取屏幕宽
     *
     * @return
     */
    public static int getScreenWidth() {
        final Resources resources = DoDo.getAppContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();//显示指标
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高
     *
     * @return
     */
    public static int getScreenHeight() {
        final Resources resources = DoDo.getAppContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * dp → px
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        final Resources resources = DoDo.getAppContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, dm);
    }

    //TypedValue.applyDimension() 将任意非标准测量单位(dp,sp...)转换为标准测量单位(px),不能反向转换！

    /**
     * sp → px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final Resources resources = DoDo.getAppContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, dm);
    }

    /**
     * px → dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        final float scale = DoDo.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px → sp
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = DoDo.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}
