package com.dodo.xinyue.conan.module.message;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.module.message.adapter.MessageCenterAdapter;
import com.dodo.xinyue.conan.module.message.callback.IConvertMessage;
import com.dodo.xinyue.conan.module.message.data.MessageCenterDataConverter;
import com.dodo.xinyue.conan.module.message.listener.MessageCenterItemClickListener;
import com.dodo.xinyue.conan.module.message.listener.MessageCenterItemLongClickListener;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MessageCenterContentDelegate
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageCenterContentDelegate extends DoDoDelegate {

    private static final String ARGS_TYPE = "args_type";
    private int mType;

    @BindView(R2.id.recyclerview)
    RecyclerView mRecyclerView;

    private MulAdapter mAdapter = null;
    private MessageCenterDataConverter mDataConverter = null;
    private GridLayoutManager mLayoutManager = null;

    public static MessageCenterContentDelegate create(int type) {
        final Bundle args = new Bundle();
        args.putInt(ARGS_TYPE, type);
        final MessageCenterContentDelegate delegate = new MessageCenterContentDelegate();
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
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_message_center_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
        initAdapter();

        mDataConverter = new MessageCenterDataConverter();

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setEmptyView(mLoadingView);
                loadData();
            }
        },258);//因为当前Delegate没有动画，不走onEnterAnimationEnd，所以这里延时时间为动画时长
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    private void loadData() {

        mDataConverter.convertAsync(mType, new IConvertMessage() {
            @Override
            public void onCompleted(List<MulEntity> data) {
                mAdapter.setNewData(data);
            }
        });

    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //优化
        mRecyclerView.setHasFixedSize(true);
        //分割线
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
                        .color(Color.parseColor("#20ffffff"))
                        .margin(DimenUtil.dp2px(74), 0)
                        .build()
        );
        //取消adapter.notifyItemChanged()方法自带的默认动画效果
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private View mLoadingView = null;

    private void initAdapter() {
        final MessageCenterDelegate topDelegate = getParentDelegate();
        mAdapter = MessageCenterAdapter.create(new ArrayList<>(), null);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mLoadingView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.view_empty_loading, mRecyclerView, false);
        mAdapter.setOnItemClickListener(MessageCenterItemClickListener.create(topDelegate));
        mAdapter.setOnItemLongClickListener(MessageCenterItemLongClickListener.create(topDelegate));
    }

    @Override
    public void onDestroyView() {
        mDataConverter.quitSafely();
        super.onDestroyView();
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
