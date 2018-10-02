package com.dodo.xinyue.core.delegates.bottom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.R2;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.bottom.anim.BottomBarEnterAnim;
import com.dodo.xinyue.core.delegates.bottom.anim.BottomBarExitAnim;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsBuilder;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsType;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomItemBuilder;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 底部容器 基类
 * 负责统一处理并管理底部Tab和相应的delegate
 *
 * @author DoDo
 * @date 2017/9/7
 */
public abstract class BaseBottomContainerDelegate extends DoDoDelegate implements View.OnClickListener {

    private final ArrayList<BaseBottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BaseBottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private final ArrayList<ViewGroup> CONTAINERS = new ArrayList<>();

    private int mCurrentDelegateIndex = 0;
    private int mTabCount = 0;//Tab数量

    private boolean mTabCanClick = false;
    private boolean mCanQuit = false;

    @BindView(R2.id.llBottomBarContainer)
    LinearLayoutCompat mBottomBarContainer = null;
    @BindView(R2.id.llTabContainer)
    LinearLayoutCompat mBottomBar = null;
    @BindView(R2.id.vLine)
    View mLine = null;
    @BindView(R2.id.ivContainerBg)
    AppCompatImageView mContainerBg = null;//TODO 后续可添加纯色背景

    /**
     * 设置背景
     */
    @DrawableRes
    public abstract int setBackgroundRes();

    /**
     * 批量设置TabBean
     */
    public abstract LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> setTabItems(BottomItemBuilder builder);

    /**
     * 默认第一页索引
     */
    @IntRange(from = 0)
    public abstract int setFirstPageIndex();

    /**
     * 设置Bottombar参数
     */
    public WeakHashMap<BottomBarParamsType, Object> setBottomBar(BottomBarParamsBuilder builder) {
        return null;
    }

    /**
     * Tab点击事件分发
     *
     * @param position
     * @param isRepeat 重复点击同一个Tab
     * @return true拦截 false放行
     */
    public boolean onTabSelected(int position, boolean isRepeat) {
        return false;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom_container;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化变量
        mCurrentDelegateIndex = setFirstPageIndex();

        final BottomItemBuilder builder = BottomItemBuilder.builder();
        final LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> items = setTabItems(builder);
        ITEMS.putAll(items);
        mTabCount = ITEMS.size();
        for (Map.Entry<BaseBottomTabBean, BaseBottomItemDelegate> item : ITEMS.entrySet()) {
            final BaseBottomTabBean key = item.getKey();
            final BaseBottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //设置背景
        mContainerBg.setImageResource(setBackgroundRes());

        initBottomBar();
        initTabBean();

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        //如果需要同时加载多个fragment,可以在这里loadMultipleRootFragment
        YoYo.with(new BottomBarEnterAnim())
                .interpolate(new OvershootInterpolator())//回弹
                .duration(666)
                .onEnd(animator -> {
                    initItemDelegates();
                    mTabCanClick = true;
                    mCanQuit = true;
                })
                .playOn(mBottomBarContainer);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            YoYo.with(new BottomBarExitAnim())
                    .interpolate(new AccelerateInterpolator())//加速
                    .duration(100)
                    .playOn(mBottomBarContainer);
        } else {
            YoYo.with(new BottomBarEnterAnim())
                    .interpolate(new DecelerateInterpolator())//减速
                    .duration(380)
                    .delay(50)
                    .playOn(mBottomBarContainer);
        }
    }

    private void initBottomBar() {
        final BottomBarParamsBuilder builder = BottomBarParamsBuilder.builder();
        final WeakHashMap<BottomBarParamsType, Object> params = setBottomBar(builder);
        if (params == null) {
            return;
        }
//        boolean isSetBottomBarHeight = false;//如果用户没有设置,需要设置一个默认值
        for (Map.Entry<BottomBarParamsType, Object> item : params.entrySet()) {
            final BottomBarParamsType key = item.getKey();
            final Object value = item.getValue();
            if (value == null) {
                continue;
            }
            switch (key) {
                case BOTTOM_BAR_HEIGHT:
                    final int bottombarHeight = (int) value;
                    setBottombarHeight(bottombarHeight);
//                    isSetBottomBarHeight = true;
                    break;
                case BOTTOM_BAR_BACKGROUND_COLOR:
                    final int bottombarBackgroundColor = (int) value;
                    mBottomBar.setBackgroundColor(bottombarBackgroundColor);
                    break;
                case BOTTOM_BAR_BACKGROUND_RES:
                    final int bottombarBackgroundRes = (int) value;
                    mBottomBar.setBackgroundResource(bottombarBackgroundRes);
                    break;
                case BOTTOM_BAR_PADDING_LEFT_AND_RIGHT:
                    final int bottombarPadding = (int) value;
                    mBottomBar.setPadding(DimenUtil.dp2px(bottombarPadding), 0, DimenUtil.dp2px(bottombarPadding), 0);
                    break;
                case LINE_HAS_VISIBLE:
                    final boolean lineVisible = (boolean) value;
                    if (lineVisible) {
                        mLine.setVisibility(View.VISIBLE);
                    } else {
                        mLine.setVisibility(View.GONE);
                    }
                    break;
                case LINE_HEIGHT:
                    final int lineHeight = (int) value;
                    setLineHeight(lineHeight);
                    break;
                case LINE_BACKGROUND_COLOR:
                    final int lineBackgroundColor = (int) value;
                    mLine.setBackgroundColor(lineBackgroundColor);
                    break;
                case LINE_BACKGROUND_RES:
                    final int lineBackgroundRes = (int) value;
                    mLine.setBackgroundResource(lineBackgroundRes);
                    break;
                default:
                    break;
            }
        }
//        if (!isSetBottomBarHeight) {
//            setBottombarHeight(50);
//        }
    }

    /**
     * 实例化item
     */
    private void initTabBean() {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            final BaseBottomTabBean tabBean = TAB_BEANS.get(i);
            final int layoutId = tabBean.getLayoutId();

            final View customView = LayoutInflater.from(getContext()).inflate(layoutId, null);
            if (!(customView instanceof ViewGroup)) {
                throw new RuntimeException("the view of TabBean must be a ViewGroup!");
            }

            final ViewGroup container = (ViewGroup) customView;

            //权重适配布局，Tab布局之上，mBottomBar布局之下
            @SuppressLint("RestrictedApi") final ContentFrameLayout rootView = new ContentFrameLayout(getContext());

            mBottomBar.addView(rootView);
            rootView.addView(container);
//            mBottomBar.addView(container);
            //修改宽高，先获取，再修改，最后设置
            //使用父布局参数类型设置子布局参数
            LinearLayoutCompat.LayoutParams rootViewParams = (LinearLayoutCompat.LayoutParams) rootView.getLayoutParams();
            rootViewParams.width = 0;
            rootViewParams.height = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
            rootViewParams.weight = 1;
            rootView.setLayoutParams(rootViewParams);
//            rootView.setBackgroundColor(Color.WHITE);//防止和底部布局背景重叠(和水波纹扩散范围有关)
//
            ContentFrameLayout.LayoutParams containerParams = (ContentFrameLayout.LayoutParams) container.getLayoutParams();
            containerParams.width = ContentFrameLayout.LayoutParams.MATCH_PARENT;
            containerParams.height = ContentFrameLayout.LayoutParams.MATCH_PARENT;
            container.setLayoutParams(containerParams);

//            LinearLayoutCompat.LayoutParams rootViewParams = (LinearLayoutCompat.LayoutParams) container.getLayoutParams();
//            rootViewParams.width = 0;
//            rootViewParams.height = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
//            rootViewParams.weight = 1;
//            container.setLayoutParams(rootViewParams);
//            container.setBackgroundColor(Color.WHITE);//防止和底部布局背景重叠


            CONTAINERS.add(container);
            tabBean.initView(container);
            container.setBackground(tabBean.getContainerSelector());

            container.setTag(i);//用于记录点击的是第几个，便于控制delegate的显示和隐藏
            container.setOnClickListener(this);
            if (i == mCurrentDelegateIndex) {
//                tabBean.setSelectedState(container);
                setSelectedState(i);
            } else {
//                tabBean.setNormalState(container);
                setNormalState(i);
            }

        }
    }

    /**
     * 初始化delegate
     */
    private void initItemDelegates() {
        final ISupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new ISupportFragment[mTabCount]);
        getSupportDelegate().loadMultipleRootFragment(R.id.flDelegateContainer, mCurrentDelegateIndex, delegateArray);
    }


    @Override
    public void onClick(View v) {
        if (!mTabCanClick) {
            return;
        }
        final int currentIndex = (int) v.getTag();
        final int oldIndex = mCurrentDelegateIndex;
        if (currentIndex == oldIndex) {
            onTabSelected(currentIndex, true);
            return;
        }

        switchState(oldIndex, currentIndex);

        if (onTabSelected(currentIndex, false)) {
            return;
        }

        //显示选中的delegate,隐藏上一个delegate
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(currentIndex), ITEM_DELEGATES.get(oldIndex));
        mCurrentDelegateIndex = currentIndex;
    }

    /**
     * 切换Tab状态
     *
     * @param normalIndex
     * @param selectedIndex
     */
    private void switchState(int normalIndex, int selectedIndex) {
        setNormalState(normalIndex);
        setSelectedState(selectedIndex);
    }

    private void setNormalState(int normalIndex) {
        final BaseBottomTabBean oldTabBean = TAB_BEANS.get(normalIndex);
        final ViewGroup container = CONTAINERS.get(normalIndex);
        container.setSelected(false);//press事件会通过父布局自动传递给子布局，无需处理
        final int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            container.getChildAt(i).setSelected(false);
        }
//        oldTabBean.setNormalState(container);
    }

    private void setSelectedState(int selectedIndex) {
        final BaseBottomTabBean currentTabBean = TAB_BEANS.get(selectedIndex);
        final ViewGroup container = CONTAINERS.get(selectedIndex);
        container.setSelected(true);
        final int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            container.getChildAt(i).setSelected(true);
        }
//        currentTabBean.setSelectedState(container);
    }

    private void setBottombarHeight(int height) {
        LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) mBottomBar.getLayoutParams();
        layoutParams.height = DimenUtil.dp2px(height);
        mBottomBar.setLayoutParams(layoutParams);
    }

    private void setLineHeight(int height) {
        LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) mLine.getLayoutParams();
        layoutParams.height = DimenUtil.dp2px(height);
        mLine.setLayoutParams(layoutParams);
    }

    public void switchDelegate(int index) {
        final int currentIndex = index;
        final int oldIndex = mCurrentDelegateIndex;
        if (currentIndex == oldIndex) {
            onTabSelected(currentIndex, true);
            return;
        }

        switchState(oldIndex, currentIndex);

        if (onTabSelected(currentIndex, false)) {
            return;
        }

        //显示选中的delegate,隐藏上一个delegate
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(currentIndex), ITEM_DELEGATES.get(oldIndex));
        mCurrentDelegateIndex = currentIndex;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (super.onBackPressedSupport()) {
            return true;//防止动画未结束就按返回键
        }
        return !mCanQuit;
    }
}