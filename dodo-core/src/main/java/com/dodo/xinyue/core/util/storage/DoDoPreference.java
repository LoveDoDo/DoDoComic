package com.dodo.xinyue.core.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dodo.xinyue.core.app.DoDo;

/**
 * SharedPreference工具类
 *
 * @author DoDo
 * @date 2017/9/3
 */
public final class DoDoPreference {

    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */
    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(DoDo.getAppContext());
    private static final String APP_PREFERENCES_KEY = "profile";//TODO profile:配置文件

    public static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }

    public static void setAppProfile(String value) {
        getAppPreference()
                .edit()
                .putString(APP_PREFERENCES_KEY, value)
                .apply();
    }

    public static String getAppProfile() {
        return getAppPreference().getString(APP_PREFERENCES_KEY, null);
    }

    public static JSONObject getAppProfileJson() {
        final String profile = getAppProfile();
        return JSON.parseObject(profile);
    }

    public static void removeAppProfile() {
        getAppPreference()
                .edit()
                .remove(APP_PREFERENCES_KEY)
                .apply();
    }

    public static void clearAppPreferences() {
        getAppPreference()
                .edit()
                .clear()
                .apply();
    }

    public static void setAppFlag(String key, boolean flag) {
        getAppPreference()
                .edit()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getAppFlag(String key) {
        return getAppPreference()
                .getBoolean(key, false);
    }

    public static void addCustomAppProfile(String key, String value) {
        getAppPreference()
                .edit()
                .putString(key, value)
                .apply();
    }

    public static void addCustomAppProfile(String key,Object value) {
        getAppPreference()
                .edit()
                .putString(key, JSON.toJSONString(value))
                .apply();
    }

    public static String getCustomAppProfile(String key) {
        return getAppPreference().getString(key, "");
    }

    public static void removeCustomAppProfile(@NonNull String key) {
        getAppPreference()
                .edit()
                .remove(key)
                .apply();
    }


}
