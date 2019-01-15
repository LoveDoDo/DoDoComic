package com.dodo.xinyue.conan.module.setting.about;

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

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.module.setting.about.anim.RotationAnimator;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.web.IPageLoadListener;
import com.dodo.xinyue.core.delegates.web.WebDelegateImpl;
import com.dodo.xinyue.core.util.CommonUtil;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * FeedbackDelegate
 * 意见反馈
 *
 * @author DoDo
 * @date 2019/1/13
 */
public class FeedbackDelegate extends DoDoDelegate {

    protected static final RequestOptions IMAGE_OPTIONS =
            new RequestOptions()
//                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

    private WebDelegateImpl mWebDelegate = null;
    private boolean isReload = false;//是否是刷新操作
    private ObjectAnimator mRotationAnim = null;

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

    @OnClick(R2.id.tvCopy)
    void onTvCopyClicked() {
        if (CommonUtil.copyToClipboard(ApiConstants.FEEDBACK_URL)) {
            ToastUtils.showShort("复制链接成功，请在微信中打开");
        } else {
            ToastUtils.showShort("复制链接失败");
        }
    }

    public static FeedbackDelegate create() {
        return new FeedbackDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_feedback;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

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

        String postData = getPostData();
        mWebDelegate = WebDelegateImpl.create(ApiConstants.FEEDBACK_URL, postData);//吐个槽
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

    private String getPostData() {
        String openid = CommonUtil.getUniqueId(); // 用户的openid 自定义 唯一性 一定复杂度 管理员登陆使用QQ号作为openid
        String nickname = "柯南迷"; // 用户昵称 不超过8个字
        String headimgurl = "https://support.qq.com/data/52238/2019/0111/6410b8ddfcf63d372e4da13b19d50eda.png";  // 用户的头像url，支持png,jpg，必须是https开头的
        String postData = "nickname=" + nickname + "&avatar=" + headimgurl + "&openid=" + openid;
        return postData;
    }

    private void clearHistory() {
        if (mWebDelegate.getWebView() != null) {
            mWebDelegate.getWebView().clearHistory();
        }
    }

    private void refresh() {
        isReload = true;
        mWebDelegate.getWebView().postUrl(ApiConstants.FEEDBACK_URL, getPostData().getBytes());
        clearHistory();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.global_enter);
        fragmentAnimator.setExit(R.anim.global_exit);
        return fragmentAnimator;
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
