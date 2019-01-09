package com.dodo.xinyue.core.net.interceptors.cache;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 有网时的缓存设置
 *
 * @author DoDo
 * @date 2018/10/26
 */
public class NetCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int onlineCacheTime = 60 * 10;//10分钟 有网时的缓存过期时间(单位:秒)。0=不缓存
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=" + onlineCacheTime)
                .removeHeader("Pragma")
                .build();
    }

}
