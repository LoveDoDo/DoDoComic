package com.dodo.xinyue.conan.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.ConanDelegate;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;

import butterknife.OnClick;

/**
 * 主页
 *
 * @author DoDo
 * @date 2018/9/26
 */
public class IndexDelegate extends BaseBottomItemDelegate {

    @OnClick(R2.id.tvIndex)
    void onTvIndexClicked() {
        getParentDelegate().start(ConanDelegate.create());
    }

    public static IndexDelegate create() {
        return new IndexDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

}
