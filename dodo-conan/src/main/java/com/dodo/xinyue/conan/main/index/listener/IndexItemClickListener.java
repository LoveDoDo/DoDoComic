package com.dodo.xinyue.conan.main.index.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.main.index.bean.IndexBean;
import com.dodo.xinyue.conan.main.index.data.IndexItemType;
import com.dodo.xinyue.conan.module.thumb.ThumbPreviewDelegate;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulFields;

/**
 * ThumbPreviewItemClickListener
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexItemClickListener implements BaseQuickAdapter.OnItemClickListener {

    private final DoDoDelegate DELEGATE;

    public IndexItemClickListener(DoDoDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static IndexItemClickListener create(DoDoDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MulEntity entity = (MulEntity) adapter.getData().get(position);
        final IndexBean comicBean = entity.getField(MulFields.BEAN);
//        ToastUtils.showShort(comicBean.getTitle());

        //共享元素动画 只有返回动画，没有进入动画
//        final ThumbPreviewDelegate delegate = ThumbPreviewDelegate.create(comicBean);
//
//        DELEGATE.getParentDelegate().getParentDelegate().setExitTransition(new Fade());
//        delegate.setEnterTransition(new Fade());
//        delegate.setSharedElementReturnTransition(new DetailTransition());
//        delegate.setSharedElementEnterTransition(new DetailTransition());
//        adapter.getViewByPosition(position, R.id.title).setTransitionName(comicBean.getTitle());
//        DELEGATE.getParentDelegate().getParentDelegate().extraTransaction()
//                .addSharedElement(adapter.getViewByPosition(position, R.id.title), "test")
//                .start(delegate);

//        //临时动画
        DELEGATE.getParentDelegate().extraTransaction()
                .setCustomAnimations(R.anim.thumb_preview_enter, R.anim.index_content,R.anim.index_content,R.anim.thumb_preview_exit)
                .start(ThumbPreviewDelegate.create(comicBean));

        switch (adapter.getItemViewType(position)) {
            case IndexItemType.COMIC_TEXT:
//                final TextView tvNumber = (TextView) adapter.getViewByPosition(position, R.id.number);
//                YoYo.with(new RotationAnimator())
//                        .duration(400)
//                        .playOn(tvNumber);
//                final TextView tvTitle = (TextView) adapter.getViewByPosition(position, R.id.title);
//                YoYo.with(Techniques.Landing)
//                        .duration(400)
//                        .playOn(tvTitle);
                break;
            case IndexItemType.COMIC_NUMBER:


                break;
            case IndexItemType.COMIC_IMAGE_TEXT:

                break;
            case IndexItemType.COMIC_GRID:

                break;
            default:
                break;
        }


    }

}
