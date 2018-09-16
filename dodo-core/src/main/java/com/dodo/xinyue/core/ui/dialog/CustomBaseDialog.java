package com.dodo.xinyue.core.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.widget.base.BaseDialog;

/**
 * 自定义Dialog基类
 *
 * @author DoDo
 * @date 2018/1/11
 */
public class CustomBaseDialog extends BaseDialog<CustomBaseDialog> {

    private TextView mTvCancel = null;
    private TextView mTvConfirm = null;

    public CustomBaseDialog(Context context) {
        super(context);
    }

    public CustomBaseDialog(Context context, boolean isPopupStyle) {
        super(context, isPopupStyle);
    }

    @Override
    public View onCreateView() {
//        widthScale(0.85f);
//        dimEnabled(false);
//        showAnim(new ZoomInEnter());
        widthScale(1);
        heightScale(1);
//        dismissAnim(new ZoomOutExit());
//        final View view = View.inflate(this.getContext(), R.layout.dialog_custom, null);

//        mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);
//        mTvConfirm = (TextView) view.findViewById(R.id.tv_exit);


//        view.setBackgroundDrawable(
//                CornerUtils.cornerDrawable(Color.parseColor("#000000"), dp2px(5)));
        ToastUtils.showShort("onCreateView()");
        return null;
    }

    @Override
    public void setUiBeforShow() {
        mTvCancel.setOnClickListener(v -> {
            ToastUtils.showShort("取消");
            dismiss();
        });

        mTvConfirm.setOnClickListener(v ->
                ToastUtils.showShort("确定")
        );
    }
}
