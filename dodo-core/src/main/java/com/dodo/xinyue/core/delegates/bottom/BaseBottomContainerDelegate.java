package com.dodo.xinyue.core.delegates.bottom;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.R2;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.bottom.anim.BottomBarEnterAnim;
import com.dodo.xinyue.core.delegates.bottom.anim.BottomBarExitAnim;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsBuilder;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsType;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

import java.util.ArrayList;
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
    private final ArrayList<BaseBottomTabBean> BIG_TAB_BEANS = new ArrayList<>();
    private final ArrayList<BaseBottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();

    private final ArrayList<View> TAB_VIEWS = new ArrayList<>();
    private final ArrayList<View> BIG_TAB_VIEWS = new ArrayList<>();

    private int mCurrentDelegateIndex = 0;
    private int mTabCount = 0;//Tab数量

    private boolean mTabCanClick = false;
    private boolean mCanQuit = false;

    @BindView(R2.id.flDelegateContainer)
    ContentFrameLayout mDelegateContainer = null;
    @BindView(R2.id.llBottomBarContainer)
    RelativeLayout mBottomBarContainer = null;
    @BindView(R2.id.llTabContainer)
    LinearLayoutCompat mTabContainer = null;
    @BindView(R2.id.llBigTabContainer)
    LinearLayoutCompat mBigTabContainer = null;
    @BindView(R2.id.vLine)
    View mLine = null;
    @BindView(R2.id.ivContainerBg)
    AppCompatImageView mContainerBg = null;//TODO 后续可添加纯色背景

    /**
     * 批量设置TabBean
     *
     * 请重写 {@link #setTabBeans} {@link #setItemDelegates} {@link #setTabBeans}
     *
     */
//    @Deprecated
//    public abstract LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> setTabItems(BottomItemBuilder builder);

    /**
     * 批量设置TabBean
     */
    public abstract ArrayList<BaseBottomTabBean> setTabBeans();

    /**
     * 批量设置BigTabBean
     */
    public ArrayList<BaseBottomTabBean> setBigTabBeans() {
        return null;
    }

    /**
     * 批量设置Delegate
     */
    public abstract ArrayList<BaseBottomItemDelegate> setItemDelegates();

    /**
     * 设置背景
     */
    @DrawableRes
    public int setBackgroundRes() {
        return 0;
    }

    /**
     * 默认第一页索引
     */
    @IntRange(from = 0)
    public int setFirstPageIndex() {
        return 0;
    }

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
    public final Object setLayout() {
        return R.layout.delegate_bottom_container;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化变量
        mCurrentDelegateIndex = setFirstPageIndex();

        TAB_BEANS.addAll(setTabBeans());
        if (setBigTabBeans() != null) {
            BIG_TAB_BEANS.addAll(setBigTabBeans());
            mBigTabContainer.setVisibility(View.VISIBLE);
        }
        ITEM_DELEGATES.addAll(setItemDelegates());

        mTabCount = ITEM_DELEGATES.size();

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //设置背景
        if (setBackgroundRes() != 0) {
            mContainerBg.setImageResource(setBackgroundRes());
        }

        initBottomBar();
        initTab();

    }

    private void initBottomBar() {
        final BottomBarParamsBuilder builder = BottomBarParamsBuilder.builder();
        final WeakHashMap<BottomBarParamsType, Object> params = setBottomBar(builder);
        if (params == null) {
            return;
        }
        for (Map.Entry<BottomBarParamsType, Object> item : params.entrySet()) {
            final BottomBarParamsType key = item.getKey();
            final Object value = item.getValue();
            if (value == null) {
                continue;
            }
            switch (key) {
                case BOTTOM_BAR_HEIGHT:
                    setBottomBarContainerHeight((int) value);
                    break;
                case BOTTOM_BAR_BACKGROUND_COLOR:
                    mBottomBarContainer.setBackgroundColor((int) value);
                    break;
                case BOTTOM_BAR_BACKGROUND_RES:
                    mBottomBarContainer.setBackgroundResource((int) value);
                    break;
                case BOTTOM_BAR_PADDING_LEFT_AND_RIGHT:
                    mBottomBarContainer.setPadding(DimenUtil.dp2px((int) value), 0, DimenUtil.dp2px((int) value), 0);
                    break;

                case TAB_CONTAINER_HEIGHT:
                    setTabContainerHeight((int) value);
                    break;
                case TAB_CONTAINER_BACKGROUND_COLOR:
                    mTabContainer.setBackgroundColor((int) value);
                    break;
                case TAB_CONTAINER_BACKGROUND_RES:
                    mTabContainer.setBackgroundResource((int) value);
                    break;
                case TAB_CONTAINER_PADDING_LEFT_AND_RIGHT:
                    mTabContainer.setPadding(DimenUtil.dp2px((int) value), 0, DimenUtil.dp2px((int) value), 0);
                    break;

                case BIG_TAB_CONTAINER_HEIGHT:
                    setBigTabContainerHeight((int) value);
                    break;
                case BIG_TAB_CONTAINE_BACKGROUND_COLOR:
                    mBigTabContainer.setBackgroundColor((int) value);
                    break;
                case BIG_TAB_CONTAINE_BACKGROUND_RES:
                    mBigTabContainer.setBackgroundResource((int) value);
                    break;
                case BIG_TAB_CONTAINE_PADDING_LEFT_AND_RIGHT:
                    mBigTabContainer.setPadding(DimenUtil.dp2px((int) value), 0, DimenUtil.dp2px((int) value), 0);
                    break;

                case LINE_HAS_VISIBLE:
                    final boolean lineVisible = (boolean) value;
                    mLine.setVisibility(lineVisible ? View.VISIBLE : View.GONE);
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
    }

    /**
     * 初始化Tab
     */
    private void initTab() {
        final int size = mTabCount;
        for (int i = 0; i < size; i++) {

            initTabBean(i);
            if (BIG_TAB_BEANS.size() == size) {
                initBigTabBean(i);
            }

            if (i == mCurrentDelegateIndex) {
                setSelectedState(i);
            } else {
                setNormalState(i);
            }

        }
    }

    private void initTabBean(int index) {
        final BaseBottomTabBean tabBean = TAB_BEANS.get(index);
        final View tabView;
        if (tabBean.setTabLayout() instanceof Integer) {
            tabView = LayoutInflater.from(getContext()).inflate((Integer) tabBean.setTabLayout(), null);
        } else if (tabBean.setTabLayout() instanceof View) {
            tabView = (View) tabBean.setTabLayout();
        } else {
            throw new ClassCastException("type of setTabLayout() must be int or View!");
        }

        //中间布局(限定水波纹范围) TabContainer -> weightView -> tabView
        @SuppressLint("RestrictedApi") final ContentFrameLayout weightView = new ContentFrameLayout(getContext());
        mTabContainer.addView(weightView);
        LinearLayoutCompat.LayoutParams weightViewParams = (LinearLayoutCompat.LayoutParams) weightView.getLayoutParams();
        weightViewParams.width = 0;
        weightViewParams.height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;//必须是wrap_content,不然水波纹无法限定
        weightViewParams.weight = 1;
        //上 中 下
        weightViewParams.gravity = tabBean.getTabGravity();

        weightView.setLayoutParams(weightViewParams);
        weightView.setBackgroundColor(Color.parseColor("#01000000"));//限定水波纹扩散范围

        weightView.addView(tabView);
        ContentFrameLayout.LayoutParams tabViewParams = (ContentFrameLayout.LayoutParams) tabView.getLayoutParams();
        tabViewParams.width = ContentFrameLayout.LayoutParams.MATCH_PARENT;
//        tabViewParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
//        tabViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tabView.setLayoutParams(tabViewParams);

        TAB_VIEWS.add(tabView);
        tabBean.initView(tabView);
        tabView.setBackground(tabBean.getContainerSelector());

        tabView.setTag(index);//用于记录点击的是第几个，便于控制delegate的显示和隐藏
        tabView.setOnClickListener(this);
    }

    private void initBigTabBean(int index) {
        final BaseBottomTabBean bigTabBean = BIG_TAB_BEANS.get(index);
        final View bigTabView;
        if (bigTabBean.setBigTabLayout() == null) {
            return;
        } else if (bigTabBean.setBigTabLayout() instanceof Integer) {
            bigTabView = LayoutInflater.from(getContext()).inflate((Integer) bigTabBean.setBigTabLayout(), null);
        } else if (bigTabBean.setBigTabLayout() instanceof View) {
            bigTabView = (View) bigTabBean.setBigTabLayout();
        } else {
            throw new ClassCastException("type of setBigTabLayout() must be int or View!");
        }

        //中间布局(限定水波纹范围) TabContainer -> weightView -> tabView
        @SuppressLint("RestrictedApi") final ContentFrameLayout weightView = new ContentFrameLayout(getContext());
        mBigTabContainer.addView(weightView);
        LinearLayoutCompat.LayoutParams weightViewParams = (LinearLayoutCompat.LayoutParams) weightView.getLayoutParams();
        weightViewParams.width = 0;
        weightViewParams.height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;//必须是wrap_content,不然水波纹无法限定
        weightViewParams.weight = 1;
        //上 中 下
        weightViewParams.gravity = bigTabBean.getTabGravity();

        weightView.setLayoutParams(weightViewParams);
        weightView.setBackgroundColor(Color.parseColor("#01000000"));//限定水波纹扩散范围

        weightView.addView(bigTabView);
        ContentFrameLayout.LayoutParams tabViewParams = (ContentFrameLayout.LayoutParams) bigTabView.getLayoutParams();
        tabViewParams.width = ContentFrameLayout.LayoutParams.MATCH_PARENT;
//        tabViewParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
//        tabViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        bigTabView.setLayoutParams(tabViewParams);

        BIG_TAB_VIEWS.add(bigTabView);
        bigTabBean.initView(bigTabView);
        bigTabView.setBackground(bigTabBean.getContainerSelector());

        bigTabView.setTag(index);//用于记录点击的是第几个，便于控制delegate的显示和隐藏
        bigTabView.setOnClickListener(this);

    }

    /**
     * 初始化所有子delegate
     */
    private void initItemDelegates() {

//        //设置DeledateContainer的高度
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mDelegateContainer.getLayoutParams();
        lp.height = mDelegateContainer.getHeight() - mTabContainer.getHeight();
        mDelegateContainer.setLayoutParams(lp);

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
     */
    private void switchState(int normalIndex, int selectedIndex) {
        setNormalState(normalIndex);
        setSelectedState(selectedIndex);
    }

    private void setNormalState(int normalIndex) {
        final BaseBottomTabBean oldTabBean = TAB_BEANS.get(normalIndex);
        final View tabView = TAB_VIEWS.get(normalIndex);
        if (BIG_TAB_VIEWS.size() == 0) {
            tabView.setSelected(false);
        } else {
            final BaseBottomTabBean oldBigTabBean = BIG_TAB_BEANS.get(normalIndex);
            final View bigTabView = BIG_TAB_VIEWS.get(normalIndex);
            bigTabView.setVisibility(View.INVISIBLE);
            bigTabView.setSelected(false);

            //显示tabView
            tabView.setVisibility(View.VISIBLE);
        }

    }

    private void setSelectedState(int selectedIndex) {
        final BaseBottomTabBean currentTabBean = TAB_BEANS.get(selectedIndex);
        final View tabView = TAB_VIEWS.get(selectedIndex);
        if (BIG_TAB_VIEWS.size() == 0) {
            tabView.setSelected(true);
        } else {
            final BaseBottomTabBean oldBigTabBean = BIG_TAB_BEANS.get(selectedIndex);
            final View bigTabView = BIG_TAB_VIEWS.get(selectedIndex);
            bigTabView.setVisibility(View.VISIBLE);
            bigTabView.setSelected(true);

            //隐藏tabView
            tabView.setVisibility(View.INVISIBLE);
        }

    }

    private void setBottomBarContainerHeight(int height) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBottomBarContainer.getLayoutParams();
        layoutParams.height = DimenUtil.dp2px(height);
        mBottomBarContainer.setLayoutParams(layoutParams);
    }

    private void setTabContainerHeight(int height) {
        LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) mTabContainer.getLayoutParams();
        layoutParams.height = DimenUtil.dp2px(height);
        mTabContainer.setLayoutParams(layoutParams);
    }

    private void setBigTabContainerHeight(int height) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBigTabContainer.getLayoutParams();
        layoutParams.height = DimenUtil.dp2px(height);
        mBigTabContainer.setLayoutParams(layoutParams);
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