package com.dodo.xinyue.conan.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.daimajia.androidanimations.library.YoYo;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.constant.ApiURL;
import com.dodo.xinyue.conan.helper.ApiHelper;
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
import com.dodo.xinyue.conan.view.dialog.message.ConanMessageDialog;
import com.dodo.xinyue.conan.view.dialog.update.ConanUpdateDialog;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;
import com.dodo.xinyue.core.net.RestClient;
import com.flyco.tablayout.SlidingTabLayout;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Arrays;
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

    @BindView(R2.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R2.id.vpContent)
    ViewPager mViewPagerContent;

    @BindView(R2.id.rlStatusLayout)
    RelativeLayout mStatusLayout = null;
    @BindView(R2.id.llLoading)
    LinearLayoutCompat mLoadingLayout = null;
    @BindView(R2.id.llLoadFailure)
    LinearLayoutCompat mLoadingFailureLayout = null;
    @BindView(R2.id.llContent)
    LinearLayoutCompat mContentLayout = null;

    @OnClick(R2.id.llLoadFailure)
    void onLoadFailureClicked() {
        onLoadingStatus();
        requestData();
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
        initData();
    }

    private List<Integer> mLanguageIndexList = null;
    private List<Integer> mTypeIndexList = null;
    private List<Integer> mSourceIndexList = null;
    private List<Integer> mFormIndexList = null;

    /**
     * 初始化数据
     */
    private void initData() {
        /**
         * 初始化下拉框及配置
         */
        final int configVersion = ApiHelper.getIndexPreferenceConfigVersion();
        if (configVersion != ApiConstants.INDEX_PREFERENCE_CONFIG_VERSION) {
            ApiHelper.clearIndexPreferenceConfig();
            ApiHelper.setIndexPreferenceConfigVersion();
        }
        mLanguageIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_LANGUAGE);
        if (mLanguageIndexList == null) {
            mLanguageIndexList = Arrays.asList(ApiConstants.LANGUAGE_DEFAULT);
            ApiHelper.saveIndexPreferenceConfig(ApiConstants.KEY_LANGUAGE, ApiConstants.LANGUAGE_DEFAULT);
        }
        mTypeIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_TYPE);
        if (mTypeIndexList == null) {
            mTypeIndexList = Arrays.asList(ApiConstants.TYPE_DEFAULT);
            ApiHelper.saveIndexPreferenceConfig(ApiConstants.KEY_TYPE, ApiConstants.TYPE_DEFAULT);
        }
        mSourceIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_SOURCE);
        if (mSourceIndexList == null) {
            mSourceIndexList = Arrays.asList(ApiConstants.SOURCE_DEFAULT);
            ApiHelper.saveIndexPreferenceConfig(ApiConstants.KEY_SOURCE, ApiConstants.SOURCE_DEFAULT);
        }
        mFormIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_FORM);
        if (mFormIndexList == null) {
            mFormIndexList = Arrays.asList(ApiConstants.FORM_DEFAULT);
            ApiHelper.saveIndexPreferenceConfig(ApiConstants.KEY_FORM, ApiConstants.FORM_DEFAULT);
        }

        mLanguageIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_LANGUAGE_DEFAULT_INDEX);
        mTypeIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_TYPE_DEFAULT_INDEX);
        mSourceIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_SOURCE_DEFAULT_INDEX);
        mFormIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_FORM_DEFAULT_INDEX);

        mTvLanguage.setText(ApiHelper.getLanguageStr(mLanguageIndex));
        mTvType.setText(ApiHelper.getTypeStr(mTypeIndex));
        mTvSource.setText(ApiHelper.getSourceStr(mSourceIndex));
        mTvForm.setText(ApiHelper.getFormStr(mFormIndex));

        /**
         * 初始化箭头相关
         */
        for (int i = 0; i < 4; i++) {
            mArrowList.add(new ArrowBean());
            mArrowAnimController.add(null);
        }
        /**
         * 初始化头像viewpager
         */
        int[] imgRes = new int[]{R.drawable.conan_logo_2, R.drawable.huiyuan};

        mViewPagerLogo.setOffscreenPageLimit(imgRes.length);//默认是1 默认最多缓存2的1次方+1=3
        mViewPagerLogo.setAdapter(new LogoPagerAdapter(imgRes));

        onLoadingStatus();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        if (savedInstanceState != null) {
//            return;
//        }
        requestData();

    }

    private void requestData() {

//        onLoadingStatus();

        final String pageModuleUrl = getPageModuleUrl();
        if (TextUtils.isEmpty(pageModuleUrl)) {
            onLoadFailureStatus();
            return;
        }
        String requestUrl;
        switch (mSourceIndex) {
            case ApiConstants.SOURCE_IQIYI:
                requestUrl = pageModuleUrl.replace("{page}", "1");
                break;
            case ApiConstants.SOURCE_YOUKU:
                requestUrl = "";
                break;
            default:
                ToastUtils.showShort("Source is wrong");
                onLoadFailureStatus();
                return;
        }
        RestClient.builder()
                .url(requestUrl)
                .success(response -> {
                    final int numberCount = getNumberCount(response);
                    if (numberCount <= 0) {
                        ToastUtils.showShort("NumberCount is wrong!");
                        onLoadFailureStatus();
                        return;
                    }
                    handleData(pageModuleUrl, numberCount);
                })
                .failure(() -> {
                    ToastUtils.showShort("请求失败");
                    onLoadFailureStatus();
                })
                .error((code, msg) -> {
                    ToastUtils.showShort("请求错误");
                    onLoadFailureStatus();
                })
                .build()
                .get();
    }

    private void handleData(String pageModuleUrl, int count) {
        String[] titles = null;
        String[] urls = null;
        switch (mSourceIndex) {
            case ApiConstants.SOURCE_IQIYI:
                final int pageCount = (count % 50 == 0) ? (count / 50) : (count / 50 + 1);
                titles = new String[pageCount];
                urls = new String[pageCount];
                for (int i = 0; i < pageCount; i++) {
                    if (i != pageCount - 1) {
                        titles[i] = (i * 50 + 1) + "-" + (50 * (i + 1));
                    } else {
                        titles[i] = (i * 50 + 1) + "-" + count;
                    }

                    urls[i] = pageModuleUrl.replace("{page}", String.valueOf(i + 1));
                }
                break;
            case ApiConstants.SOURCE_YOUKU:

                break;
            default:
                break;
        }

        if (titles == null || urls == null) {
            ToastUtils.showShort("Handle data is wrong!");
            onLoadFailureStatus();
            return;
        }

        mViewPagerContent.setOffscreenPageLimit(2);//默认是1 默认最多缓存2的1次方+1=3
        mViewPagerContent.setAdapter(new IndexPagerAdapter(getChildFragmentManager(), titles, urls));
        mTabLayout.setViewPager(mViewPagerContent);
        mTabLayout.setCurrentTab(0, false);
        mTabLayout.notifyDataSetChanged();//清除上一次选中状态

        onLoadSuccessStatus();

    }

    /**
     * 获取总集数
     */
    private int getNumberCount(String response) {
        final int numberCount;
        switch (mSourceIndex) {
            case ApiConstants.SOURCE_IQIYI:
                final String json = response.substring("var tvInfoJs=".length(), response.length());
                final JSONObject data = JSON.parseObject(json).getJSONObject("data");
                numberCount = data.getIntValue("allNum");
                break;
            case ApiConstants.SOURCE_YOUKU:
                ToastUtils.showShort("youku");
                numberCount = 0;
                break;
            default:
                numberCount = 0;
                break;
        }
        return numberCount;
    }

    /**
     * 获取页数模版url {page}
     */
    private String getPageModuleUrl() {
        if (mSourceIndex == ApiConstants.SOURCE_IQIYI) {
            if (mLanguageIndex == ApiConstants.LANGUAGE_CHINESE
                    && mTypeIndex == ApiConstants.TYPE_TV) {
                return ApiURL.IQIYI_CHINESE_TV_URL;
            }
            if (mLanguageIndex == ApiConstants.LANGUAGE_CHINESE
                    && mTypeIndex == ApiConstants.TYPE_MOVIE) {
                return ApiURL.IQIYI_CHINESE_MOVIE_URL;
            }
            if (mLanguageIndex == ApiConstants.LANGUAGE_JAPANESE
                    && mTypeIndex == ApiConstants.TYPE_TV) {
                return ApiURL.IQIYI_JAPANESE_TV_URL;
            }
            if (mLanguageIndex == ApiConstants.LANGUAGE_JAPANESE
                    && mTypeIndex == ApiConstants.TYPE_MOVIE) {
                return ApiURL.IQIYI_JAPANESE_MOVIE_URL;
            }
        } else if (mSourceIndex == ApiConstants.SOURCE_YOUKU) {
            ToastUtils.showShort("youku");
        }

        ToastUtils.showShort("Url is not found!");
        return null;
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
        ConanUpdateDialog.builder()
                .title(AppUtils.getAppName() + "更新啦~")
                .content("DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~")
                .versionName("1")
                .packageSize("2")
                .bottomLeftRadius(8)
                .bottomRightRadius(8)
                .widthScale(0.85f)
                .build()
                .show();
        return true;
    }

    @OnLongClick(R2.id.tvHistory)
    boolean onTvHistoryLongClicked() {
        ToastUtils.showShort("历史记录");
        ConanMessageDialog.builder()
                .title("公告")
                .content("DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~DoDo祝你国庆节快乐~~")
                .radius(8)
                .widthScale(0.85f)
                .build()
                .show();
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

    private ArrayList<YoYo.YoYoString> mArrowAnimController = new ArrayList<>();//动画控制器
    /**
     * 分类相关
     */

    private int mLanguageIndex = 0;
    private int mTypeIndex = 0;
    private int mSourceIndex = 0;
    private int mFormIndex = 0;
    @BindView(R2.id.tvLanguage)
    AppCompatTextView mTvLanguage = null;//语言
    @BindView(R2.id.tvType)
    AppCompatTextView mTvType = null;//类型
    @BindView(R2.id.tvSource)
    AppCompatTextView mTvSource = null;//来源
    @BindView(R2.id.tvForm)
    AppCompatTextView mTvForm = null;//展示形式

    @OnClick(R2.id.llLanguage)
    void onLanguageClicked() {
        final int size = mLanguageIndexList.size();
        final String[] languageStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            languageStrArray[i] = ApiHelper.getLanguageStr(mLanguageIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换语言")
                .addItems(languageStrArray, mLanguageIndexList.indexOf(mLanguageIndex))
                .onSelected(this::onLanguageSelected)
                .onOpen(() -> switchArrow(0))
                .onClose(() -> switchArrow(0))
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    private void onLanguageSelected(int selectedIndex) {
        final int lastIndex = mLanguageIndex;
        mLanguageIndex = mLanguageIndexList.get(selectedIndex);

        requestData();

        YoYo.with(new TextScaleFadeInAnim())
                .onStart(animator -> mTvLanguage.setText(ApiHelper.getLanguageStr(lastIndex)))
                .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                        .onStart(animator1 -> mTvLanguage.setText(ApiHelper.getLanguageStr(mLanguageIndex)))
                        .interpolate(new DecelerateInterpolator())
                        .duration(300)
                        .playOn(mTvLanguage))
                .interpolate(new AccelerateInterpolator())
                .duration(300)
                .playOn(mTvLanguage);
    }

    @OnClick(R2.id.llType)
    void onTypeClicked() {
        final int size = mTypeIndexList.size();
        final String[] typeStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            typeStrArray[i] = ApiHelper.getTypeStr(mTypeIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换版本")
                .addItems(typeStrArray, mTypeIndexList.indexOf(mTypeIndex))
                .onSelected(this::onTypeSelected)
                .onOpen(() -> switchArrow(1))
                .onClose(() -> switchArrow(1))
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    private void onTypeSelected(int selectedIndex) {
        final int lastIndex = mTypeIndex;
        mTypeIndex = mTypeIndexList.get(selectedIndex);

        requestData();

        YoYo.with(new TextScaleFadeInAnim())
                .onStart(animator -> mTvType.setText(ApiHelper.getTypeStr(lastIndex)))
                .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                        .onStart(animator1 -> mTvType.setText(ApiHelper.getTypeStr(mTypeIndex)))
                        .interpolate(new DecelerateInterpolator())
                        .duration(300)
                        .playOn(mTvType))
                .interpolate(new AccelerateInterpolator())
                .duration(300)
                .playOn(mTvType);
    }

    @OnClick(R2.id.llSource)
    void onSourceClicked() {
        final int size = mSourceIndexList.size();
        final String[] sourceStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            sourceStrArray[i] = ApiHelper.getSourceStr(mSourceIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换平台")
                .addItems(sourceStrArray, mSourceIndexList.indexOf(mSourceIndex))
                .onSelected(this::onSourceSelected)
                .onOpen(() -> switchArrow(2))
                .onClose(() -> switchArrow(2))
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    private void onSourceSelected(int selectedIndex) {
        final int lastIndex = mSourceIndex;
        mSourceIndex = mSourceIndexList.get(selectedIndex);

        requestData();

        YoYo.with(new TextScaleFadeInAnim())
                .onStart(animator -> mTvSource.setText(ApiHelper.getSourceStr(lastIndex)))
                .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                        .onStart(animator1 -> mTvSource.setText(ApiHelper.getSourceStr(mSourceIndex)))
                        .interpolate(new DecelerateInterpolator())
                        .duration(300)
                        .playOn(mTvSource))
                .interpolate(new AccelerateInterpolator())
                .duration(300)
                .playOn(mTvSource);
    }

    @OnClick(R2.id.llForm)
    void onFormClicked() {
        final int size = mFormIndexList.size();
        final String[] formStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            formStrArray[i] = ApiHelper.getFormStr(mFormIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换列表")
                .addItems(formStrArray, mFormIndexList.indexOf(mFormIndex))
                .onSelected(this::onFormSelected)
                .onOpen(() -> switchArrow(3))
                .onClose(() -> switchArrow(3))
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    private void onFormSelected(int selectedIndex) {
        final int lastIndex = mFormIndex;
        mFormIndex = mFormIndexList.get(selectedIndex);

        YoYo.with(new TextScaleFadeInAnim())
                .onStart(animator -> mTvForm.setText(ApiHelper.getFormStr(lastIndex)))
                .onEnd(animator -> YoYo.with(new TextScaleFadeOutAnim())
                        .onStart(animator1 -> mTvForm.setText(ApiHelper.getFormStr(mFormIndex)))
                        .onEnd(animator12 -> requestData())
                        .interpolate(new DecelerateInterpolator())
                        .duration(300)
                        .playOn(mTvForm))
                .interpolate(new AccelerateInterpolator())
                .duration(300)
                .playOn(mTvForm);
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

        final YoYo.YoYoString lastAnim = mArrowAnimController.get(index);
        if (lastAnim != null && lastAnim.isRunning()) {
            lastAnim.stop();
        }

        final YoYo.YoYoString anim = YoYo.with(new RotateOpenArrowAnim())
                .interpolate(new AccelerateDecelerateInterpolator())
                .duration(600)
                .playOn(arrow);

        mArrowAnimController.set(index, anim);
    }

    private void startArrowCloseAnim(int index) {
        final IconTextView arrow = getArrow(index);
        if (arrow == null) {
            return;
        }

        final YoYo.YoYoString lastAnim = mArrowAnimController.get(index);
        if (lastAnim != null && lastAnim.isRunning()) {
            lastAnim.stop();
        }

        final YoYo.YoYoString anim = YoYo.with(new RotateCloseArrowAnim())
                .duration(300)
                .playOn(arrow);

        mArrowAnimController.set(index, anim);
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

    public int getLanguageIndex() {
        return mLanguageIndex;
    }

    public int getTypeIndex() {
        return mTypeIndex;
    }

    public int getSourceIndex() {
        return mSourceIndex;
    }

    public int getFormIndex() {
        return mFormIndex;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mArrowAnimController.clear();
    }

    private void onLoadSuccessStatus() {
        mStatusLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    private void onLoadFailureStatus() {
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingFailureLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
        mStatusLayout.setVisibility(View.VISIBLE);
    }

    private void onLoadingStatus() {
        mContentLayout.setVisibility(View.GONE);
        mLoadingFailureLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mStatusLayout.setVisibility(View.VISIBLE);
    }

}
