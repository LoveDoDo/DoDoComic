package com.dodo.xinyue.conan.module.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

/**
 * SearchDelegate
 *
 * @author DoDo
 * @date 2018/10/7
 */
public class SearchDelegate extends DoDoDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }
}
