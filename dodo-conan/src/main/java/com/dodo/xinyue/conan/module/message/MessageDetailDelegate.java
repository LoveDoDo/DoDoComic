package com.dodo.xinyue.conan.module.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;
import com.dodo.xinyue.conan.module.message.adapter.MessageDetailAdapter;
import com.dodo.xinyue.conan.module.message.data.MessageDetailDataConverter;
import com.dodo.xinyue.conan.module.message.listener.MessageDetailItemChildClickListener;
import com.dodo.xinyue.conan.module.message.listener.MessageDetailItemChildLongClickListener;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * MessageDetailDelegate
 *
 * @author DoDo
 * @date 2019/1/19
 */
public class MessageDetailDelegate extends BaseModuleDelegate {

    private static final String ARGS_TYPE = "args_type";

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
        return mTitle;
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
        if (mDataConverter.getMessageCount(mType) <= 0) {
            mAdapter.setEmptyView(mNoDataView);
            return;
        }
        mAdapter.setNewData(mDataConverter.setType(mType).convert());
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
    }

}
