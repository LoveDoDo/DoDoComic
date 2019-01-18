package com.dodo.xinyue.conan.module.setting.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;
import com.dodo.xinyue.conan.view.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * MessageDelegate
 *
 * @author DoDo
 * @date 2018/10/30
 */
public class MessageDelegate extends BaseModuleDelegate {

    @BindView(R2.id.sbReceiveNewMessage)
    SwitchButton mSbReceiveNewMessage = null;

    @OnClick(R2.id.rlReceiveNewMessage)
    void onReceiveNewMessageClicked() {
        mSbReceiveNewMessage.toggle();
    }

    public static MessageDelegate create() {
        return new MessageDelegate();
    }

    @Override
    public Object setChildLayout() {
        return R.layout.delegate_message;
    }

    @Override
    public String setTitle() {
        return "消息设置";
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mSbReceiveNewMessage.setChecked(ApiHelper.isReceiveNewMessage());
        mSbReceiveNewMessage.setOnCheckedChangeListener((buttonView, isChecked) -> ApiHelper.setReceiveNewMessage(isChecked));
    }
}
