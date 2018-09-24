package com.dodo.xinyue.test.interceptor;

import com.dodo.xinyue.core.net.interceptors.BaseInterceptor;
import com.dodo.xinyue.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * TestInterceptor
 *
 * @author DoDo
 * @date 2018/9/20
 */
public class TestInterceptor extends BaseInterceptor {

    private final String DEBUG_URL = "TestInterceptor";

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

    private Response debugResponse(Chain chain, String filePath) {
        final String json = FileUtil.getAssetsFile(filePath);
        return getResponse(chain, json);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, "test/test.json");
        }
        return chain.proceed(chain.request());
    }

}
