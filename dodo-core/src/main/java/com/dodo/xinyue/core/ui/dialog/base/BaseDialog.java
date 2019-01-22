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

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.dialog.callback.ICloseDialog;
import com.dodo.xinyue.core.ui.dialog.callback.IOpenDialog;
import com.dodo.xinyue.core.ui.dialog.manager.DialogManager;
import com.dodo.xinyue.core.ui.rcLayout.RCRelativeLayout;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by YuanJun on 2018/1/12 16:05
 */

/**
 * BaseDialog
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

    /**
     * 公共参数
     */
    private Context mContext;
    private int mDialogTheme;//主题/样式
    private int mDialogAnim;//动画  -1 = 无动画
    private int mDialogGravity;//显示位置 多个用 | 分割
    private int[] mDialogRadius;//圆角 单位dp
    private float mWidthScale;//宽度缩放比例 0-1
    private float mHeightScale;//高度缩放比例 0-1
    private boolean mCanceledOnTouchOutside;//窗口外点击取消Dialog
    private boolean mCancelable;//返回键取消Dialog
    private boolean mCoverStatusBar;//覆盖状态栏(全屏)
    private boolean mBackgroundDimEnabled;//背景变暗
    private IOpenDialog mIOpenDialog;//回调 打开Dialog
    private ICloseDialog mICloseDialog;//回调 关闭Dialog


    public BaseDialog(DialogPublicParamsBean bean) {
        super(bean.getContext(), bean.getDialogTheme());

        this.mContext = bean.getContext();
        this.mDialogTheme = bean.getDialogTheme();
        this.mDialogAnim = bean.getDialogAnim();
        this.mDialogGravity = bean.getDialogGravity();
        this.mDialogRadius = bean.getDialogRadius();
        this.mWidthScale = bean.getWidthScale();
        this.mHeightScale = bean.getHeightScale();
        this.mCanceledOnTouchOutside = bean.isCanceledOnTouchOutside();
        this.mCancelable = bean.isCancelable();
        this.mCoverStatusBar = bean.isCoverStatusBar();
        this.mBackgroundDimEnabled = bean.isBackgroundDimEnabled();
        this.mIOpenDialog = bean.getIOpenDialog();
        this.mICloseDialog = bean.getICloseDialog();

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

        if (!mCoverStatusBar) {
            //工作区高度 = 屏幕高度 - 状态栏高度
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mWrapContainerView.getLayoutParams();
            layoutParams.topMargin = DimenUtil.getStatusBarHeight();
            mWrapContainerView.setLayoutParams(layoutParams);
        }

        //适配全面屏 必须放在setContentView()之后
        initWindowAttrs();

        //添加事件
        onBindView(mCustomView);

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
            window.setWindowAnimations(mDialogAnim);
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
        mWrapContainerView.setTopLeftRadius(DimenUtil.dp2px(mDialogRadius[0]));
        mWrapContainerView.setTopRightRadius(DimenUtil.dp2px(mDialogRadius[1]));
        mWrapContainerView.setBottomLeftRadius(DimenUtil.dp2px(mDialogRadius[2]));
        mWrapContainerView.setBottomRightRadius(DimenUtil.dp2px(mDialogRadius[3]));
        //屏蔽点击音效
        mWrapContainerView.setSoundEffectsEnabled(false);
    }

    /**
     * 初始化底层容器
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initContainerView() {
        //圆角布局
        mContainerView = new RelativeLayout(mContext);
        //居中
        mContainerView.setGravity(mDialogGravity);
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
        int deviceHeight = DimenUtil.getScreenHeight();

        mContainerWidth = (int) (deviceWidth * mWidthScale);
        mContainerHeight = (int) (deviceHeight * mHeightScale);

    }

    /**
     * 初始化用户自定义的View
     */
    private void initCustomView() {
        final Object tempView = setLayout();
        if (tempView instanceof Integer) {
            //这里第二个参数之所以不直接传null，是为了让用户自定义的View的根布局的宽高生效
            mCustomView = LayoutInflater.from(mContext).inflate((Integer) tempView, mContainerView, false);
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
    private void _coverStatusBar() {
        mContainerView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * 适配全面屏
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
        lp.gravity = Gravity.TOP;//不设置的话,会在屏幕下方空出一些间距
        if (!mBackgroundDimEnabled) {
            lp.dimAmount = 0f;
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

    /**
     * 设置window窗口容器属性，必须放在setContentView()之后
     * <p>
     * 暂时没用
     */
    private void _initWindowAttrs() {
        //设置dialog布局参数
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //窗口必须和内容布局大小一致，以便于点击非窗口区域可以dismiss
            //需要一个相对布局包裹住内容布局，
            //窗口最大为整个屏幕 减去 状态栏 减去 上下左右大约10个dp的间距
            //width或height设置为WRAP_CONTENT，则宽高
//            lp.width = (int) (deviceWidth * 0.85f);//
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            lp.height = deviceHeight;//内容区（除去状态栏的部分）
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;//全屏
            //偏移
//            lp.height += deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
//            lp.gravity = Gravity.LEFT | Gravity.TOP;
//            lp.x = 100;
//            lp.y = lp.y-100;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //该方法在onDetachedFromWindow()后执行
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (mIOpenDialog != null) {
            mIOpenDialog.onOpen();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        //调用cancel取消dialog会走onCancel和onDismiss
        //调用dismiss取消dialog会走onDismiss
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mICloseDialog != null) {
            mICloseDialog.onClose();
        }
    }

    @Override
    public void show() {
//        if (!DialogManager.getInstance().canShow(this)) {
//            return;
//        }
        if (mIsForceShow) {
            super.show();
            return;
        }
        DialogManager.getInstance().cancelLastDialog();
        DialogManager.getInstance().bindDialog(this);
        super.show();
    }

    @Override
    public void cancel() {
//        if (!DialogManager.getInstance().canCancel()) {
//            return;
//        }
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
     *
     * 不隐藏其他Dialog，只保证自己能显示出来
     */
    public void forceShow() {
        mIsForceShow = true;
        show();
    }

}
