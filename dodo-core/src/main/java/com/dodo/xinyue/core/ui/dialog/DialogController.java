package com.dodo.xinyue.core.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.annimon.stream.Stream;
import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.util.dimen.DimenUtil;

import java.util.ArrayList;

/**
 * DialogController
 *
 * @author DoDo
 * @date 2018/1/11
 */
public class DialogController {

    //Loader容器，用于统一管理
    private static final ArrayList<Dialog> DIALOGS = new ArrayList<>();

    public static void showDialog(BaseDialog dialog) {

        dialog.show();


//        if (style == DialogStyle.NORMAL_1) {
//            final NormalDialog dialog = new NormalDialog(context);
//            dialog.title(title)
//                    .content(content)
//                    .show();
//        } else if (style == DialogStyle.IMAGE) {
//            final ImageDialog imageDialog = new ImageDialog(context);
//            imageDialog.setCanceledOnTouchOutside(true);
//            imageDialog.giftContent(content)
//                    .show();
//        } else {
//            ToastUtils.showShort("DialogStyle为空");
//        }


//        test(context);
//        testDialog(context);
//        new CustomBaseDialog(context, false).show();
//        final NormalDialog dialog = new NormalDialog(context);
//        dialog.content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")
//                .style(NormalDialog.STYLE_ONE)
//                .titleTextSize(23)
//                .showAnim(new ZoomInAnim())
//                .dismissAnim(new ZoomOutAnim())
//                .show();
//
//        dialog.setOnBtnClickL(
//                () -> {
//                    ToastUtils.showShort("left");
//                    dialog.cancel();
//                },
//                () -> {
//                    ToastUtils.showShort("right");
//                    dialog.cancel();
//                });

    }

    private static void testDialog(Context context) {

    }

    private static void test(Context context) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final View view = View.inflate(context, 0, null);
        //将avLoadingIndicatorView设置为dialog的根视图
        dialog.setContentView(view);

        //设置dialog布局参数
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = (int) (deviceWidth * 1f);
//            lp.height = deviceHeight;
            //偏移
//            lp.height += deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
//            lp.gravity = Gravity.LEFT | Gravity.TOP;
//            lp.x = 100;
//            lp.y = 100;
        }

//        LOADERS.add(dialog);
//        dialog.setCancelable(true);//设置点击后不可取消
        dialog.show();
    }

    public static void stopDialog() {
        Stream.of(DIALOGS).forEach(dialog -> {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();//有回调
//                    dialog.dismiss();//无回调
                }
            }
        });
    }

}
