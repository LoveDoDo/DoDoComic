package com.dodo.xinyue.conan.view.dialog.loading;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.core.ui.dialog.base.BaseDialog;
import com.dodo.xinyue.core.ui.dialog.bean.DialogPublicParamsBean;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;

/**
 * ConanLoadingDialog
 *
 * @author DoDo
 * @date 2018/10/28
 */
public class ConanLoadingDialog extends BaseDialog {

    private ObjectAnimator mRotationAnim = null;

    @BindView(R2.id.tvProgress)
    IconTextView mTvProgress = null;

    public ConanLoadingDialog(DialogPublicParamsBean bean) {
        super(bean);
    }

    public static ConanLoadingDialogBuilder builder() {
        return new ConanLoadingDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    public void onBindView(View rootView) {

        mRotationAnim = ObjectAnimator.ofFloat(mTvProgress, "rotation", 0, 360);
        mRotationAnim.setDuration(1314);
        mRotationAnim.setRepeatCount(Animation.INFINITE);//无限循环
        mRotationAnim.setRepeatMode(ValueAnimator.RESTART);//重复动画
        mRotationAnim.setInterpolator(new LinearInterpolator());//覆盖默认的AccelerateDecelerateInterpolator()
        mRotationAnim.start();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mRotationAnim != null) {
            mRotationAnim.cancel();
            mRotationAnim = null;
        }
    }
}
