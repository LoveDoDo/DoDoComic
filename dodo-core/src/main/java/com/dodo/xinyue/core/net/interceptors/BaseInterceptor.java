package com.dodo.xinyue.core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * BaseInterceptor
 *
 * @author DoDo
 * @date 2017/9/2
 */
public abstract class BaseInterceptor implements Interceptor {

    //TODO Chain:拦截链

    /**
     * get
     * 获取Url的所有参数
     *
     * @param chain
     * @return
     */
    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        //获取到请求的Url对象，其实就是对Url地址的一个封装对象，url.toString()就直接返回Url字符串
        final HttpUrl url = chain.request().url();
        //拿到请求参数的个数
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    /**
     * get
     * 获取单个参数
     *
     * @param chain
     * @param key
     * @return
     */
    protected String getUrlParameter(Chain chain, String key) {
        return getUrlParameters(chain).get(key);
    }

    /**
     * post
     * 获取请求体中的所有参数
     *
     * @param chain
     * @return
     */
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final FormBody formBody = (FormBody) chain.request().body();
        int size = 0;
        if (formBody != null) {
            size = formBody.size();
        }
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    /**
     * post
     * 获取单个参数
     *
     * @param chain
     * @param key
     * @return
     */
    protected String getBodyParameter(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }


}
