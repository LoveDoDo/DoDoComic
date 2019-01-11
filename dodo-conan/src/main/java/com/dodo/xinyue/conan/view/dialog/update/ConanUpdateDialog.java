package com.dodo.xinyue.conan.view.dialog.update;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
 * ConanUpdateDialog
 *
 * @author DoDo
 * @date 2018/11/2
 */
public class ConanUpdateDialog extends BaseDialog {

    private final String mTitle;
    private final String mContent;
    private final String mVersionName;
    private final String mPackageSize;
    private final IConfirm mConfirm;

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.tvContent)
    TextView mTvContent = null;
    @BindView(R2.id.tvVersionName)
    TextView mTvVersionName = null;
    @BindView(R2.id.tvPackageSize)
    TextView mTvPackageSize = null;

    @BindView(R2.id.sv)
    ScrollView mScrollView = null;//方便动态设置ScrollView的高度

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

    public ConanUpdateDialog(DialogPublicParamsBean bean,
                             String title,
                             String content,
                             String versionName,
                             String packageSize,
                             IConfirm confirm) {
        super(bean);
        this.mTitle = title;
        this.mContent = content;
        this.mVersionName = versionName;
        this.mPackageSize = packageSize;
        this.mConfirm = confirm;
    }

    public static ConanUpdateDialogBuilder builder() {
        return new ConanUpdateDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_update;
    }

    @Override
    public void onBindView(View rootView) {
        mTvContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTvContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (mTvContent.getHeight() > DimenUtil.dp2px(130)) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mScrollView.getLayoutParams();
                    layoutParams.height = DimenUtil.dp2px(130);
                    mScrollView.setLayoutParams(layoutParams);
                }
            }
        });

        mTvTitle.setText(mTitle);
        mTvContent.setText(mContent);
        mTvVersionName.setText("版本：" + mVersionName);
        if (TextUtils.equals(mPackageSize, "-1")) {
            //已下载，直接安装
            mTvPackageSize.setText("已下载");
            mTvPackageSize.setTextColor(ContextCompat.getColor(getContext(), R.color.dodo_blue));
        } else {
            mTvPackageSize.setText("大小：" + mPackageSize);
        }

    }
}
