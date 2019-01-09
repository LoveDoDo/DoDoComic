package com.dodo.xinyue.conan.main.mine.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.main.mine.bean.MineBean;
import com.dodo.xinyue.conan.module.setting.SettingDelegate;
import com.dodo.xinyue.conan.view.dialog.loading.ConanLoadingDialog;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.net.loader.DoDoLoader;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulItemClickListener;

/**
 * MineItemClickListener
 *
 * @author DoDo
 * @date 2018/10/25
 */
public class MineItemClickListener extends MulItemClickListener {

    private MineItemClickListener(DoDoDelegate delegate) {
        super(delegate);
    }

    public static MineItemClickListener create(DoDoDelegate delegate) {
        return new MineItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position, MulEntity entity, int itemViewType) {
        final MineBean bean = entity.getBean();
//        ToastUtils.showShort(String.valueOf(position));
        switch (position) {
            case 0:
                ConanLoadingDialog.builder()
                        .anim(-1)
                        .backgroundDimEnabled(false)
                        .build()
                        .show();
                break;
            case 1:
                DoDoLoader.showLoading(DoDo.getActivity());
                break;
            case 2:

                break;
            case 5:
                DELEGATE.getParentDelegate().start(new SettingDelegate());
                break;
            default:
                break;
        }
    }
}
