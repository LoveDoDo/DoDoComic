package com.dodo.xinyue.conan.module.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.listener.IHandleMessage;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;
import com.dodo.xinyue.conan.module.message.adapter.MessageDetailAdapter;
import com.dodo.xinyue.conan.module.message.data.MessageDetailDataConverter;
import com.dodo.xinyue.conan.module.message.listener.MessageDetailItemChildClickListener;
import com.dodo.xinyue.conan.module.message.listener.MessageDetailItemChildLongClickListener;
import com.dodo.xinyue.conan.view.ConanLoadMoreView;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.util.log.DoDoLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MessageDetailDelegate
 *
 * @author DoDo
 * @date 2019/1/19
 */
public class MessageDetailDelegate extends BaseModuleDelegate implements BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "MessageDetailDelegate";
    private static final String ARGS_TYPE = "args_type";
    private static final int PAGE_COUNT = 10;//分页加载 每页item数
    private int mPage = 0;//分页加载 页数

    private int mType;
    private String mTitle = null;

    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerView;

    private MulAdapter mAdapter = null;
    private MessageDetailDataConverter mDataConverter = null;
    private GridLayoutManager mLayoutManager = null;


    public static MessageDetailDelegate create(int type) {
        final Bundle args = new Bundle();
        args.putInt(ARGS_TYPE, type);
        final MessageDetailDelegate delegate = new MessageDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args == null) {
            return;
        }
        mType = args.getInt(ARGS_TYPE);
        mTitle = ApiHelper.getMessageNickName(mType);
    }

    @Override
    public Object setChildLayout() {
        return R.layout.delegate_message_detail;
    }

    @Override
    public String setTitle() {
        return mTitle + "(" + ConanDataBaseManager.getMessageCount(mType) + ")";
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        initRecyclerView();
        initAdapter();

        loadData();
    }

    private void loadData() {
        mAdapter.setEmptyView(mLoadingView);
        mDataConverter = new MessageDetailDataConverter();
        if (ConanDataBaseManager.getMessageCount(mType) <= 0) {
            mAdapter.setEmptyView(mNoDataView);
            return;
        }
        ConanDataBaseManager.queryMessageAsync(mType, mPage++, PAGE_COUNT, new IHandleMessage() {
            @Override
            public void onSuccess(List<JiGuangMessage> result) {
                mAdapter.setNewData(mDataConverter.setData(result).convert());
                mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
            }

            @Override
            public void onFailure() {
                mAdapter.setEmptyView(mNoDataView);
                DoDoLogger.d("查询消息失败");
            }
        });

    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //优化
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);//TODO 对于动态添加的情况，要显示scrollbar并且不显示OverScrollMode，必须代码里动态设置，布局里设置不起作用
        //取消adapter.notifyItemChanged()方法自带的默认动画效果
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private View mLoadingView = null;
    private View mNoDataView = null;

    private void initAdapter() {
        mAdapter = MessageDetailAdapter.create(new ArrayList<>(), null);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mLoadingView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.view_empty_loading, mRecyclerView, false);
        mNoDataView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.view_empty_no_data, mRecyclerView, false);
        mAdapter.setOnItemChildClickListener(MessageDetailItemChildClickListener.create(this));
        mAdapter.setOnItemChildLongClickListener(MessageDetailItemChildLongClickListener.create(this));

        //下拉加载
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setLoadMoreView(new ConanLoadMoreView());
        mAdapter.enableLoadMoreEndClick(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConanDataBaseManager.queryMessageAsync(mType, mPage++, PAGE_COUNT, new IHandleMessage() {
                    @Override
                    public void onSuccess(List<JiGuangMessage> result) {
                        final int size = result.size();
                        if (size <= 0) {
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        mAdapter.addData(mDataConverter.addData(result));
                        if (size == PAGE_COUNT) {
                            mAdapter.loadMoreComplete();
                        } else {
                            mAdapter.loadMoreEnd(true);
                        }

                        DoDoLogger.d(TAG, "加载更多 page：" + mPage);
                    }

                    @Override
                    public void onFailure() {
                        DoDoLogger.d(TAG, "查询消息失败");
                        mAdapter.loadMoreFail();
                    }
                });
            }
        }, 520);


    }
}
