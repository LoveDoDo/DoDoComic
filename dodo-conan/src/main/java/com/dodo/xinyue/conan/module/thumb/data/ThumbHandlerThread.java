package com.dodo.xinyue.conan.module.thumb.data;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.bumptech.glide.request.FutureTarget;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.ui.image.GlideApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ThumbHandlerThread
 *
 * @author DoDo
 * @date 2018/9/23
 */
public class ThumbHandlerThread extends HandlerThread implements Handler.Callback {

    public static final String KEY_PREVIEW_URL = "key_preview_url";
    public static final String KEY_PREVIEW_PATHS = "key_preview_paths";
    public static final String KEY_IMG_NUM = "key_img_num";
    private static final String KEY_IS_DONE = "key_is_done";
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_FAILURE = 1;

    private Handler mWorkerHandler = null;

    private int mTotleTime = 0;
    private String mPreviewImgUrl = null;
    private Handler mUIHandler = null;

    private int mInterval = 10;//每张图片间隔时间(秒)
    private int mUnitImgNum = 100;//每张大图包含的小图数
    private int mSmallImgWidth = 160;//小图宽
    private int mSmallImgHeight = 90;//小图高

    private final List<String> mPreviewImgUrls = new ArrayList<>();
    private final ArrayList<String> mPreviewImgPaths = new ArrayList<>();

    private ThumbHandlerThread(String name) {
        super(name);
    }

    ThumbHandlerThread(
            int totleTime,
            String previewImgUrl,
            Handler uiHandler
    ) {
        this("ThumbHandlerThread");
        this.mTotleTime = totleTime;
        this.mPreviewImgUrl = previewImgUrl;
        this.mUIHandler = uiHandler;
    }

    public static ThumbHandlerThreadBuilder builder() {
        return new ThumbHandlerThreadBuilder();
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkerHandler = new Handler(getLooper(), this);
        final int smallImgNum = computeSmallImgNum();
        final int largeImgNum = computeLargeImgNum(smallImgNum);
        //预览图url前缀
        final String prefixUrl = mPreviewImgUrl.substring(0, mPreviewImgUrl.lastIndexOf("."));
        //预览图url后缀
        final String suffixUrl = mPreviewImgUrl.substring(mPreviewImgUrl.lastIndexOf(".") + 1);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < largeImgNum; i++) {
            sb.delete(0, sb.length());
            sb.append(prefixUrl);
            sb.append("_");
            sb.append(mSmallImgWidth);
            sb.append("_");
            sb.append(mSmallImgHeight);
            sb.append("_");
            sb.append(i + 1);
            sb.append(".");
            sb.append(suffixUrl);
            final String previewImgUrl = sb.toString();
            mPreviewImgUrls.add(previewImgUrl);
        }
        enqueue();
    }

    /**
     * 入列
     * <p>
     * 依次添加到WorkerHandler消息队列中
     */
    private void enqueue() {
        final int size = mPreviewImgUrls.size();
        for (int i = 0; i < size; i++) {
            final String previewImgUrl = mPreviewImgUrls.get(i);
            final Message msg = mWorkerHandler.obtainMessage();
            final Bundle data = new Bundle();
            data.putString(KEY_PREVIEW_URL, previewImgUrl);
            if (i == size - 1) {
                data.putBoolean(KEY_IS_DONE, true);
            } else {
                data.putBoolean(KEY_IS_DONE, false);
            }
            msg.setData(data);
            mWorkerHandler.sendMessage(msg);
        }
    }

    /**
     * 重试(失败重试)
     */
    public final void retry() {
        mPreviewImgPaths.clear();
        enqueue();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg == null) {
            return false;
        }
        final Bundle args = msg.getData();
        if (args == null) {
            return false;
        }

        final String previewImgUrl = args.getString(KEY_PREVIEW_URL);
        final boolean isDone = args.getBoolean(KEY_IS_DONE);

        FutureTarget<File> previewImg =
                GlideApp.with(DoDo.getAppContext())
                        .download(previewImgUrl)
                        .submit();//submit本身是异步的,但返回值previewImg.get()方法是阻塞的

        try {
            File previewImgFile = previewImg.get();
            String previewImgPath = previewImgFile.getAbsolutePath();
            mPreviewImgPaths.add(previewImgPath);
            if (isDone) {
                sendSuccessMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage();
        }
        return true;
    }

    private void sendSuccessMessage() {
        final Message successMsg = mUIHandler.obtainMessage(STATUS_SUCCESS);
        final Bundle args = new Bundle();
        args.putStringArrayList(KEY_PREVIEW_PATHS, mPreviewImgPaths);
        args.putInt(KEY_IMG_NUM, computeSmallImgNum());
        successMsg.setData(args);
        mUIHandler.sendMessage(successMsg);
    }

    private void sendFailureMessage() {
        mUIHandler.sendEmptyMessage(STATUS_FAILURE);
    }

    /**
     * 计算总图片数(大图)
     */
    private int computeLargeImgNum(int smallImgNum) {
        final int largeImgNum;
        final int shang = smallImgNum / mUnitImgNum;
        final int yushu = smallImgNum % mUnitImgNum;
        if (yushu != 0) {
            //余数不为0,最后一张大图小于单位小图数
            largeImgNum = shang + 1;
        } else {
            largeImgNum = shang;
        }
        return largeImgNum;
    }

    /**
     * 计算总图片数(小图)
     */
    private int computeSmallImgNum() {
        final int smallImgNum;
        final int shang = mTotleTime / mInterval;
        final int yushu = mTotleTime % mInterval;
        if (yushu != 0) {
            //余数不为0,保证最后不到10秒的时间有图
            smallImgNum = shang + 1;
        } else {
            smallImgNum = shang;
        }
        return smallImgNum;
    }

    @Override
    public boolean quitSafely() {
        if (mWorkerHandler != null) {
            mWorkerHandler.removeCallbacksAndMessages(null);
            mWorkerHandler = null;
        }
        return super.quitSafely();
    }
}
