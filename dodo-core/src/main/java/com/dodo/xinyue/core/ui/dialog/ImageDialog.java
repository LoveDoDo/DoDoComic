package com.dodo.xinyue.core.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ImageDialog
 *
 * @author DoDo
 * @date 2018/1/18
 */
public class ImageDialog extends BaseDialog {

    private String mGiftContent = null;

    @BindView(R2.id.btn_sign)
    TextView mTvSign = null;
    @BindView(R2.id.gift)
    TextView mGift = null;

    @OnClick(R2.id.btn_sign)
    void onSignClicked(View view) {
        cancel();
    }

    public ImageDialog(Context context) {
        super(context);
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_image;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        mGift.setText(mGiftContent);
    }

    public ImageDialog giftContent(String content) {
        mGiftContent = content;
        return this;
    }
}
