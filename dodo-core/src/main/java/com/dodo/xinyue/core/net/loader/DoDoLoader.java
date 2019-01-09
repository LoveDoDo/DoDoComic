package com.dodo.xinyue.core.net.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * 进度条的dialog
 *
 * @author DoDo
 * @date 2017/8/31
 */
public class DoDoLoader {

    //缩放比
    private static final int LOADER_SIZE_SCALE = 5;
    //偏移比
    private static final int LOADER_OFFSET_SCALE = 10;
    //Loader容器，用于统一管理
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    //默认Loader样式
    private static final String DEFAULT_LOADER = LoaderStyle.BallScaleIndicator.name();

    /**
     * 传入枚举类型，方便
     *
     * @param context
     * @param type
     */
    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    /**
     * 显示进度条
     * context不能传Application的context,否则显示WebView的时候会报错
     *
     * @param context
     * @param type
     */
    public static void showLoading(Context context, String type) {

        //这里的dialog作为一个容器，用于承载并显示avLoadingIndicatorView
        //必须自定义dialog的style，不然显示会出问题
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(context, type);
        //将avLoadingIndicatorView设置为dialog的根视图
        dialog.setContentView(avLoadingIndicatorView);

        //设置dialog布局参数
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            //偏移
//            lp.height += deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);
//        dialog.setCancelable(false);//设置点击后不可取消
        dialog.show();
    }

    /**
     * 显示默认进度条
     *
     * @param context
     */
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    /**
     * 隐藏进度条
     */
    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();//有回调
//                    dialog.dismiss();//无回调
                }
            }
        }
    }


}

