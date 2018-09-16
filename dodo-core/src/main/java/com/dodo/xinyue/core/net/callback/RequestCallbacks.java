package com.dodo.xinyue.core.net.callback;

import android.os.Handler;

import com.dodo.xinyue.core.app.ConfigKeys;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.net.loader.DoDoLoader;
import com.dodo.xinyue.core.net.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 统一管理处理请求的回调
 *
 * @author DoDo
 * @date 2017/8/31
 */
public final class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    //static 避免内存泄漏
    private static final Handler HANDLER = DoDo.getHandler();

    public RequestCallbacks(IRequest request,
                            ISuccess success,
                            IFailure failure,
                            IError error,
                            LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
//            if (call.isExecuted()) {
//                final String json = response.body();
//                if (!TextUtils.isEmpty(json)) {
//                    final JSONObject data = JSON.parseObject(json);
//                    if (data != null) {
//                        final boolean flag = data.getBoolean("flag");
//                        if (flag) {
//                            if (SUCCESS != null) {
//                                SUCCESS.onSuccess(response.body());
//                            }
//                        } else {
//                            final String errorMessage = data.getString("errorMessage");
//                            if (!TextUtils.isEmpty(errorMessage)) {
//                                ToastUtils.showShort(errorMessage);
//                            }
//                        }
//                    }
//                }
//            }
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

        final long delayed = DoDo.getConfiguration(ConfigKeys.LOADER_DELAYED);

        //隐藏进度条
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DoDoLoader.stopLoading();
                }
            }, delayed);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }
}
