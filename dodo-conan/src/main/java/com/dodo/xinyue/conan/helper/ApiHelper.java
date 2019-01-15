package com.dodo.xinyue.conan.helper;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.core.util.storage.DoDoPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiHelper
 *
 * @author DoDo
 * @date 2018/10/30
 */
public class ApiHelper {

    public static String getLanguageStr(int languageIndex) {
        String languageStr;
        switch (languageIndex) {
            case ApiConstants.LANGUAGE_CHINESE:
                languageStr = "国语";
                break;
            case ApiConstants.LANGUAGE_JAPANESE:
                languageStr = "日语";
                break;
            default:
                languageStr = "";
                ToastUtils.showShort("Language configuration error, please check profile");
                break;
        }
        return languageStr;
    }

    public static String getTypeStr(int typeIndex) {
        String typeStr;
        switch (typeIndex) {
            case ApiConstants.TYPE_TV:
                typeStr = "TV版";
                break;
            case ApiConstants.TYPE_MOVIE:
                typeStr = "剧场版";
                break;
            default:
                typeStr = "";
                ToastUtils.showShort("Type configuration error, please check profile");
                break;
        }
        return typeStr;
    }

    public static String getSourceStr(int sourceIndex) {
        String sourceStr;
        switch (sourceIndex) {
            case ApiConstants.SOURCE_IQIYI:
                sourceStr = "爱奇艺";
                break;
            case ApiConstants.SOURCE_YOUKU:
                sourceStr = "优酷";
                break;
            default:
                sourceStr = "";
                ToastUtils.showShort("Source configuration error, please check profile");
                break;
        }
        return sourceStr;
    }

    public static String getFormStr(int formIndex) {
        String formStr;
        switch (formIndex) {
            case ApiConstants.FORM_TEXT:
                formStr = "文字列表";
                break;
            case ApiConstants.FORM_NUMBER:
                formStr = "数字列表";
                break;
            case ApiConstants.FORM_IMAGE_TEXT:
                formStr = "图文列表";
                break;
            case ApiConstants.FORM_GRID:
                formStr = "网格列表";
                break;
            default:
                formStr = "";
                ToastUtils.showShort("Form configuration error, please check profile");
                break;
        }
        return formStr;
    }

    public static int getConfigVersion() {
        final String configVersion = DoDoPreference.getCustomAppProfile(ApiConstants.KEY_CONFIG_VERSION);
        if (TextUtils.isEmpty(configVersion)) {
            return 0;
        }
        return Integer.valueOf(configVersion);
    }

    public static int getIndexPreferenceConfigVersion() {
        final String configVersion = DoDoPreference.getCustomAppProfile(ApiConstants.KEY_INDEX_PREFERENCE_CONFIG_VERSION);
        if (TextUtils.isEmpty(configVersion)) {
            return 0;
        }
        return Integer.valueOf(configVersion);
    }

    public static void setConfigVersion() {
        DoDoPreference.addCustomAppProfile(ApiConstants.KEY_CONFIG_VERSION, ApiConstants.CONFIG_VERSION);
    }

    public static void setIndexPreferenceConfigVersion() {
        DoDoPreference.addCustomAppProfile(ApiConstants.KEY_INDEX_PREFERENCE_CONFIG_VERSION, ApiConstants.INDEX_PREFERENCE_CONFIG_VERSION);
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> getIndexPreferenceConfig(String key) {
        String languageJson = DoDoPreference.getCustomAppProfile(key);
        if (TextUtils.isEmpty(languageJson)) {
            return null;
        }
        return JSON.parseObject(languageJson, ArrayList.class);
    }

    public static void saveIndexPreferenceConfig(String key, Integer[] data) {
        DoDoPreference.addCustomAppProfile(key, data);
    }

    public static int getDefaultIndex(String key) {
        String defaultIndex = DoDoPreference.getCustomAppProfile(key);
        if (TextUtils.isEmpty(defaultIndex)) {
            switch (key) {
                case ApiConstants.KEY_LANGUAGE_DEFAULT_INDEX:
                    return ApiConstants.LANGUAGE_CHINESE;
                case ApiConstants.KEY_TYPE_DEFAULT_INDEX:
                    return ApiConstants.TYPE_TV;
                case ApiConstants.KEY_SOURCE_DEFAULT_INDEX:
                    return ApiConstants.SOURCE_IQIYI;
                case ApiConstants.KEY_FORM_DEFAULT_INDEX:
                    return ApiConstants.FORM_TEXT;
                default:
                    break;
            }
            return 0;
        }
        return Integer.valueOf(defaultIndex);
    }

    public static void saveDefaultIndex(String key, int defaultIndex) {
        DoDoPreference.addCustomAppProfile(key, defaultIndex);
    }

    public static void clearConfig() {
        DoDoPreference.clearAppPreferences();
    }

    public static void clearIndexPreferenceConfig() {
        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_LANGUAGE);
        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_TYPE);
        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_SOURCE);
        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_FORM);

        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_LANGUAGE_DEFAULT_INDEX);
        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_TYPE_DEFAULT_INDEX);
        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_SOURCE_DEFAULT_INDEX);
        DoDoPreference.removeCustomAppProfile(ApiConstants.KEY_FORM_DEFAULT_INDEX);
    }

    public static boolean isQuicklyOpenApp() {
        return DoDoPreference.getAppPreference().getBoolean(ApiConstants.KEY_QUICKLY_OPEN_APP, false);
    }

    public static void setQuicklyOpenApp(boolean isQuicklyOpenApp) {
        DoDoPreference.getAppPreference()
                .edit()
                .putBoolean(ApiConstants.KEY_QUICKLY_OPEN_APP, isQuicklyOpenApp)
                .apply();
    }

    public static boolean isReceiveNewMessage() {
        return DoDoPreference.getAppPreference().getBoolean(ApiConstants.KEY_RECEIVE_NEW_MESSAGE, true);
    }

    public static void setReceiveNewMessage(boolean isReceiveNewMessage) {
        DoDoPreference.getAppPreference()
                .edit()
                .putBoolean(ApiConstants.KEY_RECEIVE_NEW_MESSAGE, isReceiveNewMessage)
                .apply();
    }

    public static int getMessageForm() {
        return DoDoPreference.getAppPreference().getInt(ApiConstants.KEY_MESSAGE_FORM, JiGuangMessage.FORM_DIALOG);
    }

    public static void setMessageForm(int messageForm) {
        DoDoPreference.getAppPreference()
                .edit()
                .putInt(ApiConstants.KEY_MESSAGE_FORM, messageForm)
                .apply();
    }

    public static long getLastCheckUpdateTimestamp() {
        return DoDoPreference.getAppPreference().getLong(ApiConstants.LAST_CHECK_UPDATE_TIMESTAMP, 0L);
    }

    public static void setCurrentCheckUpdateTimestamp() {
        DoDoPreference.getAppPreference()
                .edit()
                .putLong(ApiConstants.LAST_CHECK_UPDATE_TIMESTAMP, System.currentTimeMillis())
                .apply();
    }

}
