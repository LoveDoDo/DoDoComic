package com.dodo.xinyue.conan.module.thumb.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.conan.module.thumb.bean.ThumbItemType;
import com.dodo.xinyue.conan.module.thumb.bean.ThumbPreviewBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ThumbPreviewDataConverter
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ThumbPreviewDataConverter extends DataConverter {

    private static final int SPLIT_ROW = 10;//一张大图的行数
    private static final int SPLIT_COLUMN = 10;//一张大图的列数

    private List<String> mPreviewImgPaths = null;
    private int mImgNum = 0;

    private List<BitmapRegionDecoder> mBitmapRegionDecoder = new ArrayList<>();
    private BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();

    @Override
    public ArrayList<MulEntity> convert() {
        if (mPreviewImgPaths == null) {
            return ENTITIES;
        }
        final int size = mPreviewImgPaths.size();
        if (size <= 0) {
            return ENTITIES;
        }
        clearData();
        int imgNum = 0;
        mBitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.Options tempOptions = new BitmapFactory.Options();
        tempOptions.inJustDecodeBounds = true;//不加载Bitmap进内存,只取图片尺寸
        for (int i = 0; i < size; i++) {
            final String previewImgPath = mPreviewImgPaths.get(i);
            BitmapFactory.decodeFile(previewImgPath, tempOptions);
            int imgWidth = tempOptions.outWidth;
            int imgHeight = tempOptions.outHeight;
            int itemWidth = imgWidth / 10;
            int itemHeight = imgHeight / 10;
            BitmapRegionDecoder decoder;
            try {
                decoder = BitmapRegionDecoder.newInstance(previewImgPath, false);
                mBitmapRegionDecoder.add(decoder);
                for (int j = 0; j < SPLIT_ROW; j++) {
                    for (int k = 0; k < SPLIT_COLUMN; k++) {
                        if (imgNum >= mImgNum) {
                            return ENTITIES;
                        }
                        final Rect rect = new Rect();
                        rect.left = k * itemWidth;
                        rect.top = j * itemHeight;
                        rect.right = (k + 1) * itemWidth;
                        rect.bottom = (j + 1) * itemHeight;

                        final ThumbPreviewBean bean = new ThumbPreviewBean();
                        bean.setImgIndex(i);
                        bean.setRect(rect);
                        final MulEntity entity = MulEntity.builder()
                                .setItemType(ThumbItemType.THUMB_PREVIEW)
                                .setBean(bean)
                                .build();
                        ENTITIES.add(entity);
                        imgNum++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                clearData();
                final MulEntity entity = MulEntity.builder()
                        .setItemType(ThumbItemType.LOAD_DATA_ERROR)
                        .build();
                ENTITIES.add(entity);
                return ENTITIES;
            }
        }
        return ENTITIES;
    }

    public final void setImgNum(int imgNum) {
        this.mImgNum = imgNum;
    }

    public final void setmPreviewImgPaths(List<String> mPreviewImgPaths) {
        this.mPreviewImgPaths = mPreviewImgPaths;
    }

    /**
     * 获取decoder
     *
     * @param imgIndex 大图索引
     * @return
     */
    public final BitmapRegionDecoder getBitmapRegionDecoder(int imgIndex) {
        return mBitmapRegionDecoder.get(imgIndex);
    }

    /**
     * 获取options
     *
     * @return
     */
    public final BitmapFactory.Options getBitmapOptions() {
        return mBitmapOptions;
    }

}