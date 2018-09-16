package com.dodo.xinyue.dodocomic;

import android.app.Application;

import com.dodo.xinyue.core.app.DoDo;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * MainApp
 *
 * @author DoDo
 * @date 2018/9/16
 */
public class MainApp extends Application {

    /**TODO
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
//                .withIcon(new FontComicModule())//自定义图标库
//                .withIcon(new FontSignInModule())//登录页
//                .withIcon(new FontGoodsDetailModule())//商品详情页
                .withLoaderDelayed(1000)
                .withWebApiHost("http://192.168.75.101:5555/DoDoComic/")//以"/"结尾
//                .withInterceptor(new DebugInterceptor("intercept", R.raw.test))
//                .withInterceptor(new QihooInterceptor())
//                .withInterceptor(new EcInterceptor())
//                .withInterceptor(new JiGuangInterceptor())
//                .withInterceptor(new IMInterceptor())
                .withJavascriptInterface("dodo")//JS
//                .withWebEvent("Web调用了原生",new Web2AndroidEvent())
//                .withWebEvent("share", new ShareEvent())
                .configure();

        initStetho();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

//        DoDo.getConfigurator().withRefWatcher(LeakCanary.install(this));

        // Normal app init code...
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


//        DatabaseManager.getInstance().init(this);

    }

    /**
     * Stetho官网：http://facebook.github.io/stetho/
     * 查看入口：chrome://inspect
     * 注：需要能访问google
     *
     * 网页抓包，可视化查看数据库，视图布局等
     *
     * 抓包需要添加network拦截器
     * BUILDER.addNetworkInterceptor(new StethoInterceptor())
     *
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
