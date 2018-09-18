package com.dodo.xinyue.test.thumb.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;

import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.ItemType;
import com.dodo.xinyue.core.ui.recycler.ItemTypeBuilder;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulHolder;
import com.dodo.xinyue.test.R;
import com.dodo.xinyue.test.thumb.bean.ThumbPreviewBean;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * ThumbPreviewAdapter
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ThumbPreviewAdapter extends MulAdapter {

    protected ThumbPreviewAdapter(List<MulEntity> data, DoDoDelegate delegate) {
        super(data, delegate);
    }

    public static ThumbPreviewAdapter create(List<MulEntity> data, DoDoDelegate delegate) {
        return new ThumbPreviewAdapter(data, delegate);
    }

    public static ThumbPreviewAdapter create(DataConverter converter, DoDoDelegate delegate) {
        return new ThumbPreviewAdapter(converter.convert(), delegate);
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(ItemType.IMAGE, R.layout.item_thumb_preview)
                .addItemType(ItemType.TEXT, R.layout.item_error)
                .build();
    }

    @Override
    public void handle(MulHolder holder, MulEntity entity) {
        switch (holder.getItemViewType()) {
            case ItemType.IMAGE:
                setThumbPreview(holder, entity);
                break;
            default:
                break;
        }
    }

    private void setThumbPreview(MulHolder holder, MulEntity entity) {
        final ThumbPreviewBean bean = entity.getBean();
        final BitmapRegionDecoder decoder = bean.getDecoder();
        final BitmapFactory.Options options = bean.getOptions();
        final Rect rect = bean.getRect();
        final Bitmap bitmap = decoder.decodeRegion(rect, options);
        AppCompatImageView imageView = holder.getView(R.id.iv);
        imageView.setImageBitmap(bitmap);
    }
}
