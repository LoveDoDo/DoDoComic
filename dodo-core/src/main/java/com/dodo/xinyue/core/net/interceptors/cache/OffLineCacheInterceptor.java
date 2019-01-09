package com.dodo.xinyue.core.net.interceptors.cache;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 无网时的缓存设置
 *
 * @author DoDo
 * @date 2018/10/26
 */
public class OffLineCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected()) {
            int offlineCacheTime = 60 * 60 * 24 * 3;//3天 无网时的缓存过期时间(单位:秒)
            request = request.newBuilder()
//                        .cacheControl(new CacheControl
//                                .Builder()
//                                .maxStale(60,TimeUnit.SECONDS)
//                                .onlyIfCached()
//                                .build()
//                        ) 两种方式结果是一样的，写法不同
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + offlineCacheTime)
                    .build();
        }
        return chain.proceed(request);
    }

}
