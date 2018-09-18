package com.dodo.xinyue.test.thumb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.ui.image.GlideApp;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.ItemType;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.test.thumb.bean.ThumbPreviewBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * ThumbPreviewDataConverter
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ThumbPreviewDataConverter extends DataConverter {

    private List<String> mUrls = new ArrayList<>();
    private TestListener mListener = null;

    private RequestOptions DEFAULT_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
//                    .transform(new RoundedCorners(6))//圆角 设置后会导致GIF丢帧
//                    .dontAnimate();//设置后会导致GIF无法播放

    @Override
    public ArrayList<MulEntity> convert() {
        final String url = mUrls.get(0);
        new Thread(() -> {
            FutureTarget<File> submit = GlideApp.with(DoDo.getAppContext())
                    .applyDefaultRequestOptions(DEFAULT_OPTIONS)
                    .download(url)
                    .submit();
            try {
                File file = submit.get();
                String filePath = file.getAbsolutePath();
                BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
                tmpOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, tmpOptions);
                int width = tmpOptions.outWidth;
                int height = tmpOptions.outHeight;

                int itemWidth = width / 10;
                int itemHeight = height / 10;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(filePath, false);
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {

                        final Rect rect = new Rect();
                        rect.left = j * itemWidth;
                        rect.top = i * itemHeight;
                        rect.right = (j + 1) * itemWidth;
                        rect.bottom = (i + 1) * itemHeight;

                        final ThumbPreviewBean bean = new ThumbPreviewBean();
                        bean.setDecoder(decoder);
                        bean.setOptions(options);
                        bean.setRect(rect);
                        final MulEntity entity = MulEntity.builder()
                                .setItemType(ItemType.IMAGE)
                                .setBean(bean)
                                .build();
                        ENTITIES.add(entity);
                        mListener.done();
                    }
                }
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
                final MulEntity entity = MulEntity.builder()
                        .setItemType(ItemType.TEXT)
                        .build();
                ENTITIES.add(entity);
                mListener.done();
            }

        }).start();

        return ENTITIES;
    }

    public ThumbPreviewDataConverter setImageUrls(List<String> urls) {
        mUrls = urls;
        return this;
    }

    public interface TestListener{
        void done();
    }

    public void setListener(TestListener listener) {
        mListener = listener;
    }

    public List<MulEntity> getData() {
        return ENTITIES;
    }
}
