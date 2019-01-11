package com.dodo.xinyue.conan.view.dialog.download;

import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.core.util.CommonUtil;
import com.dodo.xinyue.core.util.log.DoDoLogger;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ConanDownloadDialog
 *
 * @author DoDo
 * @date 2019/1/11
 */
public class ConanDownloadDialog extends BaseDialog {

    private final String mPackageSize;

    @BindView(R2.id.container_normal)
    LinearLayout mContainerNormal = null;
    @BindView(R2.id.progressBar)
    ProgressBar mProgressBar = null;
    @BindView(R2.id.tvContent)
    TextView mTvContent = null;
    @BindView(R2.id.tvConfirm)
    TextView mTvConfirm = null;

    @OnClick(R2.id.tvConfirm)
    void onTvConfirmClicked() {
        cancel();
    }

    @OnClick(R2.id.tvCancel)
    void onTvCancelClicked() {
        Beta.startDownload();//先暂停，再取消
        Beta.cancelDownload();
        cancel();
    }

    @OnClick(R2.id.tvHide)
    void onTvHideClicked() {
        cancel();
        //后台下载
    }

    public ConanDownloadDialog(DialogPublicParamsBean bean,
                               String packageSize) {
        super(bean);
        this.mPackageSize = packageSize;
    }

    public static ConanDownloadDialogBuilder builder() {
        return new ConanDownloadDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_download;
    }

    @Override
    public void onBindView(View rootView) {
//        updateContent(0);
        mTvContent.setText("即将开始下载...\n总共 " + mPackageSize);
        mProgressBar.setMax((int) Beta.getUpgradeInfo().fileSize);//这里不能用Beta.getStrategyTask().getTotalLength(),第一次获取到的是0
        DoDoLogger.d(mProgressBar.getMax());
        Beta.registerDownloadListener(new DownloadListener() {
            @Override
            public void onReceive(DownloadTask task) {
                updateContent(task.getSavedLength());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mProgressBar.setProgress((int) task.getSavedLength(), true);
                } else {
                    mProgressBar.setProgress((int) task.getSavedLength());
                }
                DoDoLogger.d(mProgressBar.getProgress());
            }

            @Override
            public void onCompleted(DownloadTask task) {
                updateContent(task.getSavedLength());
                cancel();
            }

            @Override
            public void onFailed(DownloadTask task, int code, String extMsg) {
                DoDoLogger.d("下载出错\n错误码：" + code + "\n错误信息：" + extMsg);
                mTvContent.setText("下载出错，请稍后重试");
                mContainerNormal.setVisibility(View.GONE);
                mTvConfirm.setVisibility(View.VISIBLE);
                mProgressBar.setProgress((int) task.getSavedLength());
//                mProgressBar.setVisibility(View.GONE);
            }
        });
        switch (Beta.getStrategyTask().getStatus()) {
            case DownloadTask.INIT:
            case DownloadTask.DELETED:
            case DownloadTask.FAILED:
            case DownloadTask.PAUSED:
                Beta.startDownload();
                break;
            case DownloadTask.DOWNLOADING:
                Beta.startDownload();//先暂停，再继续下载
                Beta.startDownload();
                break;
            case DownloadTask.COMPLETE:
                cancel();
                break;
            default:
                break;
        }

    }

    private void updateContent(long size) {
        if (mTvContent != null) {
            mTvContent.setText("正在下载升级包，已下载 " + CommonUtil.getFormatDownloadPackageSize(size) + "\n总共 " + mPackageSize);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Beta.unregisterDownloadListener();
        super.onDismiss(dialog);
    }
}
