package com.dodo.xinyue.conan.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.constant.ApiURL;
import com.dodo.xinyue.conan.main.index.adapter.IndexPagerAdapter;
import com.dodo.xinyue.conan.main.index.adapter.LogoPagerAdapter;
import com.dodo.xinyue.conan.main.index.anim.RotateCloseArrowAnim;
import com.dodo.xinyue.conan.main.index.anim.RotateOpenArrowAnim;
import com.dodo.xinyue.conan.main.index.anim.TextScaleFadeInAnim;
import com.dodo.xinyue.conan.main.index.anim.TextScaleFadeOutAnim;
import com.dodo.xinyue.conan.main.index.bean.ArrowBean;
import com.dodo.xinyue.conan.main.index.dialog.ConanListDialog;
import com.dodo.xinyue.conan.module.history.HistoryDelegate;
import com.dodo.xinyue.conan.module.money.MoneyDelegate;
import com.dodo.xinyue.conan.module.search.SearchDelegate;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;
import com.dodo.xinyue.core.net.RestClient;
import com.dodo.xinyue.core.ui.dialog.options.DialogOptions;
import com.flyco.tablayout.SlidingTabLayout;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * 主页
 *
 * @author DoDo
 * @date 2018/9/26
 */
public class IndexDelegate extends BaseBottomItemDelegate {

    private static final String TAG = "IndexDelegate";

    private static final DialogOptions DIALOG_OPTIONS =
            new DialogOptions()
                    .anim(R.style.DialogBottomAnim)
                    .topLeftRadius(6)
                    .topRightRadius(6)
                    .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

    @BindView(R2.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R2.id.vpContent)
    ViewPager mViewPagerContent;

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
            mAnimController.add(null);
        }
        mTvLangauge.setText(LANGUAGE_DES[mLanguageIndex]);
        mTvType.setText(TYPE_DES[mTypeIndex]);
        mTvSource.setText(SOURCE_DES[mSourceIndex]);
        mTvForm.setText(FORM_DES[mFormIndex]);

        int[] imgRes = new int[]{R.drawable.conan_logo_2, R.drawable.huiyuan};

        mViewPagerLogo.setOffscreenPageLimit(imgRes.length);//默认是1 默认最多缓存2的1次方+1=3
        mViewPagerLogo.setAdapter(new LogoPagerAdapter(imgRes));

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        requestData();

    }

    private void requestData() {
        final String moduleUrl = getUrl();
        final String requestUrl = moduleUrl.replace("{page}", "1");
        RestClient.builder()
                .url(requestUrl)
                .success(response -> {
                    final String json = response.substring("var tvInfoJs=".length(), response.length());
                    final JSONObject data = JSON.parseObject(json).getJSONObject("data");
                    final int count = data.getIntValue("allNum");
                    handleData(moduleUrl, count);
                })
                .build()
                .get();
    }

    private String getUrl() {
        String url = "";
        switch (mLanguageIndex) {
            case 0:
                //国语
                switch (mTypeIndex) {
                    case 0:
                        //TV版
                        url = ApiURL.GUO_YU_TV_URL;
                        break;
                    case 1:
                        //剧场版
                        url = ApiURL.GUO_YU_JU_CHANG_URL;
                        break;
                    case 5:
                        //主线篇
                        url = ApiURL.GUO_YU_ZHU_XIAN_URL;
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                //日语
                switch (mTypeIndex) {
                    case 0:
                        //TV版
                        url = ApiURL.RI_YU_TV_URL;
                        break;
                    case 1:
                        //剧场版
                        url = ApiURL.RI_YU_JU_CHANG_URL;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort("url未找到");
            if (mLanguageIndex == 0) {
                url = ApiURL.GUO_YU_TV_URL;
            } else {
                url = ApiURL.RI_YU_TV_URL;
            }
            mTypeIndex = 0;
//            mSpinnerType.setSelectedIndex(0);
        }

        return url;
    }

    private void handleData(String url, int count) {

        final int pageCount = count / 50 + 1;
        final String[] titles = new String[pageCount];
        final String[] urls = new String[pageCount];
        for (int i = 0; i < pageCount; i++) {
            if (i != pageCount - 1) {
                titles[i] = Integer.toString(50 * (i) + 1) + "-" + Integer.toString(50 * (i + 1));
            } else {
                titles[i] = Integer.toString(50 * (i) + 1) + "-" + Integer.toString(count);
            }

            urls[i] = url.replace("{page}", Integer.toString(i + 1));
        }

        switch (mFormIndex) {
            case 0:
            case 1:
                mViewPagerContent.setOffscreenPageLimit(pageCount);
                break;
            case 2:
            case 3:
                mViewPagerContent.setOffscreenPageLimit(1);
                break;
            default:
                break;
        }

//        mViewPager.setOffscreenPageLimit(pageCount);//默认是1 默认最多缓存2的1次方+1=3
        mViewPagerContent.setAdapter(new IndexPagerAdapter(getChildFragmentManager(), titles, urls));
        mTabLayout.setViewPager(mViewPagerContent);
        mTabLayout.setCurrentTab(0, true);
        mTabLayout.notifyDataSetChanged();//清除上一次选中状态
    }

    public int getLanguage() {
        return mLanguageIndex;
    }

    public int getType() {
        return mTypeIndex;
    }

    public int getSource() {
        return mSourceIndex;
    }

    public int getForm() {
        return mFormIndex;
    }


    @BindView(R2.id.vpLogo)
    ViewPager mViewPagerLogo = null;
    @BindView(R2.id.tvTitle)
    AppCompatTextView mTvTitle = null;

    @OnClick(R2.id.tvTitle)
    void onTvTitleClicked() {
        if (mViewPagerLogo.getCurrentItem() + 1 >= mViewPagerLogo.getChildCount()) {
            mViewPagerLogo.setCurrentItem(0, true);
        } else {
            mViewPagerLogo.setCurrentItem(mViewPagerLogo.getCurrentItem() + 1, true);
        }
    }

    @OnClick(R2.id.tvSearch)
    void onTvSearchClicked() {
        getParentDelegate().start(new SearchDelegate());
    }

    @OnClick(R2.id.tvHistory)
    void onTvHistoryClicked() {
        getParentDelegate().start(new HistoryDelegate());
    }

    @OnClick(R2.id.tvMoney)
    void onTvMoneyClicked() {
        getParentDelegate().start(new MoneyDelegate());
    }

    @OnLongClick(R2.id.tvSearch)
    boolean onTvSearchLongClicked() {
        ToastUtils.showShort("搜索");
        return true;
    }

    @OnLongClick(R2.id.tvHistory)
    boolean onTvHistoryLongClicked() {
        ToastUtils.showShort("历史记录");
        return true;
    }

    @OnLongClick(R2.id.tvMoney)
    boolean onTvMoneyLongClicked() {
        ToastUtils.showShort("恭喜找到第三条线索");
        return true;
    }

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
    //动画控制器

    private ArrayList<YoYo.YoYoString> mAnimController = new ArrayList<>();
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
        ConanListDialog.builder()
                .title("切换语言")
                .addItems(LANGUAGE_DES, mLanguageIndex)
                .onSelected(selectedIndex -> {
                    final int lastIndex = mLanguageIndex;
                    mLanguageIndex = selectedIndex;
                    requestData();
                    YoYo.with(new TextScaleFadeInAnim())
                            .onStart(animator -> mTvLangauge.setText(LANGUAGE_DES[lastIndex]))
                            .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                                    .onStart(animator1 -> mTvLangauge.setText(LANGUAGE_DES[mLanguageIndex]))
                                    .interpolate(new DecelerateInterpolator())
                                    .duration(300)
                                    .playOn(mTvLangauge))
                            .interpolate(new AccelerateInterpolator())
                            .duration(300)
                            .playOn(mTvLangauge);

                })
                .onOpen(() -> switchArrow(0))
                .onClose(() -> switchArrow(0))
                .options(DIALOG_OPTIONS)
                .build()
                .show();
    }

    @OnClick(R2.id.llType)
    void onTypeClicked() {
        ConanListDialog.builder()
                .title("切换版本")
                .addItems(TYPE_DES, mTypeIndex)
                .onSelected(selectedIndex -> {
                    final int lastIndex = mTypeIndex;
                    mTypeIndex = selectedIndex;
                    requestData();
                    YoYo.with(new TextScaleFadeInAnim())
                            .onStart(animator -> mTvType.setText(TYPE_DES[lastIndex]))
                            .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                                    .onStart(animator1 -> mTvType.setText(TYPE_DES[mTypeIndex]))
                                    .interpolate(new DecelerateInterpolator())
                                    .duration(300)
                                    .playOn(mTvType))
                            .interpolate(new AccelerateInterpolator())
                            .duration(300)
                            .playOn(mTvType);
                })
                .onOpen(() -> switchArrow(1))
                .onClose(() -> switchArrow(1))
                .options(DIALOG_OPTIONS)
                .build()
                .show();
    }

    @OnClick(R2.id.llSource)
    void onSourceClicked() {
        ConanListDialog.builder()
                .title("切换来源")
                .addItems(SOURCE_DES, mSourceIndex)
                .onSelected(selectedIndex -> {
                    final int lastIndex = mSourceIndex;
                    mSourceIndex = selectedIndex;
                    requestData();
                    YoYo.with(new TextScaleFadeInAnim())
                            .onStart(animator -> mTvSource.setText(SOURCE_DES[lastIndex]))
                            .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                                    .onStart(animator1 -> mTvSource.setText(SOURCE_DES[mSourceIndex]))
                                    .interpolate(new DecelerateInterpolator())
                                    .duration(300)
                                    .playOn(mTvSource))
                            .interpolate(new AccelerateInterpolator())
                            .duration(300)
                            .playOn(mTvSource);
                })
                .onOpen(() -> switchArrow(2))
                .onClose(() -> switchArrow(2))
                .options(DIALOG_OPTIONS)
                .build()
                .show();
    }

    @OnClick(R2.id.llForm)
    void onFormClicked() {
        ConanListDialog.builder()
                .title("切换列表")
                .addItems(FORM_DES, mFormIndex)
                .onSelected(selectedIndex -> {
                    final int lastIndex = mFormIndex;
                    mFormIndex = selectedIndex;
                    requestData();
                    YoYo.with(new TextScaleFadeInAnim())
                            .onStart(animator -> mTvForm.setText(FORM_DES[lastIndex]))
                            .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                                    .onStart(animator1 -> mTvForm.setText(FORM_DES[mFormIndex]))
                                    .interpolate(new DecelerateInterpolator())
                                    .duration(300)
                                    .playOn(mTvForm))
                            .interpolate(new AccelerateInterpolator())
                            .duration(300)
                            .playOn(mTvForm);
                })
                .onOpen(() -> switchArrow(3))
                .onClose(() -> switchArrow(3))
                .options(DIALOG_OPTIONS)
                .build()
                .show();
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

        final YoYo.YoYoString lastAnim = mAnimController.get(index);
        if (lastAnim != null && lastAnim.isRunning()) {
            lastAnim.stop();
        }

        final YoYo.YoYoString anim = YoYo.with(new RotateOpenArrowAnim())
                .interpolate(new AccelerateDecelerateInterpolator())
                .duration(600)
                .playOn(arrow);

        mAnimController.set(index, anim);
    }

    private void startArrowCloseAnim(int index) {
        final IconTextView arrow = getArrow(index);
        if (arrow == null) {
            return;
        }

        final YoYo.YoYoString lastAnim = mAnimController.get(index);
        if (lastAnim != null && lastAnim.isRunning()) {
            lastAnim.stop();
        }

        final YoYo.YoYoString anim = YoYo.with(new RotateCloseArrowAnim())
                .duration(300)
                .playOn(arrow);

        mAnimController.set(index, anim);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAnimController.clear();
    }
}
