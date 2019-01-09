package com.dodo.xinyue.conan.main.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.main.mine.adapter.MineAdapter;
import com.dodo.xinyue.conan.main.mine.bean.MineBean;
import com.dodo.xinyue.conan.main.mine.bean.MineItemType;
import com.dodo.xinyue.conan.main.mine.event.NewMessageEvent;
import com.dodo.xinyue.conan.main.mine.listener.MineItemClickListener;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * MineDelegate
 *
 * @author DoDo
 * @date 2018/10/2
 */
public class MineDelegate extends BaseBottomItemDelegate {

    private MulAdapter mAdapter = null;
    private GridLayoutManager mLayoutManager = null;

    @BindView(R2.id.vRedDot)
    View mRedDot = null;

    @BindView(R2.id.rv)
    RecyclerView mRecyclerView = null;

    @OnClick(R2.id.rlMessage)
    void onMessageClicked() {
        mRedDot.setVisibility(View.GONE);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_mine;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //优化
        mRecyclerView.setHasFixedSize(true);
        //分割线
//        mRecyclerView.addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(mRecyclerView.getContext())
//                        .color(Color.parseColor("#20ffffff"))
//                        .size(DimenUtil.dp2px(1))
//                        .showLastDivider()
//                        .build()
//        );
//        mRecyclerView.addItemDecoration(
//                new VerticalDividerItemDecoration.Builder(mRecyclerView.getContext())
//                        .color(Color.parseColor("#10ffffff"))
//                        .size(DimenUtil.dp2px(1))
//                        .showLastDivider()
//                        .build()
//        );
        RecyclerViewDivider.with(mRecyclerView.getContext())
                .color(Color.parseColor("#20ffffff"))
                .size(DimenUtil.dp2px(1))
                .build()
                .addTo(mRecyclerView);
        //取消adapter.notifyItemChanged()方法自带的默认动画效果
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        handleData();

    }

    private void handleData() {
        final ArrayList<MineBean> beans = new ArrayList<>();
        beans.add(new MineBean("{icon-tuili}", "每日推理"));
        beans.add(new MineBean("{icon-joke}", "开心一刻"));
        beans.add(new MineBean("{icon-key}", "寻宝"));
        beans.add(new MineBean("{icon-tieba}", "柯南吧"));
        beans.add(new MineBean("{icon-tieba}", "DoDo动漫吧"));
        beans.add(new MineBean("{icon-money}", "赞助"));
//        beans.add(new MineBean("{icon-progress spin}", "面试"));
        final ArrayList<MulEntity> data = new ArrayList<>();
        final int size = beans.size();
        for (int i = 0; i < size; i++) {
            final MulEntity entity = MulEntity.builder()
                    .setItemType(MineItemType.ITEM)
                    .setSpanSize(1)
                    .setBean(beans.get(i))
                    .build();
            data.add(entity);
        }
        //占位
        final int yushu = size % 3;
        if (yushu != 0) {
            for (int i = 0; i < 3 - yushu; i++) {
                final MulEntity entity = MulEntity.builder()
                        .setItemType(MineItemType.NONE)
                        .setSpanSize(1)
                        .build();
                data.add(entity);
            }
        }
        mAdapter = MineAdapter.create(data, this);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener(MineItemClickListener.create(this));
    }

    /**
     * 新消息红点提醒
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNewMessageEvent(NewMessageEvent event) {
        EventBusActivityScope.getDefault(DoDo.getActivity()).removeStickyEvent(event);
        mRedDot.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusActivityScope.getDefault(DoDo.getActivity()).register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusActivityScope.getDefault(DoDo.getActivity()).unregister(this);
    }

}
