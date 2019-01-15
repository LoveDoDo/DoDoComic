package com.dodo.xinyue.conan.view.dialog.message;

import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

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

    @BindView(R2.id.sv)
    ScrollView mScrollView = null;//方便动态设置ScrollView的高度 TODO 子布局不能设置android:layout_gravity="center"，否则底部会出现空白，顶部显示不全

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

        mTvTitle.setText(mTitle);
        if (mContent.contains("\n")) {
            mTvContent.setGravity(Gravity.START);
        }
        mTvContent.setText(mContent);
        mScrollView.invalidate();
    }
}
