package com.dodo.xinyue.conan.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;

import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.main.index.anim.RotateCloseArrow;
import com.dodo.xinyue.conan.main.index.anim.RotateOpenArrow;
import com.dodo.xinyue.conan.main.index.bean.ArrowBean;
import com.dodo.xinyue.conan.main.index.dialog.ConanListDialog;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 *
 * @author DoDo
 * @date 2018/9/26
 */
public class IndexDelegate extends BaseBottomItemDelegate {

    private static final String TAG = "IndexDelegate";

    /**
     * 箭头相关
     */
    private final List<ArrowBean> mArrowList = new ArrayList<>();
    private int mLastArrowIndex = -1;
    @BindView(R2.id.tvLanguageArrow)
    IconTextView mLangaugeArrow = null;//语言
    @BindView(R2.id.tvTypeArrow)
    IconTextView mTypeArrow = null;//类型
    @BindView(R2.id.tvSourceArrow)
    IconTextView mSourceArrow = null;//来源
    @BindView(R2.id.tvFormArrow)
    IconTextView mFormArrow = null;//展示形式

    /**
     * 分类相关
     */
    private static final String[] LANGUAGE_DES = new String[]{"国语", "日语"};
    private static final String[] TYPE_DES = new String[]{"TV版", "剧场版", "OVA", "特别篇", "剧情篇", "主线篇"};
    private static final String[] SOURCE_DES = new String[]{"爱奇艺"};
    private static final String[] FORM_DES = new String[]{"文字列表", "数字列表", "图文列表", "网格列表"};
    private int mLanguageIndex = 0;
    private int mTypeIndex = 0;
    private int mSourceIndex = 0;
    private int mFormIndex = 0;
    @BindView(R2.id.tvLanguage)
    AppCompatTextView mTvLangauge = null;//语言
    @BindView(R2.id.tvType)
    AppCompatTextView mTvType = null;//类型
    @BindView(R2.id.tvSource)
    AppCompatTextView mTvSource = null;//来源
    @BindView(R2.id.tvForm)
    AppCompatTextView mTvForm = null;//展示形式

    @OnClick(R2.id.llLanguage)
    void onLanguageClicked() {
//        ToastUtils.showShort("语言");

//        ConanDialog.builder()
//                .title("heiheihie")
//                .anim(R.style.CustomDialogAnim)
//                .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
//                .topLeftRadius(10)
//                .topRightRadius(20)
//                .bottomLeftRadius(30)
//                .bottomRightRadius(40)
//                .widthScale(0.8f)
//                .heightScale(0.5f)
//                .coverStatusBar(true)
//                .canceledOnTouchOutside(false)
//                .cancelable(false)
//                .onOpen(new IOpenDialog() {
//                    @Override
//                    public void onOpen() {
//                        ToastUtils.showShort("open");
//                    }
//                })
//                .onClose(new ICloseDialog() {
//                    @Override
//                    public void onClose() {
//                        ToastUtils.showShort("close");
//                    }
//                })
//                .build()
//                .show();


        ConanListDialog.builder()
                .title("切换语言")
                .addItems(LANGUAGE_DES, mLanguageIndex)
                .onSelected(selectedIndex -> {
                    mLanguageIndex = selectedIndex;
                    mTvLangauge.setText(LANGUAGE_DES[mLanguageIndex]);
                })
                .onOpen(() -> switchArrow(0))
                .onClose(() -> switchArrow(0))
                .topLeftRadius(6)
                .topRightRadius(6)
//                .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                .gravity(Gravity.TOP)
                .anim(R.style.CustomDialogAnim)
                .coverStatusBar(true)
                .build()
                .show();


    }

    @OnClick(R2.id.llType)
    void onTypeClicked() {
        ConanListDialog.builder()
                .title("切换版本")
                .addItems(TYPE_DES, mTypeIndex)
                .onSelected(selectedIndex -> {
                    mTypeIndex = selectedIndex;
                    mTvType.setText(TYPE_DES[mTypeIndex]);
                })
                .onOpen(() -> switchArrow(1))
                .onClose(() -> switchArrow(1))
                .topLeftRadius(6)
                .topRightRadius(6)
//                .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                .gravity(Gravity.TOP)
                .anim(R.style.CustomDialogAnim)
                .coverStatusBar(false)
                .build()
                .show();
    }

    @OnClick(R2.id.llSource)
    void onSourceClicked() {
        ConanListDialog.builder()
                .title("切换来源")
                .addItems(SOURCE_DES, mSourceIndex)
                .onSelected(selectedIndex -> {
                    mSourceIndex = selectedIndex;
                    mTvSource.setText(SOURCE_DES[mSourceIndex]);
                })
                .onOpen(() -> switchArrow(2))
                .onClose(() -> switchArrow(2))
                .topLeftRadius(6)
                .topRightRadius(6)
                .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                .anim(R.style.CustomDialogAnim)
                .coverStatusBar(true)
                .build()
                .show();
    }

    @OnClick(R2.id.llForm)
    void onFormClicked() {
        ConanListDialog.builder()
                .title("切换列表")
                .addItems(FORM_DES, mFormIndex)
                .onSelected(selectedIndex -> {
                    mFormIndex = selectedIndex;
                    mTvForm.setText(FORM_DES[mFormIndex]);
                })
                .onOpen(() -> switchArrow(3))
                .onClose(() -> switchArrow(3))
                .topLeftRadius(6)
                .topRightRadius(6)
                .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                .anim(R.style.CustomDialogAnim)
                .coverStatusBar(false)
                .build()
                .show();
    }

    private void showDialog() {

    }

    public static IndexDelegate create() {
        return new IndexDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        for (int i = 0; i < 4; i++) {
            mArrowList.add(new ArrowBean());
        }
        mTvLangauge.setText(LANGUAGE_DES[mLanguageIndex]);
        mTvType.setText(TYPE_DES[mTypeIndex]);
        mTvSource.setText(SOURCE_DES[mSourceIndex]);
        mTvForm.setText(FORM_DES[mFormIndex]);
    }

    private void switchArrow(int index) {
        switchArrow(index, true);
    }

    /**
     * 切换箭头方向
     *
     * @param index
     * @param restoreLastArrow 点击触发传true即可,false为内部使用
     */
    private void switchArrow(int index, boolean restoreLastArrow) {
        final ArrowBean arrowBean = mArrowList.get(index);
        final int currentStatus = arrowBean.getStatus();
        switch (currentStatus) {
            case ArrowBean.STATUS_CLOSE:
                if (restoreLastArrow) {
                    startArrowOpenAnim(index);
                    arrowBean.setStatus(ArrowBean.STATUS_OPEN);
                }
                break;
            case ArrowBean.STATUS_OPEN:
                startArrowCloseAnim(index);
                arrowBean.setStatus(ArrowBean.STATUS_CLOSE);
                break;
            default:
                break;
        }
        if (!restoreLastArrow) {
            return;
        }
        if (mLastArrowIndex == -1) {
            mLastArrowIndex = index;
            return;
        }
        if (mLastArrowIndex == index) {
            return;
        }
        final int lastArrowIndex = mLastArrowIndex;
        switchArrow(lastArrowIndex, false);
        mLastArrowIndex = index;
    }

    private void startArrowOpenAnim(int index) {
        final IconTextView arrow = getArrow(index);
        if (arrow == null) {
            return;
        }
        YoYo.with(new RotateOpenArrow())
                .duration(300)
                .playOn(arrow);
    }

    private void startArrowCloseAnim(int index) {
        final IconTextView arrow = getArrow(index);
        if (arrow == null) {
            return;
        }
        YoYo.with(new RotateCloseArrow())
                .duration(300)
                .playOn(arrow);
    }

    private IconTextView getArrow(int index) {
        IconTextView arrow = null;
        switch (index) {
            case 0:
                arrow = mLangaugeArrow;
                break;
            case 1:
                arrow = mTypeArrow;
                break;
            case 2:
                arrow = mSourceArrow;
                break;
            case 3:
                arrow = mFormArrow;
                break;
            default:
                break;
        }
        return arrow;
    }
}
