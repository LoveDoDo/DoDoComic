package com.dodo.xinyue.conan.main.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.main.index.adapter.IndexAdapter;
import com.dodo.xinyue.conan.main.index.data.IndexDataConverter;
import com.dodo.xinyue.conan.main.index.listener.IndexItemClickListener;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.net.RestClient;
import com.dodo.xinyue.core.net.callback.ISuccess;
import com.dodo.xinyue.core.ui.image.GlideApp;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import butterknife.BindView;

/**
 * IndexContentDelegate
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexContentDelegate extends DoDoDelegate implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "IndexContentDelegate";
    private static final String ARGS_URL = "pager_url";
    private String mUrl = null;
    private int mForm = 0;
    private int mLanguage = 0;
    private int mType = 0;

    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private MulAdapter mAdapter = null;
    private IndexDataConverter mDataConverter = null;
    private GridLayoutManager mLayoutManager = null;

    public static IndexContentDelegate create(String url) {
        final Bundle args = new Bundle();
        args.putString(ARGS_URL, url);
        final IndexContentDelegate delegate = new IndexContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final String url = getArguments().getString(ARGS_URL);
        if (!TextUtils.isEmpty(url)) {
            mUrl = url;
        }
        final IndexDelegate delegate = getParentDelegate();
        mForm = delegate.getForm();
        mLanguage = delegate.getLanguage();
        mType = delegate.getType();
//        DoDoLogger.d(TAG, mForm);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //防止viewpager滚动多次导致重复刷新
        if (mAdapter == null) {

            initRefreshLayout();
            initRecyclerView();

            mRefreshLayout.setRefreshing(true);

            //这里适当延时一下，不然切换数字列表会卡
            DoDo.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestData();
                }
            }, 200);

        }

    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                R.color.app_background
        );
        mRefreshLayout.setOnRefreshListener(this);
//        //设置灵敏度
//        mRefreshLayout.setDistanceToTriggerSync(400);
    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 6);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //优化
        mRecyclerView.setHasFixedSize(true);
        switch (mForm) {
            case 0:
                //分割线
                mRecyclerView.addItemDecoration(
                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.parseColor("#20ffffff"))
                                .margin(DimenUtil.dp2px(54), 0)
                                .build()
                );
                break;
            case 1:
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRecyclerView.getLayoutParams();
//                layoutParams.setMargins(DimenUtil.dp2px(8), DimenUtil.dp2px(8), 0, 0);
//                mRecyclerView.setLayoutParams(layoutParams);
                mRecyclerView.setPadding(DimenUtil.dp2px(8), DimenUtil.dp2px(8), 0, 0);
                //分割线
                mRecyclerView.addItemDecoration(
                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.TRANSPARENT)
                                .size(DimenUtil.dp2px(8))
                                .showLastDivider()
                                .build()
                );
                mRecyclerView.addItemDecoration(
                        new VerticalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.TRANSPARENT)
                                .size(DimenUtil.dp2px(8))
                                .showLastDivider()
                                .build()
                );
                break;
            case 2:
                //分割线
                mRecyclerView.addItemDecoration(
                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.parseColor("#20ffffff"))
                                .build()
                );
                break;
            case 3:
                mRecyclerView.setPadding(DimenUtil.dp2px(8), DimenUtil.dp2px(8), 0, 0);
                //分割线
                mRecyclerView.addItemDecoration(
                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.TRANSPARENT)
                                .size(DimenUtil.dp2px(8))
                                .showLastDivider()
                                .build()
                );
                mRecyclerView.addItemDecoration(
                        new VerticalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.TRANSPARENT)
                                .size(DimenUtil.dp2px(8))
                                .showLastDivider()
                                .build()
                );
                break;
            default:
                break;
        }

        //取消adapter.notifyItemChanged()方法自带的默认动画效果
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        //优化快速滑动时的图片加载
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private final int defaultY = 100;//快速滑动的默认临界值，超过改值则暂停图片加载，当小于改值时恢复图片加载
            private boolean toggle = false;//进入快速滑动标识1
            private boolean toggle2 = false;//暂停加载标识，是需要暂停一次即可
            private int tempY = -1;//就是dy的绝对值，定义这个主要是为了修正惯性滑动刚开始时的dy值异常（刚滑动可能就会突然大于100）

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                switch (recyclerView.getScrollState()) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        //按住并滑动
                        //这里会多次响应该状态，因为只需要在这里初始化一次标识符，所以移到下面onScrollStateChanged()里进行初始化
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        //松手后惯性滑动
                        if (tempY == -1) {
                            tempY = 0;//修正惯性滑动刚开始时的dy值异常
                        } else if (tempY >= 0) {
                            tempY = Math.abs(dy);
                        }

                        if (tempY > defaultY && !toggle) {
                            //进入快速滑动
                            toggle = true;
                        }
                        if (toggle) {
                            if (tempY > defaultY && !toggle2) {
                                toggle2 = true;
                                GlideApp.with(getProxyActivity()).pauseRequestsRecursive();
//                                Log.d("HAHAHA", "暂停加载" + tempY);
                            } else if (tempY < defaultY) {
                                if (GlideApp.with(getProxyActivity()).isPaused()) {
                                    GlideApp.with(getProxyActivity()).resumeRequestsRecursive();
//                                    Log.d("HAHAHA", "开启加载" + tempY);
                                }
                            }

                        }
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //完全停止滑动
                        //这里是滑动监听，所以停止滑动的时候该状态不会在此响应
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        //按住并滑动
//                        Log.d("HAHAHA", "SCROLL_STATE_DRAGGING");
                        toggle = false;
                        toggle2 = false;
                        tempY = -1;
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        //松手后惯性滑动
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //完全停止滑动
//                        Log.d("HAHAHA", "SCROLL_STATE_IDLE");
                        if (GlideApp.with(getProxyActivity()).isPaused()) {
                            GlideApp.with(getProxyActivity()).resumeRequestsRecursive();
//                            Log.d("HAHAHA", "停止滑动，并处于暂停状态，重新开启加载");
                        }
                        toggle = false;
                        toggle2 = false;
                        tempY = -1;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData() {

        RestClient.builder()
                .url(mUrl)
//                .url("intercept")
//                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String json = response.substring("var tvInfoJs=".length(), response.length());
                        handleData(json);
                        mRefreshLayout.setRefreshing(false);
//                        DoDoLogger.d(TAG, response);
                    }
                })
                .build()
                .get();
    }

    private View noNetView = null;
    private View noDataView = null;

    /**
     * 处理数据
     *
     * @param json
     */
    private void handleData(String json) {
        mDataConverter = new IndexDataConverter();
        final IndexDelegate comicDelegate = getParentDelegate();
        mAdapter = IndexAdapter.create(mDataConverter.setForm(mForm).setLanguage(mLanguage).setType(mType).setJsonData(json), comicDelegate);
        //空布局
        //如果加载失败，需要点击空布局进行刷新，所以设置空布局方法就放在刷新操作里
        //这里只做初始化空布局view的工作
        noNetView = LayoutInflater.from(getProxyActivity()).inflate(R.layout.view_empty_no_net, (ViewGroup) mRecyclerView.getParent(), false);//这里不写父布局也可以
        noDataView = LayoutInflater.from(getProxyActivity()).inflate(R.layout.view_empty_no_data, (ViewGroup) mRecyclerView.getParent(), false);//这里不写父布局也可以
        noDataView.setOnClickListener(v -> refreshData());
        noNetView.setOnClickListener(v -> refreshData());

//        //内部会调用rv.setAdapter()并保存rv的实例，方便获取到item内部的子控件 getViewByPosition()
        mAdapter.bindToRecyclerView(mRecyclerView);
//        mRecyclerView.setAdapter(mAdapter);//设置上拉加载会自动保存rv实例，所以不能用bindToRecyclerView()

        /**
         * item点击
         */
        mAdapter.setOnItemClickListener(IndexItemClickListener.create(comicDelegate));
        /**
         * item子控件点击
         * 需要在adapter中设置需要响应事件的子控件
         * 子控件如果响应了ChildClickListener，则该子控件就不会再响应item的点击事件
         */
//        mAdapter.setOnItemChildClickListener(TuiJianItemChildClickListener.create());

    }

    private boolean mNoNet = true;//无网络
    private boolean mNoData = true;//无数据

    /**
     * 下拉刷新
     */
    private void refreshData() {
        mAdapter.setEmptyView(R.layout.view_empty_loading, (ViewGroup) mRecyclerView.getParent());//这里不写父布局也可以。默认使用rv作为父布局
        DoDo.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //清空adapter之前的数据
                mDataConverter.clearData();
                mAdapter.setNewData(mDataConverter.convert());
                //重新开启下拉刷新监听
                ToastUtils.showShort("刷新完成");
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        }, 800);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        refreshData();
    }

    /**
     * 防止BottomDelegate无法响应返回键导致无法退出
     */
    @Override
    public boolean onBackPressedSupport() {
        return false;
    }
}
