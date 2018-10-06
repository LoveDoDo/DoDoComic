package com.dodo.xinyue.conan.main.index.view.dialog;

import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.main.index.view.dialog.bean.DialogPublicParamsBean;
import com.dodo.xinyue.conan.main.index.view.dialog.builder.ImageDialogBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * DoDoListDialog
 *
 * @author DoDo
 * @date 2018/10/4
 */
public class DoDoImageDialog extends BaseDialog {

    @BindView(R2.id.title)
    TextView mTvTitle = null;

    @OnClick(R2.id.tvConfirm)
    void onConfirmClicked() {
        dismiss();
    }

    private final String mTitle;

    public DoDoImageDialog(DialogPublicParamsBean bean, String mTitle) {
        super(bean);
        this.mTitle = mTitle;
    }

    public static <T extends ImageDialogBuilder> T builder(Class<T> tClass) {
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static ImageDialogBuilder builder() {
        return new ImageDialogBuilder();
    }

    @Override
    public Object setLayout() {
        return R.layout.test_dialog_image;
    }

    @Override
    public void onBindView(View rootView) {
        mTvTitle.setText(mTitle);
    }
}
