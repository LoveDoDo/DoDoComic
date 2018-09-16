package com.dodo.xinyue.core.net;

import com.dodo.xinyue.core.app.ConfigKeys;
import com.dodo.xinyue.core.app.DoDo;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 负责创建各种Client,包括Retrofit,OKHttpClient,RestService...
 *
 * @author DoDo
 * @date 2017/8/31
 */
public class RestCreator {

    /**
     * 惰性加载全局的参数容器
     */
    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    /**
     * 创建全局唯一的retrofit实例
     * 使用内部类holder的方式创建
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = DoDo.getConfiguration(ConfigKeys.NATIVE_API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)//设置OKHttp作为请求的client
                .addConverterFactory(ScalarsConverterFactory.create())//设置转换器
                .build();
    }

    /**
     * 创建OKHttpClient,用于提供给Retrofit
     */
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = DoDo.getConfiguration(ConfigKeys.INTERCEPTOR);

        //给OkHttpClient.Builder添加拦截器
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    //经测试，addInterceptor()方法如果拦截生效，则不加网络访问权限也可正常返回模拟结果且不报错，说明没有访问网络
                    /**
                     * addInterceptor
                     * 本地拦截器
                     *
                     * 触发intercept()方法的时机：发送请求（request）
                     *
                     * 1、拦截：直接返回需要的模拟结果数据，不访问网络
                     * 场景：测试数据
                     *
                     * 2、放行：正常访问网络请求数据（有网的情况下）
                     * 场景：统一给数据添加header,toke等
                     *
                     */
                    /**
                     * addNetworkInterceptor
                     * 网络拦截器
                     *
                     * 触发intercept()方法的时机：响应结果（response）
                     *
                     * 可拦截并设置缓存时间等，但必须调用chain.proceed(chain.request()),否则会报异常
                     * 场景：统一给数据添加cache等
                     *
                     */
                    BUILDER.addInterceptor(interceptor);
//                    BUILDER.addNetworkInterceptor(interceptor);

                }
            }

            //通过Stetho进行网络抓包
            BUILDER.addNetworkInterceptor(new StethoInterceptor());

            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)//连接超时
                .build();
    }

    /**
     * Service接口
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }
}
