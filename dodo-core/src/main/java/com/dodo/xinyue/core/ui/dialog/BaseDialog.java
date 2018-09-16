package com.dodo.xinyue.core.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.dodo.xinyue.core.util.log.DoDoLogger;
import com.flyco.dialog.utils.StatusBarUtils;

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
public abstract class BaseDialog extends AppCompatDialog {

    private static final String TAG = "BaseDialog";

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    private final Context CONTEXT;
    /**
     * 宽度缩放比例 0-1
     */
    private float mWidthScale = 1f;
    /**
     * 高度缩放比例 0-1
     */
    private float mHeightScale = 1f;

    /**
     * dialog样式
     */
    private int mDialogTheme = R.style.dialog;

    /**
     * 底层容器，用于控制dialog的位置
     * <p>
     * 注：因为window窗口最底层默认是FrameLayout，无法设置Gravity居上居中显示等，所以在上面覆盖一层RelativeLayout
     */
    private RelativeLayout mContainerView;
    /**
     * 自定义的View
     */
    private View mCustomView;

    private int mDeviceWidth;
    private int mDeviceHeight;
    private int mWorkingHeight;


    public BaseDialog(Context context) {
        this(context, R.style.dialog);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.CONTEXT = context;

        mDeviceWidth = DimenUtil.getScreenWidth();
        mDeviceHeight = DimenUtil.getScreenHeight();
        mWorkingHeight = mDeviceHeight - StatusBarUtils.getHeight(CONTEXT);
    }

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    /**
     * 调用show()执行该方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //底层容器
        mContainerView = new RelativeLayout(CONTEXT);
        mContainerView.setGravity(Gravity.CENTER);
//        mContainerView.setBackgroundColor(Color.parseColor("#30ff0000"));

        //自定义的View
        final Object tempView = setLayout();
        if (tempView instanceof Integer) {
            //这里第二个参数之所以不直接传null，是为了让用户自定义的View的根布局的宽高生效
            mCustomView = LayoutInflater.from(CONTEXT).inflate((Integer) tempView, mContainerView, false);
        } else if (tempView instanceof View) {
            mCustomView = (View) tempView;
        } else {
            throw new ClassCastException("setLayout()返回值类型必须是int或View!");
        }
        //绑定视图
        mUnbinder = ButterKnife.bind(this, mCustomView);
        //添加事件
        onBindView(savedInstanceState, mCustomView);

        mContainerView.addView(mCustomView);

        setContentView(mContainerView, new ViewGroup.LayoutParams(mDeviceWidth, mWorkingHeight));

//        mContainerView.setOnClickListener(v -> cancel());
        //防止点击穿透，导致dialog消失
        mCustomView.setClickable(true);

        //设置window窗口容器属性，必须放在setContentView()之后
//        initWindowAttrs();

    }

    public RelativeLayout getContainerView() {
        return mContainerView;
    }

    public View getCustomView() {
        return mCustomView;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        DoDoLogger.d(TAG, "onAttachedToWindow()");

//        mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        mContainerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void initWindowAttrs() {
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
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DoDoLogger.d(TAG, "onDetachedFromWindow()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //该方法在onDetachedFromWindow()后执行
        DoDoLogger.d(TAG, "onStop()");
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

}
