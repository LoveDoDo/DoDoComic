package com.dodo.xinyue.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.net.callback.IRequest;
import com.dodo.xinyue.core.net.callback.ISuccess;
import com.dodo.xinyue.core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * 保存文件
 *
 * @author DoDo
 * @date 2017/9/2
 */
public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request,
                        ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || "".equals(downloadDir)) {
            downloadDir = "down_loads";
        }
        if (extension == null || "".equals(extension)) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        if ("apk".equals(FileUtil.getExtension(file.getPath()))) {
            //自动安装apk
            autoInstallApk(file);
        }
    }

    /**
     * 自动安装apk
     *
     * @param apk
     */
    private void autoInstallApk(File apk) {
        final Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        install.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        DoDo.getAppContext().startActivity(install);
    }
}
