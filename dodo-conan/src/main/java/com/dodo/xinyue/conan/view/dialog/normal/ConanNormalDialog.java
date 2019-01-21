package com.dodo.xinyue.conan.view.dialog.normal;

import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.view.dialog.callback.IConfirm;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

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
    private final boolean mOnlyOneButton;
    private final boolean mIsHtml;

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.tvContent)
    TextView mTvContent = null;

    @BindView(R2.id.llTwoButton)
    LinearLayout mLLTwoButton = null;
    @BindView(R2.id.tvConfirm2)
    TextView mTvConfirm2 = null;

    @BindView(R2.id.sv)
    ScrollView mScrollView = null;

    @OnClick(R2.id.tvCancel)
    void onTvCancelClicked() {
        cancel();
    }

    @OnClick({R2.id.tvConfirm, R2.id.tvConfirm2})
    void onTvConfirmClicked() {
        cancel();
        if (mConfirm != null) {
            mConfirm.onConfirm();
        }
    }

    public ConanNormalDialog(DialogPublicParamsBean bean,
                             String title,
                             String content,
                             IConfirm confirm,
                             boolean onlyOneButton,
                             boolean isHtml) {
        super(bean);
        this.mTitle = title;
        this.mContent = content;
        this.mConfirm = confirm;
        this.mOnlyOneButton = onlyOneButton;
        this.mIsHtml = isHtml;
    }

    public static ConanNormalDialogBuilder builder() {
        return new ConanNormalDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_normal;
    }

    @Override
    public void onBindView(View rootView) {
        mTvContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTvContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (mTvContent.getHeight() > DimenUtil.dp2px(188)) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mScrollView.getLayoutParams();
                    layoutParams.height = DimenUtil.dp2px(188);
                    mScrollView.setLayoutParams(layoutParams);
                }
            }
        });

        if (mOnlyOneButton) {
            mLLTwoButton.setVisibility(View.GONE);
            mTvConfirm2.setVisibility(View.VISIBLE);
        }
        mTvTitle.setText(mTitle);
        if (!mIsHtml) {
            mTvContent.setText(mContent);
        } else {
            //解决不能换行的问题
            mTvContent.setText(Html.fromHtml(mContent.replace("\n", "<br>")));//例如："这是<font color=#ff0000>红色</font>,这是<font color=#0000ff>蓝色</font>"
        }
    }
}
