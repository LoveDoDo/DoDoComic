package com.dodo.xinyue.conan.main.index.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.main.index.bean.IndexBean;
import com.dodo.xinyue.conan.module.thumb.ThumbPreviewDelegate;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulFields;
import com.dodo.xinyue.core.ui.recycler.MulItemChildClickListener;

/**
 * ThumbPreviewItemClickListener
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexItemChildClickListener extends MulItemChildClickListener {

    private IndexItemChildClickListener(DoDoDelegate delegate) {
        super(delegate);
    }

    public static IndexItemChildClickListener create(DoDoDelegate delegate) {
        return new IndexItemChildClickListener(delegate);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int viewId) {
        if (viewId == R.id.tvPreview) {
            final IndexBean comicBean = entity.getField(MulFields.BEAN);
            //临时动画
            DELEGATE.getParentDelegate().extraTransaction()
                    .setCustomAnimations(R.anim.thumb_preview_enter, R.anim.index_content, R.anim.index_content, R.anim.thumb_preview_exit)
                    .start(ThumbPreviewDelegate.create(comicBean));
        }
    }
}
