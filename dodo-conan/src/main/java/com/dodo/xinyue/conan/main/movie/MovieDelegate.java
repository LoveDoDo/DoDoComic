package com.dodo.xinyue.conan.main.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;

/**
 * MovieDelegate
 *
 * @author DoDo
 * @date 2018/10/2
 */
public class MovieDelegate extends BaseBottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_movie;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
