package com.dodo.xinyue.conan.view.dialog.message;

import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialogBuilder;

/**
 * ConanMessageDialogBuilder
 *
 * @author DoDo
 * @date 2018/11/2
 */
public class ConanMessageDialogBuilder extends BaseDialogBuilder<ConanMessageDialogBuilder,ConanMessageDialog> {

    private String mTitle = null;
    private String mContent = null;
    private boolean mIsStart = false;//是否文本左对齐
    private boolean mIsHtml = false;//是否是html形式(改变文本颜色)
    private int mAction = JiGuangMessage.ACTION_NONE;//点击操作
    private String mCopyContent = null;//复制内容
    private String mCopyTips = null;//复制成功提示

    public final ConanMessageDialogBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final ConanMessageDialogBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public final ConanMessageDialogBuilder isStart(boolean isStart) {
        this.mIsStart = isStart;
        return this;
    }

    public final ConanMessageDialogBuilder isHtml(boolean isHtml) {
        this.mIsHtml = isHtml;
        return this;
    }

    public final ConanMessageDialogBuilder action(int action) {
        this.mAction = action;
        return this;
    }

    public final ConanMessageDialogBuilder copyContent(String copyContent) {
        this.mCopyContent = copyContent;
        return this;
    }

    public final ConanMessageDialogBuilder copyTips(String copyTips) {
        this.mCopyTips = copyTips;
        return this;
    }

    @Override
    public ConanMessageDialog build() {
        return new ConanMessageDialog(
                getDialogPublicParamsBean(),
                mTitle,
                mContent,
                mIsStart,
                mIsHtml,
                mAction,
                mCopyContent,
                mCopyTips
        );
    }
}
