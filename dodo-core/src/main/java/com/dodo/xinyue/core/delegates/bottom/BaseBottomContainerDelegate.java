package com.dodo.xinyue.core.delegates.bottom;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.R2;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsBuilder;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsType;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomItemBuilder;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.dodo.xinyue.core.util.toast.ToastUtil;

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

    //双击退出间隔时间
    protected static final long WAIT_TIME = 2000L;
    //记录上次点击的时间
    protected long TOUCH_TIME = 0;

    private final ArrayList<BaseBottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BaseBottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BaseBottomTabBean, BaseBottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private final ArrayList<ViewGroup> CONTAINERS = new ArrayList<>();

    private int mCurrentDelegateIndex = 0;
    private int mTabCount = 0;//Tab数量


    @BindView(R2.id.ll_bottom_bar)
    LinearLayoutCompat mBottomBar = null;
    @BindView(R2.id.line_bottom_bar)
    View mLine = null;

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
        initBottomBar();
        initTabBean();
        initItemDelegates();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        //如果需要同时加载多个fragment,可以在这里loadMultipleRootFragment
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
                    final int bottombarHeight = (int) value;
                    setBottombarHeight(bottombarHeight);
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
            final ContentFrameLayout rootView = new ContentFrameLayout(getContext());

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
        getSupportDelegate().loadMultipleRootFragment(R.id.fl_bottom_container, mCurrentDelegateIndex, delegateArray);
    }


    @Override
    public void onClick(View v) {
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

    /**
     * 双击返回键退出应用
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            getProxyActivity().finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtil.showLong("再按一次返回键将关闭程序");
        }
        return true;//消费掉该事件，不再向后传递
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
}