package com.dodo.xinyue.core.ui.popup.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;
import com.dodo.xinyue.core.ui.popup.bean.PopupWindowPublicParamsBean;
import com.dodo.xinyue.core.ui.popup.callback.IClose;
import com.dodo.xinyue.core.ui.popup.callback.IOpen;
import com.dodo.xinyue.core.ui.rcLayout.RCRelativeLayout;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BasePopupWindow
 *
 * @author DoDo
 * @date 2019/1/24
 */
public abstract class BasePopupWindow extends PopupWindow
        implements PopupWindow.OnDismissListener {

    private static final String TAG = "BasePopupWindow";
    //默认动画 duration=220
    public static final int DEFAULT_ANIM = android.R.style.Animation_Dialog;
    ;//使用popupWindow默认动画

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    private InterceptRelativeLayout mContainerView;
    private RCRelativeLayout mWrapContainerView;
    private View mCustomView;

    private int mContainerWidth;
    private int mContainerHeight;

    /**
     * 公共参数
     */
    private Context mContext;
    private int mAnim;//动画  -1 = 无动画
    private int mGravity;//显示位置 多个用 | 分割
    private int[] mRadius;//圆角 单位dp
    private float mWidthScale;//宽度缩放比例 0-1
    private float mHeightScale;//高度缩放比例 0-1
    private boolean mCanceledOnTouchOutside;//窗口外点击取消PopupWindow
    private boolean mCancelable;//返回键取消PopupWindow
    private boolean mCoverStatusBar;//覆盖顶部状态栏
    private boolean mCoverNavigationBar;//覆盖底部导航栏
    private boolean mFullScreen;//全屏 会自动覆盖状态栏和导航栏
    private boolean mBackgroundDimEnabled;//背景变暗
    private float mDimCount;//背景变暗的比例 0=透明 1=黑色
    private long mDimTime;//背景变暗的时间 默认=默认动画的时间
    private IOpen mIOpen;//回调 打开PopupWindow
    private IClose mIClose;//回调 关闭PopupWindow

    public BasePopupWindow(PopupWindowPublicParamsBean bean) {
        super();

        this.mContext = bean.getContext();
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
        this.mDimCount = bean.getDimCount();
        this.mDimTime = bean.getDimTime();
        this.mIOpen = bean.getIOpen();
        this.mIClose = bean.getIClose();

    }

    private void init() {
        initContainerView();
        initWrapContainerView();
        initCustomView();
        initEvent();

        //绑定视图
        mUnbinder = ButterKnife.bind(this, mCustomView);


        //将自定义View添加到包裹容器里
        mWrapContainerView.addView(mCustomView);
        //将包裹容器添加到底层容器里
        mContainerView.addView(mWrapContainerView);

        //将布局设置给popupWindow
        setContentView(mContainerView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);//sdk > 21 解决 标题栏没有办法遮罩的问题

        adjustLayout();

        //添加事件
        onBindView(mCustomView);
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
     * 初始化事件
     */
    private void initEvent() {

        if (mFullScreen) {
            mCoverStatusBar = true;
            mCoverNavigationBar = true;
        }

        setFocusable(true);//必须 拦截返回键
        mContainerView.setFocusable(true);//必须 拦截返回键
        mContainerView.setFocusableInTouchMode(true);//必须 拦截返回键

//        setOutsideTouchable(true);

        //设置popupWindow动画
        setAnimationStyle(mAnim);

        setOnDismissListener(this);

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
        mContainerView = new InterceptRelativeLayout(mContext);
        //居中
        mContainerView.setGravity(mGravity);//设置内部的mWrapContainerView的位置
        //背景透明
        mContainerView.setBackgroundColor(Color.TRANSPARENT);
        //屏蔽点击音效
        mContainerView.setSoundEffectsEnabled(false);
        //点击容器取消
        if (mCanceledOnTouchOutside) {
            mContainerView.setOnTouchListener((v, event) -> {
                dismiss();
                return true;
            });
        }

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

        //防止点击穿透,导致popupWindow消失 尤其是Image的透明区域
        mCustomView.setClickable(true);
    }

    /**
     * 调整布局
     */
    private void adjustLayout() {
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

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight() + BarUtils.getNavBarHeight();//加上底部导航栏高度
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

    @Override
    public void onDismiss() {
        if (mIClose != null) {
            mIClose.onClose();
        }
        if (mUnbinder != null) {
            try {
                mUnbinder.unbind();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void target() {

    }

    public void show(View targetView) {

        if (targetView == null) {
            targetView = mContainerView;
        }

        init();

//        if (mBackgroundDimEnabled) {
//            final ValueAnimator animator = ValueAnimator.ofFloat(0f, mDimCount);
//            animator.setDuration(mDimTime);
//            animator.setInterpolator(new LinearInterpolator());
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float value = (float) animation.getAnimatedValue();
//                    mContainerView.setBackgroundColor(Color.argb((int) (value * 255), 0, 0, 0));
////                    DoDoLogger.d(value + "");
//                }
//            });
//            animator.start();
//        }

//        PopupWindowCompat.
        showAtLocation(targetView, Gravity.CENTER, 0, 0);//Gravity.CENTER会有问题

        if (mIOpen != null) {
            mIOpen.onOpen();
        }
    }

    public void cancel() {
//        if (mBackgroundDimEnabled) {
//            final ValueAnimator animator = ValueAnimator.ofFloat(mDimCount, 0f);
//            animator.setDuration(mDimTime);
//            animator.setInterpolator(new AccelerateDecelerateInterpolator());
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float value = (float) animation.getAnimatedValue();
//                    mContainerView.setBackgroundColor(Color.argb((int) (value * 255), 0, 0, 0));
//                }
//            });
//            animator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    dismiss();
//                }
//            });
//            animator.start();
//        }
        dismiss();
    }

    /**
     * 专门负责拦截返回键
     */
    private class InterceptRelativeLayout extends RelativeLayout {

        public InterceptRelativeLayout(Context context) {
            super(context);
        }

        /**
         * 重载该函数可以拦截返回键
         * <p>
         * 这个函数主要是针对让输入法优先获取按键事件,在dispatchKeyEvent之前执行
         * TODO 试过N种可能,只有这一种方法可以使popupWindow内部的View拦截到返回键
         */
        @Override
        public boolean dispatchKeyEventPreIme(KeyEvent event) {
            //照抄PopupWindow类里的内部类PopupDecorView是实现方法.准确监听点击一次返回键触发回调
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (getKeyDispatcherState() == null) {
                    return super.dispatchKeyEventPreIme(event);
                }

                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null) {
                        state.startTracking(event, this);
                    }
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null && state.isTracking(event) && !event.isCanceled()) {
                        if (mCancelable) {
                            dismiss();
                        }
                        return true;
                    }
                }
                return super.dispatchKeyEventPreIme(event);
            } else {
                return super.dispatchKeyEventPreIme(event);
            }
        }


        /**
         * 重载该函数无法拦截返回键
         */
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            return super.dispatchKeyEvent(event);
        }
    }

    /**
     *         // 用于PopupWindow的View
     View contentView = LayoutInflater.from(getContext()).inflate(R.layout.delegate_image, null, false);
     // 创建PopupWindow对象，其中：
     // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
     // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
     PopupWindow window = new PopupWindow(contentView, 400,400,false);
     // 设置PopupWindow的背景
     window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     // 设置PopupWindow是否能响应外部点击事件
     window.setOutsideTouchable(false);
     // 设置PopupWindow是否能响应点击事件
     window.setTouchable(true);
     //        window.showAtLocation(mIvCover, Gravity.CENTER, 0, 0);
     //        window.showAsDropDown(mIvCover);
     // 显示PopupWindow，其中：
     // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
     //        window.showAsDropDown(anchor, xoff, yoff);
     // 或者也可以调用此方法显示PopupWindow，其中：
     // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
     // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
     window.showAtLocation(mIvCover, Gravity.BOTTOM|Gravity.LEFT, 0, 0);

     //TODO 两种显示方法
     //window.showAsDropDown(mIvCover, , , );//显示在相对于某View的任意位置。目标View为某View     target 作用在   坐标原点：View左下角
     //window.showAtLocation(mIvCover, Gravity.BOTTOM|Gravity.LEFT, 0, 0);//显示在某window内部的任意位置(不会超出边界)，参数一可以传某window中的任意view。目标View为某window中的任意view 坐标原点：window左上角

     */

}
