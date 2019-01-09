package com.dodo.xinyue.conan.main.manhua;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;

/**
 * ManhuaDelegate
 *
 * @author DoDo
 * @date 2018/10/2
 */
public class ManhuaDelegate extends BaseBottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_manhua;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
