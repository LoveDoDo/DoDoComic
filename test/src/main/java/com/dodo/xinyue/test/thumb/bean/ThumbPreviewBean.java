package com.dodo.xinyue.test.thumb.bean;

import android.graphics.Rect;

/**
 * ThumbPreviewBean
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ThumbPreviewBean {

    private int imgIndex = 0;//大图索引
    private Rect rect = null;//小图矩形区域

    public ThumbPreviewBean() {
    }

    public int getImgIndex() {
        return imgIndex;
    }

    public void setImgIndex(int imgIndex) {
        this.imgIndex = imgIndex;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}

