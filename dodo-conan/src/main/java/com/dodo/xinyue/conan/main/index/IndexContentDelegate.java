package com.dodo.xinyue.conan.main.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.main.index.adapter.IndexAdapter;
import com.dodo.xinyue.conan.main.index.data.IndexDataConverter;
import com.dodo.xinyue.conan.main.index.listener.IndexItemChildClickListener;
import com.dodo.xinyue.conan.main.index.listener.IndexItemClickListener;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.net.RestClient;
import com.dodo.xinyue.core.ui.image.GlideApp;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * IndexContentDelegate
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexContentDelegate extends DoDoDelegate {

    private static final String TAG = "IndexContentDelegate";
    private static final String ARGS_URL = "pager_url";
    private String mUrl = null;
    private int mLanguageIndex = 0;
    private int mTypeIndex = 0;
    private int mFormIndex = 0;

    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerView;

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
        final Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        final String url = bundle.getString(ARGS_URL);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        this.mUrl = url;
        final IndexDelegate delegate = getParentDelegate();
        this.mLanguageIndex = delegate.getLanguageIndex();
        this.mTypeIndex = delegate.getTypeIndex();
        this.mFormIndex = delegate.getFormIndex();

        initRecyclerView();
        initAdapter();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        if (savedInstanceState != null) {
//            return;
//        }
        requestData();
        //这里适当延时一下，不然切换数字列表会卡
//        DoDo.getHandler().postDelayed(this::requestData, 300);

    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 6);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //优化
        mRecyclerView.setHasFixedSize(true);
        switch (mFormIndex) {
            case ApiConstants.FORM_TEXT:
                //分割线
                mRecyclerView.addItemDecoration(
                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.parseColor("#20ffffff"))
                                .margin(DimenUtil.dp2px(54), 0)
                                .build()
                );
                break;
            case ApiConstants.FORM_NUMBER:
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRecyclerView.getLayoutParams();
//                layoutParams.setMargins(DimenUtil.dp2px(8), DimenUtil.dp2px(8), 0, 0);
//                mRecyclerView.setLayoutParams(layoutParams);
                mRecyclerView.setPadding(DimenUtil.dp2px(8), 0, 0, 0);
                //分割线
//                mRecyclerView.addItemDecoration(
//                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
//                                .color(Color.TRANSPARENT)
//                                .size(DimenUtil.dp2px(8))
//                                .showLastDivider()
//                                .build()
//                );
                mRecyclerView.addItemDecoration(
                        new VerticalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.TRANSPARENT)
                                .size(DimenUtil.dp2px(8))
                                .showLastDivider()
                                .build()
                );
                break;
            case ApiConstants.FORM_IMAGE_TEXT:
                mRecyclerView.setPadding(0, DimenUtil.dp2px(4), 0, 0);
                //分割线
                mRecyclerView.addItemDecoration(
                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
                                .color(Color.parseColor("#20ffffff"))
                                .margin(DimenUtil.getScreenWidth() * 5 / 11, 0)
                                .build()
                );
                break;
            case ApiConstants.FORM_GRID:
                mRecyclerView.setPadding(DimenUtil.dp2px(8), 0, 0, 0);
                //分割线
//                mRecyclerView.addItemDecoration(
//                        new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
//                                .color(Color.TRANSPARENT)
//                                .size(DimenUtil.dp2px(8))
//                                .showLastDivider()
//                                .build()
//                );
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
                            if (getContext() == null) {
                                return;
                            }
                            if (tempY > defaultY && !toggle2) {
                                toggle2 = true;
                                GlideApp.with(getContext()).pauseRequestsRecursive();
//                                Log.d("HAHAHA", "暂停加载" + tempY);
                            } else if (tempY < defaultY) {
                                if (GlideApp.with(getContext()).isPaused()) {
                                    GlideApp.with(getContext()).resumeRequestsRecursive();
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
                        if (getContext() == null) {
                            return;
                        }
                        if (GlideApp.with(getContext()).isPaused()) {
                            GlideApp.with(getContext()).resumeRequestsRecursive();
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

    private View mLoadingView = null;
    private View mLoadFailureView = null;

    private void initAdapter() {
        final IndexDelegate indexDelegate = getParentDelegate();
        mAdapter = IndexAdapter.create(new ArrayList<>(), indexDelegate);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mLoadingView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.view_empty_loading, mRecyclerView, false);
        mLoadFailureView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.view_empty_no_data, mRecyclerView, false);
        mLoadFailureView.setOnClickListener(v -> requestData());
        mAdapter.setOnItemClickListener(IndexItemClickListener.create(indexDelegate));
        mAdapter.setOnItemChildClickListener(IndexItemChildClickListener.create(indexDelegate));
    }

    /**
     * 请求数据
     */
    private void requestData() {

        mAdapter.setEmptyView(mLoadingView);

        RestClient.builder()
                .url(mUrl)
                .success(response -> {
                    final String json = response.substring("var tvInfoJs=".length(), response.length());
                    handleData(json);
                })
                .failure(() -> mAdapter.setEmptyView(mLoadFailureView))
                .error((code, msg) -> mAdapter.setEmptyView(mLoadFailureView))
                .build()
                .get();
    }

    /**
     * 处理数据
     *
     */
    private void handleData(String json) {
        mDataConverter = new IndexDataConverter();
        final List<MulEntity> data = mDataConverter.setFormIndex(mFormIndex).setLanguageIndex(mLanguageIndex).setTypeIndex(mTypeIndex).setJsonData(json).convert();
        if (data.size() <= 0) {
            mAdapter.setEmptyView(R.layout.view_empty_no_data, mRecyclerView);
            return;
        }
        mAdapter.setNewData(data);
    }

    /**
     * 防止BottomDelegate无法响应返回键导致无法退出
     */
    @Override
    public boolean onBackPressedSupport() {
        return false;
    }

    @Override
    public boolean isTrack() {
        return false;
    }
}
