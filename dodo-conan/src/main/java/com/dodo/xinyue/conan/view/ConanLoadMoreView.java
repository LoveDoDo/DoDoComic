package com.dodo.xinyue.conan.view;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.dodo.xinyue.conan.R;

/**
 * ConanLoadMoreView
 *
 * @author DoDo
 * @date 2019/1/21
 */
public class ConanLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_end;
    }

}
