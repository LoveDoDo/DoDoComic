package com.dodo.xinyue.dodocomic;

import android.app.Application;
import android.graphics.Color;
import android.os.Environment;
import android.view.Gravity;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.icon.ConanIconFontModule;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.ui.dialog.manager.DialogManager;
import com.dodo.xinyue.core.util.CommonUtil;
import com.dodo.xinyue.core.util.log.DoDoLogger;
import com.dodo.xinyue.dodocomic.database.DatabaseManager;
import com.dodo.xinyue.test.interceptor.TestInterceptor;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import cn.jpush.android.api.JPushInterface;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


/**
 * MainApp
 *
 * @author DoDo
 * @date 2018/9/16
 */
public class MainApp extends Application {

    private static final String TAG = "MainApp";
    private static final int DELAY_BUGLY_INIT = 18 * 1000;//Bugly初始化延时
    private static final long CHECK_UPDATE_CYCLE = 7 * 24 * 60 * 60 * 1000;//检查更新弹窗策略 7天内不弹窗

    /**
     * TODO
     * 插件：adb idea
     * 功能：清除缓存数据 重启adb
     * 快捷键：Ctrl+Alt+Shift+A
     */

    @Override
    public void onCreate() {
        super.onCreate();
        DoDo.init(this)
                .withIcon(new FontAwesomeModule())//自带的图标库，需添加相应依赖
//                .withIcon(new IoniconsModule())//自带的图标库，需添加相应依赖
                .withIcon(new ConanIconFontModule())
                .withLoaderDelayed(1000)
                .withNativeApiHost("http://127.0.0.1/")
                .withWebApiHost("http://192.168.75.101:5555/DoDoComic/")//以"/"结尾
                .withInterceptor(new TestInterceptor())
//                .withInterceptor(new IQiyiInterceptor())
//                .withInterceptor(new DebugInterceptor("intercept", R.raw.test))
                .withJavascriptInterface("dodo")//JS
//                .withWebEvent("Web调用了原生",new Web2AndroidEvent())
//                .withWebEvent("share", new ShareEvent())
                .configure();

        /**
         * 更新配置
         */
        final int configVersion = ApiHelper.getConfigVersion();
        if (configVersion != ApiConstants.CONFIG_VERSION) {
            ApiHelper.clearConfig();
            ApiHelper.setConfigVersion();
        }

        /**
         * 极光推送 初始化
         */
        JPushInterface.setDebugMode(true);//调试模式 init()之前调用 正式上线时要关闭
        JPushInterface.init(this);

        /**
         * 数据库初始化
         */
        DatabaseManager.getInstance().init(this);

        /**
         * Bugly 初始化
         */
        initBugly();

        /**
         * 友盟统计 初始化
         */
        initUmeng();

        /**
         * 自定义Toast
         */
        initToast();

//        Fragmentation.builder()
//                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
//                .stackViewMode(Fragmentation.BUBBLE)
//                // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
//                // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
//                .debug(BuildConfig.DEBUG) // 实际场景建议.debug(BuildConfig.DEBUG)
//                // 生产环境时，捕获上述异常（避免crash），会捕获
//                // 建议在回调处上传下面异常到崩溃监控服务器
//                .handleException(new ExceptionHandler() {
//                    @Override
//                    public void onException(Exception e) {
//                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
//                        // Bugtags.sendException(e);
//                    }
//                })
//                .install();

//        initStetho();
//
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }

//        DoDo.getConfigurator().withRefWatcher(LeakCanary.install(this));

    }

    /**
     * 自定义Toast
     */
    private void initToast() {
        ToastUtils.setMsgColor(Color.WHITE);
        ToastUtils.setMsgTextSize(14);//传sp即可
        ToastUtils.setBgResource(R.drawable.shape_toast);
        ToastUtils.setGravity(Gravity.BOTTOM,0,218);
    }

    /**
     * 初始化友盟统计
     */
    private void initUmeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         *
         * 参数1:上下文，必须的参数，不能为空。
         * 参数2:【友盟+】 AppKey，非必须参数，如果Manifest文件中已配置AppKey，该参数可以传空，则使用Manifest中配置的AppKey，否则该参数必须传入。
         * 参数3:【友盟+】 Channel，非必须参数，如果Manifest文件中已配置Channel，该参数可以传空，则使用Manifest中配置的Channel，否则该参数必须传入，Channel命名请详见Channel渠道命名规范。
         * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机。
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */
        UMConfigure.init(this, BuildConfig.UMENG_APP_KEY, "other", UMConfigure.DEVICE_TYPE_PHONE, null);
        //选用AUTO页面采集模式
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);//手动统计Activity和Fragment
        //关闭错误统计 使用Bugly
        MobclickAgent.setCatchUncaughtExceptions(false);// isEnable: false-关闭错误统计功能；true-打开错误统计功能（默认打开）
        //打开统计SDK调试模式
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
    }

    /**
     * 初始化Bugly
     * <p>
     * TODO 后台修改更新策略，大概需要半个小时才能更新。。
     */
    private void initBugly() {
        /**
         * 升级检查周期设置
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略
         */
//        Beta.upgradeCheckPeriod = 1 * 1000;

        /**
         * 延迟初始化
         * 设置启动延时为60s（默认延时3s），APP启动60s后初始化SDK，避免影响APP启动速度;
         * TODO 必须初始化SDK后才能调用Beta.checkUpgrade()检查更新，不然没反应
         */
        Beta.initDelay = DELAY_BUGLY_INIT;

//        Beta.largeIconId = R.mipmap.ic_launcher;//设置通知栏大图标
//        Beta.smallIconId = R.mipmap.ic_launcher;//设置状态栏小图标
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//设置sd卡的Download为更新资源存储目录
        Beta.showInterruptedStrategy = true;//设置点击过确认的弹窗在App下次启动自动检查更新时会再次显示。
        Beta.canShowUpgradeActs.add(MainActivity.class);//设置只允许在MainActivity上显示更新弹窗
        /**
         * 设置自定义升级对话框UI布局
         *
         * 注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
         * 特性图片：beta_upgrade_banner，如：android:tag="beta_upgrade_banner"
         * 标题：beta_title，如：android:tag="beta_title"
         * 升级信息：beta_upgrade_info 如： android:tag="beta_upgrade_info"
         * 更新属性：beta_upgrade_feature 如： android:tag="beta_upgrade_feature"
         * 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
         * 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
         */
//        Beta.upgradeDialogLayoutId = R.layout.dialog_update;
        /**
         * 设置是否显示消息通知
         * 如果你不想在通知栏显示下载进度，你可以将这个接口设置为false，默认值为true。
         */
        Beta.enableNotification = false;
        /**
         * 设置Wifi下自动下载
         * 如果你想在Wifi网络下自动下载，可以将这个接口设置为true，默认值为false。
         */
        Beta.autoDownloadOnWifi = false;
        /**
         * 设置是否显示弹窗中的apk信息
         * 如果你使用我们默认弹窗是会显示apk信息的，如果你不想显示可以将这个接口设置为false。
         */
        Beta.canShowApkInfo = false;
        /**
         * 关闭热更新能力
         * 升级SDK默认是开启热更新能力的，如果你不需要使用热更新，可以将这个接口设置为false。
         */
        Beta.enableHotfix = false;

        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy == null) {
                    DoDoLogger.d("没有更新");
                    if (isManual) {
                        //手动检测更新
                        ToastUtils.showShort("已经是最新版咯~");
                        DialogManager.getInstance().cancelLastDialog();
                    }
                    return;
                }
                DoDoLogger.d("发现新版本");
                if (isManual) {
                    //手动检测更新
                    ApiHelper.setCurrentCheckUpdateTimestamp();
                    EventBusActivityScope.getDefault(DoDo.getActivity()).postSticky(strategy);
                    return;
                }
                long lastCheckUpdateTimestamp = ApiHelper.getLastCheckUpdateTimestamp();
                if (System.currentTimeMillis() - lastCheckUpdateTimestamp > CHECK_UPDATE_CYCLE) {
                    ApiHelper.setCurrentCheckUpdateTimestamp();
                    EventBusActivityScope.getDefault(DoDo.getActivity()).postSticky(strategy);
                } else {
                    DoDoLogger.d("发现新版本 - 不弹窗(策略)");
                }

            }
        };
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
//                DoDoLogger.d("检测更新成功");
            }

            @Override
            public void onUpgradeFailed(boolean isManual) {
                DoDoLogger.d("检测更新失败");
                DialogManager.getInstance().cancelLastDialog();
                if (!NetworkUtils.isConnected()) {
                    ToastUtils.showShort("网络未连接，请联网后重试");
                } else {
                    ToastUtils.showShort("检测更新失败，请稍后重试");
                }

            }

            /**
             * 只有手动检测更新才会回调
             */
            @Override
            public void onUpgrading(boolean isManual) {
                DoDoLogger.d("检测更新中");
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                DoDoLogger.d("下载完成");
            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                DoDoLogger.d("没有新版本");
            }
        };
        // 获取当前包名
        String packageName = this.getPackageName();
        // 获取当前进程名
        String processName = CommonUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        Bugly.init(this, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);

        //保证Bugly初始化完成，防止无法检测更新
        DoDo.getConfigurator().withCustomAttr(ApiConstants.IS_BUGLY_INIT, false);
        DoDo.getHandler().postDelayed(() -> {
            DoDo.getConfigurator().withCustomAttr(ApiConstants.IS_BUGLY_INIT, true);
        }, DELAY_BUGLY_INIT + 3 * 1000);
    }

    /**
     * Stetho官网：http://facebook.github.io/stetho/
     * 查看入口：chrome://inspect
     * 注：需要能访问google
     * <p>
     * 网页抓包，可视化查看数据库，视图布局等
     * <p>
     * 抓包需要添加network拦截器
     * BUILDER.addNetworkInterceptor(new StethoInterceptor())
     */
    @SuppressWarnings("SpellCheckingInspection")
    private void initStetho() {
        //默认初始化
        Stetho.initializeWithDefaults(this);

        //支持Dumpapp，暂时用不到
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());

    }

}
