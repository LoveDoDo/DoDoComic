package com.dodo.xinyue.conan.main.index.adapter;

import android.support.v7.widget.GridLayoutManager;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.main.index.bean.IndexBean;
import com.dodo.xinyue.conan.main.index.data.IndexItemType;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
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

    private final DoDoDelegate DELEGATE;

    protected IndexAdapter(List<MulEntity> data, DoDoDelegate delegate) {
        super(data,null);
        this.DELEGATE = delegate;
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
                break;
            case IndexItemType.COMIC_NUMBER:
                holder.setText(R.id.number, number);
                break;
            case IndexItemType.COMIC_IMAGE_TEXT:
                holder.setText(R.id.number, number)
                        .setText(R.id.title, title);
                loadImage(holder, cover, R.id.cover);
                break;
            case IndexItemType.COMIC_GRID:
                holder.setText(R.id.number, "第" + number + "集")
                        .setText(R.id.title, title);
                loadImage(holder, cover, R.id.cover);
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
