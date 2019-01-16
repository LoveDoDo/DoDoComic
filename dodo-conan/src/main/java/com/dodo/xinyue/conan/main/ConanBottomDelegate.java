package com.dodo.xinyue.conan.main;

import android.os.Bundle;
import android.view.Gravity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.bottom.ConanTabBean;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.main.index.IndexDelegate;
import com.dodo.xinyue.conan.main.manhua.ManhuaDelegate;
import com.dodo.xinyue.conan.main.mine.MineDelegate;
import com.dodo.xinyue.conan.main.mine.event.NewMessageEvent;
import com.dodo.xinyue.conan.main.yingyin.YingyinDelegate;
import com.dodo.xinyue.conan.view.dialog.download.ConanDownloadDialog;
import com.dodo.xinyue.conan.view.dialog.message.ConanMessageDialog;
import com.dodo.xinyue.conan.view.dialog.update.ConanUpdateDialog;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomDelegate;
import com.dodo.xinyue.core.delegates.bottom.BaseBottomItemDelegate;
import com.dodo.xinyue.core.delegates.bottom.bean.BaseBottomTabBean;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsBuilder;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomBarParamsType;
import com.dodo.xinyue.core.delegates.bottom.builder.BottomTabBeanBuilder;
import com.dodo.xinyue.core.delegates.bottom.options.BottomTabBeanOptions;
import com.dodo.xinyue.core.util.CommonUtil;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.download.DownloadTask;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.WeakHashMap;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * ConanBottomDelegate
 *
 * @author DoDo
 * @date 2018/10/1
 */
public class ConanBottomDelegate extends BaseBottomDelegate {

    //寻宝模式 双击间隔时间 默认2秒
    private static final long WAIT_TIME = 2000L;
    //寻宝模式 记录上次点击的时间
    private long mLastTouchTime = 0;
    //寻宝模式 点击次数
    private int mTouchCount = 1;
    //寻宝模式 目标次数
    private int mTargetCount = 8;

    //TODO 相同大小的iconSize在布局中不一定一样大(找了好久的bug...)

    private static final BottomTabBeanOptions TAB_OPTIONS =
            new BottomTabBeanOptions()
                    .setTextSelector(R.color.selector_bottom_bar_text_color)
                    .setContainerSelector(R.drawable.selector_bottom_bar_bg)
                    .setIconSelector(R.color.selector_bottom_bar_icon_color)
                    .setIconSize(30)
                    .setTextSize(13)
                    .setTabGravity(Gravity.BOTTOM);

    @Override
    public void onRestoreStatus(ArrayList<BaseBottomItemDelegate> delegates) {
        delegates.set(0, findChildFragment(IndexDelegate.class));
        delegates.set(1, findChildFragment(YingyinDelegate.class));
        delegates.set(2, findChildFragment(ManhuaDelegate.class));
        delegates.set(3, findChildFragment(MineDelegate.class));
    }

    @Override
    public ArrayList<BaseBottomTabBean> setTabBeans() {
        final ArrayList<BaseBottomTabBean> tabBeans = new ArrayList<>();
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-home}", "首页", R.raw.home))
                        .setOptions(TAB_OPTIONS)
                        .build()
        );
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-yingyin}", "影音", R.raw.yingyin))
                        .setOptions(TAB_OPTIONS)
                        .build()

        );
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-manhua}", "漫画", R.raw.comic))
                        .setOptions(TAB_OPTIONS)
                        .build()

        );
        tabBeans.add(
                BottomTabBeanBuilder.builder()
                        .setTabBean(new ConanTabBean("{icon-mine}", "我的", R.raw.mine))
                        .setOptions(TAB_OPTIONS)
                        .build()

        );
        return tabBeans;
    }

    @Override
    public int setBackgroundRes() {
        return R.drawable.yueyue;
    }

    @Override
    public ArrayList<BaseBottomItemDelegate> setItemDelegates() {
        final ArrayList<BaseBottomItemDelegate> delegates = new ArrayList<>();
        delegates.add(new IndexDelegate());
        delegates.add(new YingyinDelegate());
        delegates.add(new ManhuaDelegate());
        delegates.add(new MineDelegate());
        return delegates;
    }

    @Override
    public WeakHashMap<BottomBarParamsType, Object> setBottomBar(BottomBarParamsBuilder builder) {
        return builder
                .setTabContainerHeight(64)
                .setBottomBarHeight(64)
                .build();
    }

    @Override
    public boolean onTabSelected(int position, boolean isRepeat) {
        if (position == 3) {
            if (System.currentTimeMillis() - mLastTouchTime < WAIT_TIME) {
                mLastTouchTime = System.currentTimeMillis();
                mTouchCount++;
                if (mTouchCount > 3) {
                    ToastUtils.showShort("再按 " + (mTargetCount - mTouchCount) + " 次开启寻宝模式");
                }
                if (mTouchCount == mTargetCount) {
                    ToastUtils.showShort("恭喜你开启寻宝模式");
                    mTouchCount = 0;
                }
            } else {
                mLastTouchTime = System.currentTimeMillis();
                mTouchCount = 1;
            }
        }

        return super.onTabSelected(position, isRepeat);
    }

    @Override
    public Class<? extends DoDoDelegate> getMayBeExistDelegate() {
        return IndexDelegate.class;
    }

    @Override
    public int setFirstPageIndex() {
        return 0;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        if (!ApiHelper.isQuicklyOpenApp()) {
            fragmentAnimator.setEnter(R.anim.conan_bottom_enter);
        } else {
            fragmentAnimator.setEnter(R.anim.no_anim);
        }
        return fragmentAnimator;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        if (ApiHelper.isQuicklyOpenApp()) {
            getProxyActivity().removeWindowBackground();
        }
    }

    /**
     * 展示新消息
     * <p>
     * sticky = true 粘性消息 先发送消息再订阅，也可以收到消息，收到后需要移除消息，不然每次重新订阅都会收到
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onShowMessageEvent(JiGuangMessage event) {
        EventBusActivityScope.getDefault(DoDo.getActivity()).removeStickyEvent(event);
        EventBusActivityScope.getDefault(DoDo.getActivity()).postSticky(new NewMessageEvent());
        final JSONObject extraData = JSON.parseObject(event.getExtraData());
        final int type = event.getType();
        switch (type) {
            case JiGuangMessage.TYPE_NOTICE:
                //公告
                ConanMessageDialog.builder()
                        .title(event.getTitle())
                        .content(event.getContent())
                        .isStart(extraData.getBooleanValue("start"))//默认值必须是false
                        .isHtml(extraData.getBooleanValue("html"))
                        .action(event.getAction())
                        .copyContent(extraData.getString("copy_content"))
                        .copyTips(extraData.getString("copy_tips"))
                        .radius(8)
                        .widthScale(0.85f)
                        .build()
                        .show();

                //TODO 待完成：1、ConanMessageDialog点击操作 复制 跳转WebView 2、UpdateDialog弹出时如果有公告Dialog显示，则存为PengdingDialog pendingShow setPendingDialog 公告Dialog消失要检查pendingDialog是否为空，不为空就显示出来
                //TODO 可以给BaseDialog添加一个属性，isCancelLastDialog

                break;
            case JiGuangMessage.TYPE_NONE:
                //其他

                break;
            default:
                break;
        }
        final int form = ApiHelper.getMessageForm();
        switch (form) {
            case JiGuangMessage.FORM_DIALOG:

                break;
            case JiGuangMessage.FORM_NOTIFICATION:

                break;
            default:
                break;
        }

    }

    /**
     * Bugly新版本更新消息
     * <p>
     * sticky = true 粘性消息 先发送消息再订阅，也可以收到消息，收到后需要移除消息，不然每次重新订阅都会收到
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceivedBuglyUpgradeEvent(UpgradeInfo upgradeInfo) {
        EventBusActivityScope.getDefault(DoDo.getActivity()).removeStickyEvent(upgradeInfo);

        final int versionCode = upgradeInfo.versionCode;
        final String versionName = upgradeInfo.versionName;
        final String packageSize = Beta.getStrategyTask().getStatus() == DownloadTask.COMPLETE ? "-1" : CommonUtil.getFormatSize(upgradeInfo.fileSize, 2);
        final String downloadPath = upgradeInfo.apkUrl;
        ConanUpdateDialog.builder()
                .title(AppUtils.getAppName() + "更新啦~")
                .content("欢迎升级" + AppUtils.getAppName() + versionName + " 最新版\n" + upgradeInfo.newFeature)
                .versionName(versionName)
                .packageSize(packageSize)
                .confirm(() -> startUpdate(packageSize))
                .bottomLeftRadius(8)
                .bottomRightRadius(8)
                .widthScale(0.85f)
                .build()
                .show();
    }

    private void startUpdate(String packageSize) {
//        FileUtils.deleteFile(Beta.getStrategyTask().getSaveFile());
        if (Beta.getStrategyTask().getStatus() == DownloadTask.COMPLETE) {
            Beta.installApk(Beta.getStrategyTask().getSaveFile());
            return;
        }
        ConanDownloadDialog.builder()
                .packageSize(packageSize)
                .radius(8)
                .widthScale(0.85f)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .build()
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusActivityScope.getDefault(DoDo.getActivity()).register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusActivityScope.getDefault(DoDo.getActivity()).unregister(this);
    }

    @Override
    public boolean isTrack() {
        return false;
    }
}
