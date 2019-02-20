package com.dodo.xinyue.core.net.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * LoaderCreator
 *
 * @author DoDo
 * @date 2017/8/31
 */
public final class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(Context context, String type) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        //缓存设计，不用每次获取相同type的loader都反射一次，提高性能
        if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }


    /**
     * 通过反射，根据type（类型）拿到对应的indicator（指示器）实例
     *
     * @param name 可以是type,也可以是type对应的完整类名
     * @return
     */
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            //先取到默认的包名 com.wang.avi
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            //然后根据传入的type拼接出完整类名 com.wang.avi.indicators.XxxIndicator
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        //通过反射获取实例
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
