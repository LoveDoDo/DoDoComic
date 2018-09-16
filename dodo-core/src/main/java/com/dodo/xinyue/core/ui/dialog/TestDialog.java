package com.dodo.xinyue.core.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by YuanJun on 2018/1/12 16:15
 */

/**
 * TestDialog
 *
 * @author DoDo
 * @date 2018/1/12
 */
public class TestDialog extends BaseDialog {

    @BindView(R2.id.tv_cancel)
    TextView mTvCancel = null;
    @BindView(R2.id.tv_exit)
    TextView mTvExit = null;

    @OnClick(R2.id.tv_cancel)
    void onCancelClicked(View view) {
        ToastUtils.showShort("cancel");
    }

    @OnClick(R2.id.tv_exit)
    void onExitClicked(View view) {
        ToastUtils.showShort("exit");
    }

    public TestDialog(Context context) {
        super(context);
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_custom;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
