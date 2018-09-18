package com.dodo.xinyue.test.thumb.bean;

import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

/**
 * ThumbPreviewBean
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ThumbPreviewBean {

    private BitmapRegionDecoder decoder = null;
    private BitmapFactory.Options options = null;
    private Rect rect = null;

    public ThumbPreviewBean() {
    }

    public BitmapRegionDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(BitmapRegionDecoder decoder) {
        this.decoder = decoder;
    }

    public BitmapFactory.Options getOptions() {
        return options;
    }

    public void setOptions(BitmapFactory.Options options) {
        this.options = options;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
