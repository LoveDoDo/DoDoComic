package com.dodo.xinyue.core.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.R2;
import com.dodo.xinyue.core.util.log.DoDoLogger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * NormalDialog
 *
 * @author DoDo
 * @date 2018/1/17
 */
public class NormalDialog extends BaseDialog {

    private String mTitle = null;
    private String mContent = null;

    @BindView(R2.id.title)
    TextView tvTitle;
    @BindView(R2.id.content)
    TextView tvContent;

    @OnClick(R2.id.title)
    void onTitleClicked(View view) {
        ToastUtils.showShort("title");
    }

    @OnClick(R2.id.content)
    void onContentClicked(View view) {
        ToastUtils.showShort("content");
    }

    public NormalDialog(Context context) {
        super(context);
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_normal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        tvTitle.setText(mTitle);
        tvContent.setText(mContent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DoDoLogger.d("hahah", "onCreate()");

    }

    public NormalDialog title(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle = title;
        }
        return this;
    }

    public NormalDialog content(String content) {
        if (!TextUtils.isEmpty(content)) {
            mContent = content;
        }
        return this;
    }
}
