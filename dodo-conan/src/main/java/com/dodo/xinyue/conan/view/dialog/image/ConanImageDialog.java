package com.dodo.xinyue.conan.view.dialog.image;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.image.GlideApp;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ConanImageDialog
 *
 * @author DoDo
 * @date 2019/1/23
 */
public class ConanImageDialog extends BaseDialog {

    private final String mImage;

    private ObjectAnimator mRotationAnim = null;
    private boolean mCoverLoadDone = false;//图片是否加载完成

    @BindView(R2.id.iv)
    AppCompatImageView mIv;
    @BindView(R2.id.tvProgress)
    IconTextView mTvProgress = null;

    @OnClick(R2.id.iv)
    void onIvClicked() {
        cancel();
    }

    public ConanImageDialog(DialogPublicParamsBean bean,
                            String image) {
        super(bean);
        this.mImage = image;
    }

    public static ConanImageDialogBuilder builder() {
        return new ConanImageDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_image;
    }

    @Override
    public void onBindView(View rootView) {

        mTvProgress.setVisibility(View.INVISIBLE);
        mRotationAnim = ObjectAnimator.ofFloat(mTvProgress, "rotation", 0, 360);
        mRotationAnim.setDuration(1314);
        mRotationAnim.setStartDelay(220);
        mRotationAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!mCoverLoadDone && mTvProgress != null) {
                    mTvProgress.setVisibility(View.VISIBLE);
                }
            }
        });
        mRotationAnim.setRepeatCount(Animation.INFINITE);//无限循环
        mRotationAnim.setRepeatMode(ValueAnimator.RESTART);//重复动画
        mRotationAnim.setInterpolator(new LinearInterpolator());//覆盖默认的AccelerateDecelerateInterpolator()
        mRotationAnim.start();

        GlideApp.with(getContext())
                .load(mImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .skipMemoryCache(true)
                .timeout(10 * 1000)//默认2.5s
                .transition(new DrawableTransitionOptions().crossFade(188))//渐显 只有第一次加载有动画 内存加载无动画
                .into(new DrawableImageViewTarget(mIv) {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        mCoverLoadDone = true;
                        //存在dismiss dialog之后，加载还在继续的情况
                        if (mTvProgress != null) {
                            mTvProgress.setVisibility(View.GONE);
                        }

                    }
                });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mRotationAnim != null) {
            mRotationAnim.cancel();
            mRotationAnim = null;
        }
        super.onDismiss(dialog);
    }

}
