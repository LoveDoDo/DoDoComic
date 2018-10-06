package com.dodo.xinyue.conan.main.index.view.dialog.list;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.main.index.view.dialog.BaseDialog;
import com.dodo.xinyue.conan.main.index.view.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.conan.main.index.view.dialog.callback.OnItemSelectedListener;
import com.dodo.xinyue.conan.main.index.view.dialog.list.adapter.ListDialogAdapter;
import com.dodo.xinyue.conan.main.index.view.dialog.list.bean.ListDialogBean;
import com.dodo.xinyue.conan.main.index.view.dialog.list.data.ListDialogDataConverter;
import com.dodo.xinyue.conan.main.index.view.dialog.list.listener.ListDialogItemClickListener;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * DoDoListDialog
 *
 * @author DoDo
 * @date 2018/10/4
 */
public class DoDoListDialog extends BaseDialog {

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.rv)
    RecyclerView mRecyclerView = null;

    @OnClick(R2.id.tvCancel)
    void onCancelClicked() {
        cancel();
    }

    private final String mTitle;
    private final List<ListDialogBean> mDataList;
    private final OnItemSelectedListener mOnItemSelectedListener;

    private ListDialogAdapter mAdapter = null;
    private ListDialogDataConverter mDataConverter = null;
    private GridLayoutManager mLayoutManager = null;

    public DoDoListDialog(DialogPublicParamsBean bean,
                          String title,
                          List<ListDialogBean> mDataList,
                          OnItemSelectedListener listener) {
        super(bean);
        this.mTitle = title;
        this.mDataList = mDataList;
        this.mOnItemSelectedListener = listener;
    }

    public static ListDialogBuilder builder() {
        return new ListDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.test_dialog_list;
    }

    @Override
    public void onBindView(View rootView) {
        mTvTitle.setText(mTitle);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //分割线
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext())
                        .color(Color.parseColor("#20ffffff"))
                        .margin(DimenUtil.dp2px(18), 0)
                        .build()
        );
        //优化
        mRecyclerView.setHasFixedSize(true);
        //取消adapter.notifyItemChanged()方法自带的默认动画效果
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mDataConverter = new ListDialogDataConverter();
        mDataConverter.setData(mDataList);
        mAdapter = ListDialogAdapter.create(mDataConverter.convert());

        mAdapter.bindToRecyclerView(mRecyclerView);
        //点击事件
        mAdapter.setOnItemClickListener(ListDialogItemClickListener.create(this, mOnItemSelectedListener));
    }
}
