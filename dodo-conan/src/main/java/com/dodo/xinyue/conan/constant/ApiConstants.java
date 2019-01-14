package com.dodo.xinyue.conan.constant;

import android.view.Gravity;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.ui.dialog.options.DialogOptions;

/**
 * ApiConstants
 *
 * @author DoDo
 * @date 2018/10/26
 */
public class ApiConstants {

    /**
     * listDialog默认样式
     */
    public static final DialogOptions LIST_DIALOG_OPTIONS =
            new DialogOptions()
                    .anim(R.style.DialogBottomAnim)
                    .topLeftRadius(6)
                    .topRightRadius(6)
                    .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

    /**
     * 配置文件版本
     * <p>
     * 如果版本号不一致，则清空配置文件
     */
    public static final int CONFIG_VERSION = 1;
    /**
     * 首页偏好配置版本
     * <p>
     * 如果版本号不一致，则更新默认配置(清除偏好配置)
     */
    public static final int INDEX_PREFERENCE_CONFIG_VERSION = 1;

    /**
     * 配置文件版本 key
     */
    public static final String KEY_CONFIG_VERSION = "config_version";
    /**
     * 首页偏好配置版本 key
     */
    public static final String KEY_INDEX_PREFERENCE_CONFIG_VERSION = "index_preference_config_version";

    /**
     * 默认索引
     */
    public static final String KEY_LANGUAGE_DEFAULT_INDEX = "language_default";
    public static final String KEY_TYPE_DEFAULT_INDEX = "type_default";
    public static final String KEY_SOURCE_DEFAULT_INDEX = "source_default";
    public static final String KEY_FORM_DEFAULT_INDEX = "form_default";

    /**
     * SharedPreferences Key
     */
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SOURCE = "source";
    public static final String KEY_FORM = "form";

    /**
     * 语言 序号随便定义，同一类型不重复即可，下同
     */
    public static final int LANGUAGE_CHINESE = 1;//国语
    public static final int LANGUAGE_JAPANESE = 2;//日语
    /**
     * 类型
     */
    public static final int TYPE_TV = 1;//TV版
    public static final int TYPE_MOVIE = 2;//剧场版
    /**
     * 来源
     */
    public static final int SOURCE_IQIYI = 1;//爱奇艺
    public static final int SOURCE_YOUKU = 2;//优酷
    /**
     * 展示方式
     */
    public static final int FORM_TEXT = 1;//文字列表
    public static final int FORM_NUMBER = 2;//数字列表
    public static final int FORM_IMAGE_TEXT = 3;//图文列表
    public static final int FORM_GRID = 4;//网格列表

    /**
     * 默认配置
     */
    public static final Integer[] LANGUAGE_DEFAULT = new Integer[]{
            LANGUAGE_CHINESE,
            LANGUAGE_JAPANESE
    };
    /**
     * 默认配置
     */
    public static final Integer[] TYPE_DEFAULT = new Integer[]{
            TYPE_TV,
            TYPE_MOVIE
    };
    /**
     * 默认配置
     */
    public static final Integer[] SOURCE_DEFAULT = new Integer[]{
            SOURCE_IQIYI,
            SOURCE_YOUKU
    };
    /**
     * 默认配置
     */
    public static final Integer[] FORM_DEFAULT = new Integer[]{
            FORM_TEXT,
            FORM_NUMBER,
            FORM_IMAGE_TEXT,
            FORM_GRID
    };

    /**
     * 快速打开APP，跳过动画
     */
    public static final String KEY_QUICKLY_OPEN_APP = "quickly_open_app";

    /**
     * 接收新消息通知
     */
    public static final String KEY_RECEIVE_NEW_MESSAGE = "receive_new_message";

    /**
     * 消息展示形式
     */
    public static final String KEY_MESSAGE_FORM = "message_form";

    /**
     * 消息类型
     */
    public static final String KEY_MESSAGE_TYPE = "message_type";

    /**
     * Bugly初始化完成标识
     */
    public static final String IS_BUGLY_INIT = "is_bugly_init";

    /**
     * 反馈URL
     */
    public static final String FEEDBACK_URL = "https://support.qq.com/product/52238";

}
