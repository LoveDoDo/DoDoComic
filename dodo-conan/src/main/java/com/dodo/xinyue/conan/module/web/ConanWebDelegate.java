package com.dodo.xinyue.conan.module.web;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.module.setting.about.anim.RotationAnimator;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.web.IPageLoadListener;
import com.dodo.xinyue.core.delegates.web.WebDelegateImpl;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ConanWebDelegate
 *
 * @author DoDo
 * @date 2019/1/30
 */
public class ConanWebDelegate extends DoDoDelegate {

    private static final String ARGS_URL = "args_url";
    private static final String ARGS_TITLE = "args_title";

    protected static final RequestOptions IMAGE_OPTIONS =
            new RequestOptions()
//                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

    private WebDelegateImpl mWebDelegate = null;
    private boolean isReload = false;//是否是刷新操作
    private ObjectAnimator mRotationAnim = null;

    private String mUrl;
    private String mTitle;

    @BindView(R2.id.tvRefresh)
    TextView mTvRefresh = null;
    @BindView(R2.id.rlLoading)
    RelativeLayout mRlLoading = null;
    @BindView(R2.id.rlLoadError)
    LinearLayout mRlLoadError = null;
    @BindView(R2.id.tvProgress)
    IconTextView mTvProgress = null;
    @BindView(R2.id.content)
    ContentFrameLayout mFlContent = null;
    @BindView(R2.id.ivGif)
    ImageView mIvGif = null;

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;

    @OnClick(R2.id.tvBack)
    void onTvBackClicked() {
        pop();
    }

    @OnClick(R2.id.rlLoadError)
    void onLoadErrorClicked() {
        refresh();
    }

    @OnClick(R2.id.tvRefresh)
    void onTvRefreshClicked() {
        if (mWebDelegate.getWebView() == null) {
            return;
        }
        refresh();
        //旋转动画
        YoYo.with(new RotationAnimator())
                .duration(666)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(mTvRefresh);
    }

    public static ConanWebDelegate create(String url, String title) {
        final Bundle args = new Bundle();
        args.putString(ARGS_URL, url);
        args.putString(ARGS_TITLE, title);
        final ConanWebDelegate delegate = new ConanWebDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args == null) {
            return;
        }
        mUrl = args.getString(ARGS_URL);
        mTitle = args.getString(ARGS_TITLE);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_web_conan;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        mTvTitle.setText(mTitle);

        mRotationAnim = ObjectAnimator.ofFloat(mTvProgress, "rotation", 0, 360);
        mRotationAnim.setDuration(1314);
        mRotationAnim.setRepeatCount(Animation.INFINITE);//无限循环
        mRotationAnim.setRepeatMode(ValueAnimator.RESTART);//重复动画
        mRotationAnim.setInterpolator(new LinearInterpolator());//覆盖默认的AccelerateDecelerateInterpolator()

        Glide.with(getContext())
                .load(R.drawable.icon_webview_load_error)
                .apply(IMAGE_OPTIONS)
                .into(mIvGif);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        if (mRotationAnim.isRunning()) {
            mRlLoading.setVisibility(View.VISIBLE);
        }

        mWebDelegate = WebDelegateImpl.create(mUrl);
        mWebDelegate.setTopDelegate(this);
        mWebDelegate.setPageLoadListener(new IPageLoadListener() {
            @Override
            public void onLoadStart() {
                mRlLoadError.setVisibility(View.GONE);
                if (!mRotationAnim.isStarted()) {
                    mRotationAnim.start();
                }
                if (mRotationAnim.isRunning()) {
                    mRlLoading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadEnd(boolean isLoadError) {
                mRlLoading.setVisibility(View.GONE);
                if (isReload) {
                    isReload = false;
                    clearHistory();
                }
                if (isLoadError) {
                    //加载失败
                    mRlLoadError.setVisibility(View.VISIBLE);
                    mFlContent.setVisibility(View.GONE);
                } else {
                    mFlContent.setVisibility(View.VISIBLE);
                }
            }
        });
        getSupportDelegate().loadRootFragment(R.id.content, mWebDelegate);
    }

    private void clearHistory() {
        if (mWebDelegate.getWebView() != null) {
            mWebDelegate.getWebView().clearHistory();
        }
    }

    private void refresh() {
        isReload = true;
        mWebDelegate.getWebView().loadUrl(mUrl);
        clearHistory();
    }

    @Override
    public void onDestroy() {
        if (mRotationAnim != null) {
            mRotationAnim.cancel();
            mRotationAnim = null;
        }
        super.onDestroy();
    }

}
