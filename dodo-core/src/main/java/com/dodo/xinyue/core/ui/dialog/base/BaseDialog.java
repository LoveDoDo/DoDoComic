package com.dodo.xinyue.core.ui.dialog.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;
import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.dialog.callback.IClose;
import com.dodo.xinyue.core.ui.dialog.callback.IOpen;
import com.dodo.xinyue.core.ui.dialog.manager.DialogManager;
import com.dodo.xinyue.core.ui.rcLayout.RCRelativeLayout;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseDialog
 * <p>
 * Dialog本质是一个window，内部new了一个PhoneWindow，所有view都是添加到这个window里的
 * popupWindow本质是一个子窗口，通过全局唯一的WindowManager的addView方法将子窗口依附到父窗口中
 * <p>
 * popupWindow的contentView依附在设置的parentView上,如果parentView销毁,则popupWindow也会销毁(可以跨window,比如parentView为Dialog上的view)
 *
 * @author DoDo
 * @date 2018/1/12
 */
public abstract class BaseDialog extends AppCompatDialog
        implements DialogInterface.OnCancelListener,
        DialogInterface.OnDismissListener,
        DialogInterface.OnShowListener {

    private static final String TAG = "BaseDialog";
    //默认主题
    public static final int DEFAULT_THEME = R.style.dialog;
    //默认动画 duration=220
    public static final int DEFAULT_ANIM = android.R.style.Animation_Dialog;

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    /**
     * 底层容器，用于控制dialog的位置
     * <p>
     * 注：因为window窗口最底层默认是FrameLayout，无法设置Gravity居上居中显示等，所以在里面套一层RelativeLayout
     */
    private RelativeLayout mContainerView;

    /**
     * 包裹容器，用于设置圆角
     */
    private RCRelativeLayout mWrapContainerView;
    /**
     * 用户自定义的View
     */
    private View mCustomView;

    private int mContainerWidth;
    private int mContainerHeight;

    private boolean mIsForceShow = false;//是否强制显示
    private boolean mIsHide = false;//隐藏标识 适用于隐藏后再次显示的情况

    /**
     * 公共参数
     */
    private Context mContext;
    private int mTheme;//主题/样式
    private int mAnim;//动画  -1 = 无动画
    private int mGravity;//显示位置 多个用 | 分割
    private int[] mRadius;//圆角 单位dp
    private float mWidthScale;//宽度缩放比例 0-1
    private float mHeightScale;//高度缩放比例 0-1
    private boolean mCanceledOnTouchOutside;//窗口外点击取消Dialog
    private boolean mCancelable;//返回键取消Dialog
    private boolean mCoverStatusBar;//覆盖顶部状态栏
    private boolean mCoverNavigationBar;//覆盖底部导航栏
    private boolean mFullScreen;//全屏 会自动覆盖状态栏和导航栏
    private boolean mBackgroundDimEnabled;//背景变暗
    private IOpen mIOpen;//回调 打开Dialog
    private IClose mIClose;//回调 关闭Dialog


    public BaseDialog(DialogPublicParamsBean bean) {
        super(bean.getContext(), bean.getTheme());

        this.mContext = bean.getContext();
        this.mTheme = bean.getTheme();
        this.mAnim = bean.getAnim();
        this.mGravity = bean.getGravity();
        this.mRadius = bean.getRadius();
        this.mWidthScale = bean.getWidthScale();
        this.mHeightScale = bean.getHeightScale();
        this.mCanceledOnTouchOutside = bean.isCanceledOnTouchOutside();
        this.mCancelable = bean.isCancelable();
        this.mCoverStatusBar = bean.isCoverStatusBar();
        this.mCoverNavigationBar = bean.isCoverNavigationBar();
        this.mFullScreen = bean.isFullScreen();
        this.mBackgroundDimEnabled = bean.isBackgroundDimEnabled();
        this.mIOpen = bean.getIOpen();
        this.mIClose = bean.getIClose();

    }

    /**
     * 设置布局
     */
    public abstract Object setLayout();

    /**
     * 添加事件
     */
    public abstract void onBindView(View rootView);

    /**
     * 调用show()执行该方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initEvent();
        initWrapContainerView();
        initContainerView();
        initCustomView();

        //绑定视图
        mUnbinder = ButterKnife.bind(this, mCustomView);


        //将自定义View添加到包裹容器里
        mWrapContainerView.addView(mCustomView);
        //将包裹容器添加到底层容器里
        mContainerView.addView(mWrapContainerView);

        //将布局设置给Dialog
        setContentView(mContainerView, new ViewGroup.LayoutParams(mContainerWidth, mContainerHeight));

        adjustLayout();

        //设置窗口属性，必须放在setContentView()之后
        initWindowAttrs();

        //添加事件
        onBindView(mCustomView);

    }

    /**
     * 调整布局
     */
    private void adjustLayout() {
        if (mFullScreen) {
            mCoverStatusBar = true;
            mCoverNavigationBar = true;
        }

        if (!mCoverStatusBar) {
            //工作区高度 = 屏幕高度 - 状态栏高度
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mWrapContainerView.getLayoutParams();
            layoutParams.topMargin = DimenUtil.getStatusBarHeight();
            mWrapContainerView.setLayoutParams(layoutParams);
        }

        if (!mCoverNavigationBar) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mWrapContainerView.getLayoutParams();
            layoutParams.bottomMargin = DimenUtil.getNavigationBarHeight();
            mWrapContainerView.setLayoutParams(layoutParams);
        }

        int deviceWidth = mContainerWidth;
        int deviceHeight = mContainerHeight;
        //工作区宽度
        float validWidth = deviceWidth * (1 - mWidthScale);
        //工作区高度
        float validHeight = deviceHeight * (1 - mHeightScale);
        //水平方向的margin = leftMargin + rightMargin
        int marginHorizontal = (int) (validWidth / 2);
        //垂直方向的margin = topMargin + bottomMargin
        int marginVertical = (int) (validHeight / 2);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mWrapContainerView.getLayoutParams();
        lp.setMargins(
                marginHorizontal + lp.leftMargin,
                marginVertical + lp.topMargin,
                marginHorizontal + lp.rightMargin,
                marginVertical + lp.bottomMargin
        );
        mWrapContainerView.setLayoutParams(lp);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        setOnCancelListener(this);
        setOnDismissListener(this);
        setOnShowListener(this);
        //窗口外点击取消Dialog
        setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        //设置返回键取消Dialog
        setCancelable(mCancelable);

        setDialogAnim();
    }

    /**
     * 设置dialog动画
     */
    private void setDialogAnim() {
        final Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(mAnim);
        }
    }

    /**
     * 初始化包裹布局
     */
    private void initWrapContainerView() {
        //圆角布局
        mWrapContainerView = new RCRelativeLayout(mContext);
        //居中
        mWrapContainerView.setGravity(Gravity.CENTER);
        //背景透明
        mWrapContainerView.setBackgroundColor(Color.TRANSPARENT);
        //圆角
        mWrapContainerView.setTopLeftRadius(DimenUtil.dp2px(mRadius[0]));
        mWrapContainerView.setTopRightRadius(DimenUtil.dp2px(mRadius[1]));
        mWrapContainerView.setBottomLeftRadius(DimenUtil.dp2px(mRadius[2]));
        mWrapContainerView.setBottomRightRadius(DimenUtil.dp2px(mRadius[3]));
        //屏蔽点击音效
        mWrapContainerView.setSoundEffectsEnabled(false);
    }

    /**
     * 初始化底层容器
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initContainerView() {
        //限制布局
        mContainerView = new RelativeLayout(mContext);
        //居中
        mContainerView.setGravity(mGravity);//设置内部的mWrapContainerView的位置
        //背景透明
        mContainerView.setBackgroundColor(Color.TRANSPARENT);
        //屏蔽点击音效
        mContainerView.setSoundEffectsEnabled(false);
        //点击容器取消dialog
        if (mCanceledOnTouchOutside) {
            mContainerView.setOnTouchListener((v, event) -> {
                cancel();
                return true;
            });
        }

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight() + BarUtils.getNavBarHeight();//加上底部导航栏高度

        mContainerWidth = deviceWidth;
        mContainerHeight = deviceHeight;

    }

    /**
     * 初始化用户自定义的View
     */
    private void initCustomView() {
        final Object tempView = setLayout();
        if (tempView instanceof Integer) {
            //这里第二个参数之所以不直接传null，是为了让用户自定义的View的根布局的宽高生效
            mCustomView = LayoutInflater.from(mContext).inflate((Integer) tempView, mWrapContainerView, false);
        } else if (tempView instanceof View) {
            mCustomView = (View) tempView;
        } else {
            throw new ClassCastException("setLayout()返回值类型必须是int或View!");
        }

        //防止点击穿透,导致dialog消失 尤其是ImageDialog的透明区域
        mCustomView.setClickable(true);
    }

    /**
     * 覆盖状态栏
     * <p>
     * android4.4以上
     */
    @Deprecated
    private void coverStatusBar() {
        mContainerView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * 设置窗口大小，背景变暗
     */
    private void initWindowAttrs() {
        final Window dialogWindow = getWindow();
        if (dialogWindow == null) {
            return;
        }
        //透明导航栏
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = mContainerWidth;
        lp.height = mContainerHeight;
        if (!mBackgroundDimEnabled) {
            lp.dimAmount = 0f;//取值0~1 0=不变暗 1=完全变黑
//            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//用于适配华为等机型
        }
        dialogWindow.setAttributes(lp);
    }

    /**
     * 获取底层容器
     */
    public RelativeLayout getContainerView() {
        return mContainerView;
    }

    /**
     * 获取包裹容器
     */
    public RCRelativeLayout getWrapContainerView() {
        return mWrapContainerView;
    }

    /**
     * 获取自定义View
     */
    public View getCustomView() {
        return mCustomView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //该方法在onDetachedFromWindow()后执行
        if (mUnbinder != null) {
            try {
                mUnbinder.unbind();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (mIOpen != null) {
            mIOpen.onOpen();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        //调用cancel取消dialog会走onCancel和onDismiss
        //调用dismiss取消dialog会走onDismiss
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mIClose != null) {
            mIClose.onClose();
        }
    }

    @Override
    public void show() {
        if (mIsForceShow) {
            super.show();
            return;
        }

        if (mIsHide) {
            mIsHide = false;
            super.show();
            return;
        }

        DialogManager.getInstance().cancelLastDialog();
        DialogManager.getInstance().bindDialog(this);
        super.show();
    }

    @Override
    public void cancel() {
        if (mIsForceShow) {
            super.cancel();
            return;
        }
        DialogManager.getInstance().unbindDialog();
        super.cancel();
        DialogManager.getInstance().checkPending();
    }

    /**
     * 延时显示
     */
    public void pendingShow() {
        DialogManager.getInstance().pendingShow(this);
    }

    /**
     * 强制显示
     * <p>
     * 不隐藏其他Dialog，只保证自己能显示出来
     */
    public void forceShow() {
        mIsForceShow = true;
        show();
    }

    /**
     * 隐藏后isShowing()仍然为true,再次调用show()只会将mDecor设置为可视状态
     */
    @Override
    public void hide() {
        super.hide();//内部实现：mDecor.setVisibility(View.GONE);
        mIsHide = true;
    }

    /**
     * 直接隐藏 无动画
     */
    public void hideNoAnim() {
        final Window dialogWindow = getWindow();
        if (dialogWindow == null) {
            return;
        }
        dialogWindow.getDecorView().setVisibility(View.INVISIBLE);
        mIsHide = true;
    }
}
