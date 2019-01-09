package com.dodo.xinyue.conan.main.index.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.main.index.bean.IndexBean;
import com.dodo.xinyue.conan.main.index.data.IndexItemType;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.image.GlideApp;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.ItemTypeBuilder;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulFields;
import com.dodo.xinyue.core.ui.recycler.MulHolder;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * IndexAdapter
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexAdapter extends MulAdapter {

    private static final RequestOptions DEFAULT_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

    @Override
    protected void loadImage(MulHolder holder, String imageUrl, @IdRes int viewId) {
        GlideApp.with(mContext)
                .load(imageUrl)
                .apply(DEFAULT_OPTIONS)
                .transition(new DrawableTransitionOptions().crossFade(88))//渐显 只有第一次加载有动画 内存加载无动画
                .into((ImageView) holder.getView(viewId));

    }

    private IndexAdapter(List<MulEntity> data, DoDoDelegate delegate) {
        super(data, delegate);
//        openLoadAnimation(new AlphaInAnimation());
    }

    public static IndexAdapter create(List<MulEntity> data, DoDoDelegate delegate) {
        return new IndexAdapter(data, delegate);
    }

    public static IndexAdapter create(DataConverter converter, DoDoDelegate delegate) {
        return new IndexAdapter(converter.convert(), delegate);
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(IndexItemType.COMIC_TEXT, R.layout.item_index_text)
                .addItemType(IndexItemType.COMIC_NUMBER, R.layout.item_index_number)
                .addItemType(IndexItemType.COMIC_IMAGE_TEXT, R.layout.item_index_image_text)
                .addItemType(IndexItemType.COMIC_GRID, R.layout.item_index_grid)
                .build();
    }

    @Override
    public void handle(MulHolder holder, MulEntity entity) {
        final IndexBean comicBean = entity.getField(MulFields.BEAN);
        final String number = comicBean.getNumber();
        final String title = comicBean.getTitle();
        final String cover = comicBean.getCover();

        switch (holder.getItemViewType()) {
            case IndexItemType.COMIC_TEXT:
                holder.setText(R.id.number, number)
                        .setText(R.id.title, title);
                holder.addOnClickListener(R.id.tvPreview);
                break;
            case IndexItemType.COMIC_NUMBER:
                holder.setText(R.id.number, number);
                break;
            case IndexItemType.COMIC_IMAGE_TEXT:
                holder.setText(R.id.number, "{icon-no}." + number)
                        .setText(R.id.title, title);
                loadImage(holder, cover, R.id.cover);
                holder.addOnClickListener(R.id.tvPreview);
                break;
            case IndexItemType.COMIC_GRID:
                holder.setText(R.id.number, "第" + number + "集")
                        .setText(R.id.title, title);
                loadImage(holder, cover, R.id.cover);
                holder.addOnClickListener(R.id.tvPreview);
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        final int fullSpanSize = gridLayoutManager.getSpanCount();
        final int itemSpanSize = super.getSpanSize(gridLayoutManager, position);

        final int itemType = getData().get(position).getField(MulFields.ITEM_TYPE);
        switch (itemType) {
            case IndexItemType.COMIC_TEXT:
            case IndexItemType.COMIC_IMAGE_TEXT:
                return fullSpanSize;
            case IndexItemType.COMIC_NUMBER:
                return 1;
            case IndexItemType.COMIC_GRID:
                return fullSpanSize / 2;
            default:
                break;
        }

        return itemSpanSize;
    }

}
