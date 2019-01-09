package com.dodo.xinyue.conan.view.dialog.normal;

import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.view.dialog.callback.IConfirm;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ConanNormalDialog
 *
 * @author DoDo
 * @date 2018/10/28
 */
public class ConanNormalDialog extends BaseDialog {

    private final String mTitle;
    private final String mContent;
    private final IConfirm mConfirm;

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.tvContent)
    TextView mTvContent = null;

    @OnClick(R2.id.tvCancel)
    void onTvCancelClicked() {
        cancel();
    }

    @OnClick(R2.id.tvConfirm)
    void onTvConfirmClicked() {
        cancel();
        if (mConfirm != null) {
            mConfirm.onConfirm();
        }
    }

    public ConanNormalDialog(DialogPublicParamsBean bean,
                             String title,
                             String content,
                             IConfirm confirm) {
        super(bean);
        this.mTitle = title;
        this.mContent = content;
        this.mConfirm = confirm;
    }

    public static ConanNormalDialogBuilder builder() {
        return new ConanNormalDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_setting;
    }

    @Override
    public void onBindView(View rootView) {
        mTvTitle.setText(mTitle);
        mTvContent.setText(mContent);
    }
}
