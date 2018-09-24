package com.dodo.xinyue.test.thumb.data;

import android.os.Handler;
import android.text.TextUtils;

/**
 * ThumbHandlerThreadBuilder
 *
 * @author DoDo
 * @date 2018/9/23
 */
public class ThumbHandlerThreadBuilder {

    private int mTotleTime = 0;
    private String mPreviewImgUrl = null;
    private Handler mUIHandler = null;

    ThumbHandlerThreadBuilder() {
    }

    public final ThumbHandlerThreadBuilder totleTime(int totleTime) {
        this.mTotleTime = totleTime;
        return this;
    }

    public final ThumbHandlerThreadBuilder previewImgUrl(String previewImgUrl) {
        this.mPreviewImgUrl = previewImgUrl;
        return this;
    }

    public final ThumbHandlerThreadBuilder handler(Handler uiHandler) {
        this.mUIHandler = uiHandler;
        return this;
    }

    public final ThumbHandlerThread build() {
        if (mTotleTime <= 0) {
            throw new IllegalArgumentException("mTotleTime不合法!");
        }
        if (TextUtils.isEmpty(mPreviewImgUrl)) {
            throw new IllegalArgumentException("PreviewImgUrl不合法!");
        }
        if (mUIHandler == null) {
            throw new IllegalArgumentException("UIHandler不合法!");
        }
        return new ThumbHandlerThread(
                mTotleTime,
                mPreviewImgUrl,
                mUIHandler
        );
    }

}
