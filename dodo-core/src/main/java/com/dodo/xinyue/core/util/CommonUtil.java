package com.dodo.xinyue.core.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 公共功能集
 *
 * @author DoDo
 * @date 2017/9/18
 */
public final class CommonUtil {

    /**
     * 状态栏适配(白底黑字) 原生6.0以上
     *
     * @param activity
     * @return
     */
    public static boolean setStatusBarForNative(@NonNull Activity activity) {
        final Window window = activity.getWindow();
        if (window == null) {
            return false;
        }
        activity.getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        return true;
    }

    /**
     * 状态栏适配(白底黑字) 小米MIUI
     *
     * @param activity
     * @param dark     true=状态栏透明且黑色字体  false=清除黑色字体
     * @return true=成功  false=失败
     */
    @SuppressWarnings("unchecked")
    public static boolean setStatusBarForMIUI(@NonNull Activity activity, boolean dark) {
        boolean result = false;
        final Window window = activity.getWindow();
        if (window == null) {
            return false;
        }
        Class clazz = window.getClass();
        try {
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
            }
            result = true;
        } catch (Exception e) {

        }
        return result;
    }

    public static boolean setStatusBarForMIUI(@NonNull Activity activity) {
        return setStatusBarForMIUI(activity, true);
    }

    /**
     * 状态栏适配(白底黑字) 魅族
     *
     * @param activity
     * @param dark     true=状态栏透明且黑色字体  false=清除黑色字体
     * @return true=成功  false=失败
     */
    public static boolean setStatusBarForFlyme(@NonNull Activity activity, boolean dark) {
        boolean result = false;
        final Window window = activity.getWindow();
        if (window == null) {
            return false;
        }
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            result = true;
        } catch (Exception e) {

        }
        return result;
    }

    public static boolean setStatusBarForFlyme(@NonNull Activity activity) {
        return setStatusBarForFlyme(activity, true);
    }

    /**
     * 基本类型对象或数组转换为String
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    /**
     * 格式化单位 → KB MB GB TB
     *
     * @param size
     * @param dot  保留几位小数
     * @return
     */
    public static String getFormatSize(double size, int dot) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(dot, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(dot, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(dot, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(dot, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }

    /**
     * 格式化单位 → MB
     */
    public static String getFormatSizeSimple(double size) {
        if (size <= 0) {
            return "0M";
        }
        double tempSize = size / (1024 * 1024);
        if (tempSize <= 0.1) {
            return "< 0.1M";
        }
        BigDecimal result = new BigDecimal(tempSize);
        return result.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
    }

    /**
     * 获取目录大小
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清空文件夹
     */
    public static boolean deleteFolderFile(String filePath, boolean deleteCurrentDir) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File childFile : files) {
                    deleteFolderFile(childFile.getAbsolutePath(), true);
                }
            }
            if (deleteCurrentDir) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
