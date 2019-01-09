package com.dodo.xinyue.conan.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.module.setting.cache.ClearCacheDelegate;
import com.dodo.xinyue.conan.module.setting.message.MessageDelegate;
import com.dodo.xinyue.conan.module.setting.preference.IndexPreferenceDelegate;
import com.dodo.xinyue.conan.view.SwitchButton;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.util.CommonUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * SettingDelegate
 *
 * @author DoDo
 * @date 2018/10/27
 */
public class SettingDelegate extends DoDoDelegate {

    private static final String ARGS_ALL_CACHE_SIZE = "args_all_cache_size";
    private static final String ARGS_JSON_CACHE_SIZE = "args_json_cache_size";
    private static final String ARGS_GLIDE_CACHE_SIZE = "args_glide_cache_size";
    private static final int REQUEST_CODE_CLEAR_CACHE = 101;

    private long mAllCacheSize = 0;

    private long mJsonCacheSize = 0;
    private long mGlideCacheSize = 0;

    @BindView(R2.id.tvCacheSize)
    TextView mTvCacheSize = null;

    @BindView(R2.id.sbQuicklyOpenApp)
    SwitchButton mSbQuicklyOpenApp = null;
    @BindView(R2.id.sbDiscoverGift)
    SwitchButton mSbDiscoverGift = null;

    @OnClick(R2.id.tvBack)
    void onTvBackClicked() {
        pop();
    }

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
        start(MessageDelegate.create());
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

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
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
        if (resultCode != 1) {
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
