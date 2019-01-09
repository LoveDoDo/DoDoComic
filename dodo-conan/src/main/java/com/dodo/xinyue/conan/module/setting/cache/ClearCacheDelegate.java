package com.dodo.xinyue.conan.module.setting.cache;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.module.setting.cache.helper.ClearCacheTask;
import com.dodo.xinyue.conan.view.dialog.normal.ConanNormalDialog;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.dialog.options.DialogOptions;
import com.dodo.xinyue.core.util.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClearCacheDelegate
 *
 * @author DoDo
 * @date 2018/10/28
 */
public class ClearCacheDelegate extends DoDoDelegate {

    private static final String ARGS_ALL_CACHE_SIZE = "args_all_cache_size";
    private static final String ARGS_JSON_CACHE_SIZE = "args_json_cache_size";
    private static final String ARGS_GLIDE_CACHE_SIZE = "args_glide_cache_size";

    private static final DialogOptions DIALOG_OPTIONS =
            new DialogOptions()
                    .radius(12)
                    .widthScale(0.85f);

    private long mAllCacheSize = 0;

    private long mJsonCacheSize = 0;
    private long mGlideCacheSize = 0;

    @BindView(R2.id.tvCacheSize)
    TextView mTvCacheSize = null;
    @BindView(R2.id.tvFileCacheSize)
    TextView mTvFileCacheSize = null;
    @BindView(R2.id.tvImageCacheSize)
    TextView mTvImageCacheSize = null;

    @OnClick(R2.id.tvBack)
    void onTvBackClicked() {
        final Bundle args = new Bundle();
        args.putLong(ARGS_ALL_CACHE_SIZE, mAllCacheSize);
        args.putLong(ARGS_JSON_CACHE_SIZE, mJsonCacheSize);
        args.putLong(ARGS_GLIDE_CACHE_SIZE, mGlideCacheSize);
        setFragmentResult(1, args);
        pop();
    }

    @OnClick(R2.id.rlClearCache)
    void onClearCacheClicked() {
        if (mAllCacheSize == 0) {
            ToastUtils.showShort("缓存已全部清除");
            return;
        }

        ConanNormalDialog.builder()
                .title("清空缓存")
                .content("确定清空全部缓存？")
                .confirm(() -> {
                    new ClearCacheTask(() -> {
                        mJsonCacheSize = 0;
                        mGlideCacheSize = 0;

                        updateCacheDisplay();

                        ToastUtils.showShort("清除缓存文件成功");
                    }).execute(ClearCacheTask.CLEAR_ALL_CACHE);
                })
                .options(DIALOG_OPTIONS)
                .build()
                .show();

    }

    @OnClick(R2.id.rlClearFileCache)
    void onClearFileCacheClicked() {
        if (mJsonCacheSize == 0) {
            return;
        }

        ConanNormalDialog.builder()
                .title("清空缓存")
                .content("确定清空文件缓存？")
                .confirm(() -> {
                    new ClearCacheTask(() -> {
                        mJsonCacheSize = 0;

                        updateCacheDisplay();

                        ToastUtils.showShort("清除缓存文件成功");
                    }).execute(ClearCacheTask.CLEAR_FILE_CACHE);
                })
                .options(DIALOG_OPTIONS)
                .build()
                .show();

    }

    @OnClick(R2.id.rlClearImageCache)
    void onClearImageCacheClicked() {
        if (mGlideCacheSize == 0) {
            return;
        }

        ConanNormalDialog.builder()
                .title("清空缓存")
                .content("确定清空图片缓存？")
                .confirm(() -> {
                    new ClearCacheTask(() -> {
                        mGlideCacheSize = 0;

                        updateCacheDisplay();

                        ToastUtils.showShort("清除缓存文件成功");
                    }).execute(ClearCacheTask.CLEAR_IMAGE_CACHE);
                })
                .options(DIALOG_OPTIONS)
                .build()
                .show();

    }

    public static ClearCacheDelegate create(long allCacheSize, long jsonCacheSize, long glideCacheSize) {
        final Bundle args = new Bundle();
        args.putLong(ARGS_ALL_CACHE_SIZE, allCacheSize);
        args.putLong(ARGS_JSON_CACHE_SIZE, jsonCacheSize);
        args.putLong(ARGS_GLIDE_CACHE_SIZE, glideCacheSize);
        final ClearCacheDelegate delegate = new ClearCacheDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_clear_cache;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final Bundle args = getArguments();
        if (args == null) {
            return;
        }

        mAllCacheSize = args.getLong(ARGS_ALL_CACHE_SIZE);
        mTvCacheSize.setText(CommonUtil.getFormatSizeSimple(mAllCacheSize));

        mJsonCacheSize = args.getLong(ARGS_JSON_CACHE_SIZE);
        mTvFileCacheSize.setText(CommonUtil.getFormatSizeSimple(mJsonCacheSize));

        mGlideCacheSize = args.getLong(ARGS_GLIDE_CACHE_SIZE);
        mTvImageCacheSize.setText(CommonUtil.getFormatSizeSimple(mGlideCacheSize));
    }

    private void updateCacheDisplay() {
        mAllCacheSize = mJsonCacheSize + mGlideCacheSize;
        mTvFileCacheSize.setText(CommonUtil.getFormatSizeSimple(mJsonCacheSize));
        mTvImageCacheSize.setText(CommonUtil.getFormatSizeSimple(mGlideCacheSize));

        mTvCacheSize.setText(CommonUtil.getFormatSizeSimple(mAllCacheSize));
    }

    @Override
    public boolean onBackPressedSupport() {
        final Bundle args = new Bundle();
        args.putLong(ARGS_ALL_CACHE_SIZE, mAllCacheSize);
        args.putLong(ARGS_JSON_CACHE_SIZE, mJsonCacheSize);
        args.putLong(ARGS_GLIDE_CACHE_SIZE, mGlideCacheSize);
        setFragmentResult(1, args);
        return super.onBackPressedSupport();
    }
}
