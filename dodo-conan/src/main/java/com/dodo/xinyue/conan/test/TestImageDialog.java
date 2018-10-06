package com.dodo.xinyue.conan.test;

import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * TestImageDialog
 *
 * @author DoDo
 * @date 2018/10/6
 */
public class TestImageDialog extends BaseDialog {

    private String mTitle = null;

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;

    @OnClick(R2.id.tvConfirm)
    void onTvConfirmClicked() {
        cancel();
    }

    public TestImageDialog(DialogPublicParamsBean bean,
                           String title) {
        super(bean);
        this.mTitle = title;
    }

    public static TestImageDialogBuilder builder() {
        return new TestImageDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.layout_image_dialog;
    }

    @Override
    public void onBindView(View rootView) {
        mTvTitle.setText(mTitle);
    }
}
