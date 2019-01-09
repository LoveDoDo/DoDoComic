package com.dodo.xinyue.core.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import com.dodo.xinyue.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * DebugInterceptor
 *
 * @author DoDo
 * @date 2017/9/2
 */
public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl,
                            int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))//直接返回json
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String url = chain.request().url().toString();
//        Log.d("HAHAHA", "hhhhhhh");
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        //url中不包含关键字，进行网络访问（以下访问必须声明网络权限）
//        Request request = chain.request();//返回request,添加token
//        Response response = chain.proceed(chain.request());//返回response,设置cache
//        //设置缓存
//        return response.newBuilder()
//                .removeHeader("Pragma")// 清除头信息，不清除下面无法生效
//                .removeHeader("Cache-Control")
//                .header("Cache-Control", "public, max-age=" + maxAge)//单位：秒
//                .build();
        return chain.proceed(chain.request());
    }
}
