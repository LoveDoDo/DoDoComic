package com.dodo.xinyue.core.util.dimen;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.dodo.xinyue.core.app.DoDo;

/**
 * 测量工具类
 *
 * @author DoDo
 * @date 2017/8/31
 */
public final class DimenUtil {

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = DoDo.getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = DoDo.getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavigationBarHeight() {
        int result = 0;
        int resourceId = DoDo.getAppContext().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = DoDo.getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int fun2() {
        WindowManager wm = (WindowManager)
                DoDo.getAppContext().getSystemService(Context.WINDOW_SERVICE);;
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    /**
     * 判断是否显示了导航栏
     *
     */
    public static boolean isShowNavBar() {

        /**
         * 获取应用区域高度
         */
        Rect outRect1 = new Rect();
        try {
            DoDo.getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
        int activityHeight = outRect1.height();
        /**
         * 获取状态栏高度
         */
        int statuBarHeight = getStatusBarHeight();
        /**
         * 屏幕物理高度 减去 状态栏高度
         */
        int remainHeight = fun2() - statuBarHeight;
        /**
         * 剩余高度跟应用区域高度相等 说明导航栏没有显示 否则相反
         */
        if (activityHeight == remainHeight) {
            return false;
        } else {
            return true;
        }

    }

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
