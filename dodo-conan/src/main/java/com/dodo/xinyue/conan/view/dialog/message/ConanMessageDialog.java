package com.dodo.xinyue.conan.view.dialog.message;

import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.util.CommonUtil;
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
    private final boolean mIsStart;
    private final boolean mIsHtml;
    private final int mAction;
    private final String mCopyContent;
    private final String mCopyTips;

    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.tvContent)
    TextView mTvContent = null;
    @BindView(R2.id.tvConfirm)
    TextView mTvConfirm = null;

    @BindView(R2.id.sv)
    ScrollView mScrollView = null;//方便动态设置ScrollView的高度 TODO 子布局不能设置android:layout_gravity="center"，否则底部会出现空白，顶部显示不全

    @OnClick(R2.id.tvConfirm)
    void onTvConfirmClicked() {
        switch (mAction) {
            case JiGuangMessage.ACTION_COPY:
                if (CommonUtil.copyToClipboard(!TextUtils.isEmpty(mCopyContent) ? mCopyContent.replace("\\n","\n") : mContent)) {
                    ToastUtils.showShort(!TextUtils.isEmpty(mCopyTips) ? mCopyTips : "复制成功");
                } else {
                    ToastUtils.showShort("复制失败");
                }
                break;
            default:
                break;
        }
        cancel();
    }

    public ConanMessageDialog(DialogPublicParamsBean bean,
                              String title,
                              String content,
                              boolean isStart,
                              boolean isHtml,
                              int action,
                              String copyContent,
                              String copyTips) {
        super(bean);
        this.mTitle = title;
        this.mContent = content;
        this.mIsStart = isStart;
        this.mIsHtml = isHtml;
        this.mAction = action;
        this.mCopyContent = copyContent;
        this.mCopyTips = copyTips;

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
        if (mIsStart) {
            mTvContent.setGravity(Gravity.START);
        }
        if (!mIsHtml) {
            mTvContent.setText(mContent);
        } else {
            //解决不能换行的问题
            mTvContent.setText(Html.fromHtml(mContent.replace("\n","<br>")));//例如："这是<font color=#ff0000>红色</font>,这是<font color=#0000ff>蓝色</font>"
        }
        if (mAction == JiGuangMessage.ACTION_COPY) {
            mTvConfirm.setText("复制");
        }
    }
}
