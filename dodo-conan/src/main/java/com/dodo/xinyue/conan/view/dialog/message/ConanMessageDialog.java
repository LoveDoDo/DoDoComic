package com.dodo.xinyue.conan.view.dialog.message;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.view.dialog.normal.ConanNormalDialog;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.image.GlideApp;
import com.dodo.xinyue.core.ui.image.ProgressInterceptor;
import com.dodo.xinyue.core.ui.image.ProgressListener;
import com.dodo.xinyue.core.util.CommonUtil;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ConanMessageDialog
 *
 * @author DoDo
 * @date 2018/11/2
 */
public class ConanMessageDialog extends BaseDialog {

    private static final String TAG = "ConanMessageDialog";

    private final JiGuangMessage mMessage;

    private String mTitle;
    private String mContent;
    private int mType;
    private boolean mIsStart;//是否文本左对齐
    private boolean mIsHtml;//是否是html形式(改变文本颜色)
    private int mAction;//点击操作
    private String mCopyContent;//复制内容
    private String mCopyTips;//复制成功提示
    private String mCover;//封面图
    private boolean mIsEndOfLastLine;//最后一行是否置于尾部 适用于古诗词等类型的落款
    private String mAnswer;//推理题的答案
    private boolean mIsRetract;//是否缩进 每行都缩进
    private String mButtonText;//按钮文字 默认为：确定/复制/查看答案

    private ObjectAnimator mRotationAnim = null;
    private boolean mCoverLoadDone = false;//图片是否加载完成

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.tvContent)
    TextView mTvContent = null;
    @BindView(R2.id.tvConfirm)
    TextView mTvConfirm = null;

    @BindView(R2.id.sv)
    ScrollView mScrollView = null;//方便动态设置ScrollView的高度 TODO 子布局不能设置android:layout_gravity="center"，否则底部会出现空白，顶部显示不全

    @BindView(R2.id.ivCover)
    ImageView mIvCover = null;
    @BindView(R2.id.tvLastLine)
    TextView mTvLastLine = null;

    @BindView(R2.id.tvProgress)
    IconTextView mTvProgress = null;

    @OnClick(R2.id.tvConfirm)
    void onTvConfirmClicked() {
        if (mType == JiGuangMessage.TYPE_INFERENCE) {
            if (!TextUtils.isEmpty(mAnswer)) {
                //显示推理答案
                ConanNormalDialog.builder()
                        .title("参考答案")
                        .content(mAnswer)
                        .onlyOneButton(true)
                        .radius(8)
                        .widthScale(0.85f)
                        .build()
                        .forceShow();
                return;
            }
        }

        switch (mAction) {
            case JiGuangMessage.ACTION_COPY:
                if (CommonUtil.copyToClipboard(mCopyContent)) {
                    ToastUtils.showShort(mCopyTips);
                } else {
                    ToastUtils.showShort("复制失败");
                }
                break;
            default:
                break;
        }
        cancel();
    }

    public ConanMessageDialog(DialogPublicParamsBean bean,
                              JiGuangMessage message) {
        super(bean);
        this.mMessage = message;
        initAttr();
    }

    private void initAttr() {
        final JSONObject extraData = JSON.parseObject(mMessage.getExtraData());//默认值必须都是0/false
        mTitle = mMessage.getTitle();
        mContent = mMessage.getContent();
        mType = mMessage.getType();
        mIsStart = extraData.getBooleanValue("start");
        mIsHtml = extraData.getBooleanValue("html");
        mAction = extraData.getIntValue("action");
        mCopyContent = extraData.getString("copy_content");
        mCopyTips = extraData.getString("copy_tips");
        mCover = extraData.getString("cover");
        mIsEndOfLastLine = extraData.getBooleanValue("end");
        mAnswer = extraData.getString("answer");
        mIsRetract = extraData.getBooleanValue("retract");
        mButtonText = extraData.getString("button_text");

    }

    public static ConanMessageDialogBuilder builder() {
        return new ConanMessageDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_message;
    }

    @Override
    public void onBindView(View rootView) {
        mTvContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTvContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (mTvContent.getHeight() > DimenUtil.dp2px(188)) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mScrollView.getLayoutParams();
                    layoutParams.height = DimenUtil.dp2px(188);
                    mScrollView.setLayoutParams(layoutParams);
                }
            }
        });

        if (mIsRetract) {
            if (!TextUtils.isEmpty(mContent)) {
                String[] tempArray = mContent.split("\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tempArray.length; i++) {
                    sb.append("\t\t\t\t");//一个汉字=\t\t
                    sb.append(tempArray[i]);
                    if (i != tempArray.length - 1) {
                        //除了最后一行
                        sb.append("\n");
                    }
                }
                mContent = sb.toString();
            }
        }

        if (mType == JiGuangMessage.TYPE_INFERENCE) {
            if (!TextUtils.isEmpty(mAnswer)) {
                mTvConfirm.setText("查看答案");
                mAnswer = mAnswer.replace("\\n", "\n");//使换行符可复制
            }
        }

        if (mIsStart) {
            mTvContent.setGravity(Gravity.START);
        }
        if (mAction == JiGuangMessage.ACTION_COPY) {
            if (!TextUtils.isEmpty(mCopyContent)) {
                mCopyContent = mCopyContent.replace("\\n", "\n");//使换行符可复制
            } else {
                mCopyContent = mContent;
            }

            if (TextUtils.isEmpty(mCopyTips)) {
                mCopyTips = "复制成功";
            }
        }
        if (!TextUtils.isEmpty(mCover)) {
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
        }
        if (mIsEndOfLastLine) {
            if (!TextUtils.isEmpty(mContent)) {
                mTvLastLine.setVisibility(View.VISIBLE);
                String[] tempArray = mContent.split("\n");
                if (tempArray.length == 1) {
                    //单行
                    mTvContent.setVisibility(View.GONE);
                    mTvLastLine.setText(mContent);
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < tempArray.length; i++) {
                        if (i == tempArray.length - 1) {
                            //最后一行
                            mTvLastLine.setText(tempArray[i]);
                        } else {
                            sb.append(tempArray[i]);
                            if (i != tempArray.length - 2) {
                                //倒数第二行也不能加换行符，因为倒数第二行是正文的最后一行
                                sb.append("\n");
                            }

                        }
                    }
                    mContent = sb.toString();
                }

            }
        }
        if (TextUtils.isEmpty(mContent)) {
            mScrollView.setVisibility(View.GONE);
        }


        mTvTitle.setText(mTitle);

        if (mTvContent.getVisibility() == View.VISIBLE) {
            if (!mIsHtml) {
                mTvContent.setText(mContent);
            } else {
                //解决不能换行的问题
                mTvContent.setText(Html.fromHtml(mContent.replace("\n", "<br>")));//例如："这是<font color=#ff0000>红色</font>,这是<font color=#0000ff>蓝色</font>"
            }
        } else if (mTvLastLine.getVisibility() == View.VISIBLE) {
            //单行
            if (!mIsHtml) {
                mTvLastLine.setText(mContent);
            } else {
                //解决不能换行的问题
                mTvLastLine.setText(Html.fromHtml(mContent.replace("\n", "<br>")));//例如："这是<font color=#ff0000>红色</font>,这是<font color=#0000ff>蓝色</font>"
            }
        }

        if (mAction == JiGuangMessage.ACTION_COPY) {
            mTvConfirm.setText("复制");
        }

        if (!TextUtils.isEmpty(mButtonText)) {
            mTvConfirm.setText(mButtonText);
        }

        if (!TextUtils.isEmpty(mCover)) {
            ProgressInterceptor.addListener(mCover, new ProgressListener() {
                @Override
                public void onProgress(int progress) {
                    Log.d(TAG, "图片已加载进度：" + progress);
                }
            });
            GlideApp.with(getContext())
                    .load(mCover)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .skipMemoryCache(true)
                    .timeout(10 * 1000)//默认2.5
                    .transition(new DrawableTransitionOptions().crossFade(188))//渐显 只有第一次加载有动画 内存加载无动画
                    .into(new DrawableImageViewTarget(mIvCover) {
                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            Log.d(TAG, "图片开始加载");
                            mIvCover.setVisibility(View.VISIBLE);//TODO imageView不可见的话，Glide不回调加载成功的监听
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            Log.d(TAG, "图片加载失败");
                            ProgressInterceptor.removeListener(mCover);
                        }

                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            super.onResourceReady(resource, transition);
                            Log.d(TAG, "图片加载完成");
                            ProgressInterceptor.removeListener(mCover);
                            mCoverLoadDone = true;
                            //存在dismiss dialog之后，加载还在继续的情况
                            if (mTvProgress != null) {
                                mTvProgress.setVisibility(View.GONE);
                            }

                        }
                    });
        }
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
