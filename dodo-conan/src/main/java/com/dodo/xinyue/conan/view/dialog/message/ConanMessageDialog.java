package com.dodo.xinyue.conan.view.dialog.message;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ConanMessageDialog
 *
 * @author DoDo
 * @date 2018/11/2
 */
public class ConanMessageDialog extends BaseDialog {

    private final String mTitle;
    private final String mContent;

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.tvContent)
    TextView mTvContent = null;

    @OnClick(R2.id.tvCancel)
    void onTvCancelClicked() {
        cancel();
    }

    public ConanMessageDialog(DialogPublicParamsBean bean,
                              String title,
                              String content) {
        super(bean);
        this.mTitle = title;
        this.mContent = content;
    }

    public static ConanMessageDialogBuilder builder() {
        return new ConanMessageDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_message;
    }

    @Override
    public void onBindView(View rootView) {
        mTvTitle.setText(mTitle);
        if (mContent.contains("\n")) {
            mTvContent.setGravity(Gravity.START);
        }
        mTvContent.setText(mContent);
    }
}
