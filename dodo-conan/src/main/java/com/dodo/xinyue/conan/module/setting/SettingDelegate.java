package com.dodo.xinyue.conan.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.database.util.ConanMessageDBUtil;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;
import com.dodo.xinyue.conan.module.setting.about.AboutDelegate;
import com.dodo.xinyue.conan.module.setting.cache.ClearCacheDelegate;
import com.dodo.xinyue.conan.module.setting.message.MessageSettingDelegate;
import com.dodo.xinyue.conan.module.setting.preference.IndexPreferenceDelegate;
import com.dodo.xinyue.conan.view.SwitchButton;
import com.dodo.xinyue.conan.view.dialog.loading.ConanLoadingDialog;
import com.dodo.xinyue.conan.view.dialog.normal.ConanNormalDialog;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.ui.dialog.manager.DialogManager;
import com.dodo.xinyue.core.util.CommonUtil;
import com.tencent.bugly.beta.Beta;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * SettingDelegate
 *
 * @author DoDo
 * @date 2018/10/27
 */
public class SettingDelegate extends BaseModuleDelegate {

    private static final String ARGS_ALL_CACHE_SIZE = "args_all_cache_size";
    private static final String ARGS_JSON_CACHE_SIZE = "args_json_cache_size";
    private static final String ARGS_GLIDE_CACHE_SIZE = "args_glide_cache_size";
    private static final int REQUEST_CODE_CLEAR_CACHE = 101;

    private long mAllCacheSize = 0;

    private long mJsonCacheSize = 0;
    private long mGlideCacheSize = 0;

    @BindView(R2.id.tvCacheSize)
    TextView mTvCacheSize = null;
    @BindView(R2.id.tvAbout)
    TextView mTvAbout = null;

    @BindView(R2.id.sbQuicklyOpenApp)
    SwitchButton mSbQuicklyOpenApp = null;
    @BindView(R2.id.sbDiscoverGift)
    SwitchButton mSbDiscoverGift = null;

    @OnClick(R2.id.rlQuicklyOpenApp)
    void onQuicklyOpenAppClicked() {
        mSbQuicklyOpenApp.toggle();
    }

    @OnClick(R2.id.rlIndexPreference)
    void onIndexPreferenceClicked() {
        start(IndexPreferenceDelegate.create());
    }

    @OnClick(R2.id.rlMessage)
    void onMessageClicked() {
        start(MessageSettingDelegate.create());
    }

    @OnClick(R2.id.rlDiscoverGift)
    void onDiscoverGiftClicked() {
        mSbDiscoverGift.toggle();
    }

    @OnClick(R2.id.rlClearCache)
    void onClearCacheClicked() {
        if (mAllCacheSize <= 0) {
            ToastUtils.showShort("暂无缓存，无需清理");
            return;
        }
        getSupportDelegate().startForResult(ClearCacheDelegate.create(mAllCacheSize, mJsonCacheSize, mGlideCacheSize), REQUEST_CODE_CLEAR_CACHE);
    }

    @OnClick(R2.id.llCheckUpdate)
    void onCheckUpdateClicked() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("网络未连接，请联网后重试");
            return;
        }
        final boolean isBuglyInited = DoDo.getConfiguration(ApiConstants.IS_BUGLY_INIT);
        if (!isBuglyInited) {
            ToastUtils.showShort("检测更新失败，过几秒再试吧~");
            return;
        }
        Beta.checkUpgrade();//必须初始化SDK后调用才有效
        ConanLoadingDialog.builder()
                .timeout(() -> ToastUtils.showShort("检查更新超时，请稍后重试"))
                .anim(-1)
                .backgroundDimEnabled(false)
                .canceledOnTouchOutside(false)
                .build()
                .show();
    }

    @OnClick(R2.id.llExitApp)
    void onExitAppClicked() {
        ConanNormalDialog.builder()
                .title("关闭程序")
                .content("确定退出App？")
                .confirm(() -> {
                    AppUtils.exitApp();
                })
                .build()
                .show();
    }

    @OnClick(R2.id.rlAbout)
    void onAboutClicked() {
        start(AboutDelegate.create());
    }

    @OnClick(R2.id.llClearMessage)
    void onClearMessageClicked() {
        ConanNormalDialog.builder()
                .title("清空消息")
                .content("确定清空全部消息记录？")
                .confirm(() -> {
                    ConanLoadingDialog.builder()
                            .anim(-1)//防止旋转动画卡顿
                            .gravity(Gravity.CENTER)
                            .cancelable(false)
                            .canceledOnTouchOutside(false)
                            .onOpen(() -> {
                                ConanMessageDBUtil.dropTable();
                                ConanMessageDBUtil.createTable();
                                DialogManager.getInstance().cancelLastDialog();
                            })
                            .onClose(() -> ToastUtils.showShort("清空消息记录成功"))
                            .build()
                            .show();

                })
                .build()
                .show();
    }

    public static SettingDelegate create() {
        return new SettingDelegate();
    }

    @Override
    public Object setChildLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public String setTitle() {
        return "设置";
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mTvAbout.setText("关于" + AppUtils.getAppName());

        mGlideCacheSize = getGlideCacheSize();
        mJsonCacheSize = getJsonCacheSize();
        mAllCacheSize = mGlideCacheSize + mJsonCacheSize;
        mTvCacheSize.setText(CommonUtil.getFormatSizeSimple(mAllCacheSize));

        mSbQuicklyOpenApp.setChecked(ApiHelper.isQuicklyOpenApp());
        mSbQuicklyOpenApp.setOnCheckedChangeListener((buttonView, isChecked) -> ApiHelper.setQuicklyOpenApp(isChecked));
        mSbDiscoverGift.setOnCheckedChangeListener((buttonView, isChecked) -> ToastUtils.showShort(isChecked + ""));
    }

    private long getGlideCacheSize() {
        File glideCacheDir = Glide.getPhotoCacheDir(DoDo.getAppContext());
        if (glideCacheDir == null) {
            return 0;
        }
        return CommonUtil.getFolderSize(glideCacheDir);
    }

    private long getJsonCacheSize() {
        File httpCacheDir = new File(DoDo.getAppContext().getCacheDir(), getString(R.string.okhttp_cache_dir));
        if (!httpCacheDir.exists()) {
            return 0;
        }
        return CommonUtil.getFolderSize(httpCacheDir);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CLEAR_CACHE:
                mAllCacheSize = data.getLong(ARGS_ALL_CACHE_SIZE);
                mJsonCacheSize = data.getLong(ARGS_JSON_CACHE_SIZE);
                mGlideCacheSize = data.getLong(ARGS_GLIDE_CACHE_SIZE);
                mTvCacheSize.setText(CommonUtil.getFormatSizeSimple(mAllCacheSize));
                break;
            default:
                break;

        }
    }

}
