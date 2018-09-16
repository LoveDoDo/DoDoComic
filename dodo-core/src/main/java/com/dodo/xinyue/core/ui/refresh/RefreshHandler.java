package com.dodo.xinyue.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.net.RestClient;
import com.dodo.xinyue.core.net.callback.ISuccess;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.util.log.DoDoLogger;

/**
 * 刷新小助手
 *
 * @author DoDo
 * @date 2017/9/9
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MulAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        //设置监听
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        DoDo.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
                DoDoLogger.d("REFRESH", "刷新结束");
            }
        }, 2000);
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
//                        BEAN.setTotal(object.getInteger("total"))
//                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
//                        mAdapter = MulAdapter.create(CONVERTER.setJsonData(response));
//                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
//                        RECYCLERVIEW.setAdapter(mAdapter);
//                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
