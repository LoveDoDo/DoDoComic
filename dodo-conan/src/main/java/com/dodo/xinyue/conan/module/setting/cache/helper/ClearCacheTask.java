package com.dodo.xinyue.conan.module.setting.cache.helper;

import android.os.AsyncTask;
import android.view.Gravity;

import com.bumptech.glide.Glide;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.module.setting.cache.callback.IDone;
import com.dodo.xinyue.conan.view.dialog.loading.ConanLoadingDialog;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.ui.dialog.manager.DialogManager;
import com.dodo.xinyue.core.util.CommonUtil;

import java.io.File;

/**
 * ClearCacheTask
 *
 * @author DoDo
 * @date 2018/10/28
 */
public class ClearCacheTask extends AsyncTask<String, Void, Void> {

    public static final String CLEAR_ALL_CACHE = "clear_all_cache";
    public static final String CLEAR_FILE_CACHE = "clear_file_cache";
    public static final String CLEAR_IMAGE_CACHE = "clear_image_cache";

    private final IDone DONE;

    public ClearCacheTask(IDone done) {
        this.DONE = done;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ConanLoadingDialog.builder()
                .anim(-1)//防止旋转动画卡顿
                .gravity(Gravity.CENTER)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .build()
                .show();
    }

    @Override
    protected Void doInBackground(String... types) {
        final String type = types[0];
        switch (type) {
            case CLEAR_ALL_CACHE:
                clearJsonCache();
                clearGlideCache();
                break;
            case CLEAR_FILE_CACHE:
                clearJsonCache();
                break;
            case CLEAR_IMAGE_CACHE:
                clearGlideCache();
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        DialogManager.getInstance().cancelLastDialog();
        if (DONE != null) {
            DONE.onDone();
        }

    }

    private void clearGlideCache() {
        Glide.get(DoDo.getAppContext()).clearDiskCache();
    }

    private boolean clearJsonCache() {
        File httpCacheDir = new File(DoDo.getAppContext().getCacheDir(), DoDo.getAppContext().getString(R.string.okhttp_cache_dir));
        return CommonUtil.deleteFolderFile(httpCacheDir.getAbsolutePath(), false);
    }

}
