package com.dodo.xinyue.conan.module.thumb.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.module.thumb.ThumbPreviewDelegate;
import com.dodo.xinyue.conan.module.thumb.bean.ThumbItemType;
import com.dodo.xinyue.conan.module.thumb.bean.ThumbPreviewBean;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.ItemTypeBuilder;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulHolder;

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

    public static ThumbPreviewAdapter create(List<MulEntity> data, ThumbPreviewDelegate delegate) {
        return new ThumbPreviewAdapter(data, delegate);
    }

    public static ThumbPreviewAdapter create(DataConverter converter, ThumbPreviewDelegate delegate) {
        return new ThumbPreviewAdapter(converter.convert(), delegate);
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(ThumbItemType.THUMB_PREVIEW, R.layout.item_thumb_preview)
                .addItemType(ThumbItemType.LOAD_DATA_ERROR, R.layout.item_thumb_preview_error)
                .build();
    }

    @Override
    public void handle(MulHolder holder, MulEntity entity) {
        switch (holder.getItemViewType()) {
            case ThumbItemType.THUMB_PREVIEW:
                setThumbPreview(holder, entity);
                break;
            default:
                break;
        }
    }

    private void setThumbPreview(MulHolder holder, MulEntity entity) {
        final ThumbPreviewBean bean = entity.getBean();
        final int imgIndex = bean.getImgIndex();
        final Rect rect = bean.getRect();

        BitmapRegionDecoder decoder = ((ThumbPreviewDelegate) DELEGATE).getBitmapRegionDecoder(imgIndex);
        BitmapFactory.Options options = ((ThumbPreviewDelegate) DELEGATE).getBitmapOptions();

        final Bitmap bitmap = decoder.decodeRegion(rect, options);
        AppCompatImageView imageView = holder.getView(R.id.iv);
        imageView.setImageBitmap(bitmap);

    }
}
