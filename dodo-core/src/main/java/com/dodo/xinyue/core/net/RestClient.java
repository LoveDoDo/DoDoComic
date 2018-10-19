package com.dodo.xinyue.core.net;

import android.content.Context;

import com.dodo.xinyue.core.net.callback.IError;
import com.dodo.xinyue.core.net.callback.IFailure;
import com.dodo.xinyue.core.net.callback.IRequest;
import com.dodo.xinyue.core.net.callback.ISuccess;
import com.dodo.xinyue.core.net.callback.RequestCallbacks;
import com.dodo.xinyue.core.net.download.DownloadHandler;
import com.dodo.xinyue.core.net.loader.DoDoLoader;
import com.dodo.xinyue.core.net.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 请求的具体实现类
 *
 * @author DoDo
 * @date 2017/8/31
 */
public class RestClient {

    //final修饰，保证多线程操作时的原子性
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;//后缀名
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    private Call<String> mCall = null;

    //切换大小写快捷键：Ctrl+Shift+U
    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IRequest request,
                      String downloadDir,
                      String extension,
                      String name,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      LoaderStyle loaderStyle,
                      File file,
                      Context context) {
        this.URL = url;
        PARAMS.putAll(params);//全局变量前面不用加this
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    /**
     * 真正的请求
     *
     * @param method
     */
    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
//        Call<String> call = null;

        if (REQUEST != null) {
            //请求开始
            REQUEST.onRequestStart();
        }

        //显示进度条
        if (LOADER_STYLE != null) {
            DoDoLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                mCall = service.get(URL, PARAMS);
                break;
            case POST:
                mCall = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                mCall = service.postRaw(URL, BODY);
                break;
            case PUT:
                mCall = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                mCall = service.putRaw(URL, BODY);
                break;
            case DELETE:
                mCall = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                mCall = service.upload(URL, body);
            default:
                break;
        }

        if (mCall != null) {
            //正式开始请求
            //异步
            mCall.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    /**
     * 对外公开的方法，下同
     */
    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(
                URL,
                REQUEST,
                DOWNLOAD_DIR,
                EXTENSION,
                NAME,
                SUCCESS,
                FAILURE,
                ERROR
        ).handleDownload();
    }

    public final void cancle() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }

}
