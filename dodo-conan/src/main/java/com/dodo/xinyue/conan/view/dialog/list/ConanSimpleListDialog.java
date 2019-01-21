package com.dodo.xinyue.conan.view.dialog.list;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.view.dialog.list.adapter.ConanSimpleListDialogAdapter;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.ui.dialog.callback.OnItemSelectedListener;
import com.dodo.xinyue.core.ui.dialog.list.bean.ListDialogBean;
import com.dodo.xinyue.core.ui.dialog.list.data.ListDialogDataConverter;
import com.dodo.xinyue.core.ui.dialog.list.listener.ListDialogItemClickListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * ConanSimpleListDialog
 *
 * @author DoDo
 * @date 2019/1/21
 */
public class ConanSimpleListDialog extends BaseDialog {

    @BindView(R2.id.rv)
    RecyclerView mRecyclerView = null;

    private final List<ListDialogBean> mDataList;
    private final OnItemSelectedListener mOnItemSelectedListener;

    private ConanSimpleListDialogAdapter mAdapter = null;
    private ListDialogDataConverter mDataConverter = null;
    private GridLayoutManager mLayoutManager = null;

    public ConanSimpleListDialog(DialogPublicParamsBean bean,
                                 List<ListDialogBean> dataList,
                                 OnItemSelectedListener listener) {
        super(bean);
        this.mDataList = dataList;
        this.mOnItemSelectedListener = listener;
    }

    public static ConanSimpleListDialogBuilder builder() {
        return new ConanSimpleListDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_conan_simple_list;
    }

    @Override
    public void onBindView(View rootView) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //分割线
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext())
                        .color(Color.parseColor("#20ffffff"))
                        .build()
        );
        //优化
        mRecyclerView.setHasFixedSize(true);
        //取消adapter.notifyItemChanged()方法自带的默认动画效果
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mDataConverter = new ListDialogDataConverter();
        mDataConverter.setData(mDataList);
        mAdapter = ConanSimpleListDialogAdapter.create(mDataConverter.convert());

        mAdapter.bindToRecyclerView(mRecyclerView);
        //点击事件
        mAdapter.setOnItemClickListener(ListDialogItemClickListener.create(this, mOnItemSelectedListener));
    }

}
