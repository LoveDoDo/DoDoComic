package com.dodo.xinyue.conan.main.index.view.dialog.custom;

import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.main.index.view.dialog.BaseDialog;
import com.dodo.xinyue.conan.main.index.view.dialog.bean.DialogPublicParamsBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ConanDialog
 *
 * @author DoDo
 * @date 2018/10/5
 */
public class ConanDialog extends BaseDialog {

    private final String mTitle;

    @BindView(R2.id.title)
    TextView mTvTitle = null;

    @OnClick(R2.id.tvConfirm)
    void onConfirmClicked() {
        dismiss();
    }


    public ConanDialog(DialogPublicParamsBean bean, String mTitle) {
        super(bean);
        this.mTitle = mTitle;
    }

    public static CustomImageDialogBuilder builder() {

        return new CustomImageDialogBuilder();

    }

    @Override
    public Object setLayout() {
        return R.layout.test_dialog_custom;
    }

    @Override
    public void onBindView(View rootView) {
        mTvTitle.setText(mTitle);
    }
}
