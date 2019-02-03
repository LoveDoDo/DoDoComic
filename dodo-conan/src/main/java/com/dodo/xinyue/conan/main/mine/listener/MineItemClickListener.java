package com.dodo.xinyue.conan.main.mine.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.main.mine.bean.MineBean;
import com.dodo.xinyue.conan.module.message.MessageDetailDelegate;
import com.dodo.xinyue.conan.module.web.ConanWebDelegate;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
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
                DELEGATE.getParentDelegate().start(MessageDetailDelegate.create(JiGuangMessage.TYPE_INFERENCE));
                break;
            case 1:
                DELEGATE.getParentDelegate().start(MessageDetailDelegate.create(JiGuangMessage.TYPE_JOKE));
                break;
            case 2:
                DELEGATE.getParentDelegate().start(MessageDetailDelegate.create(JiGuangMessage.TYPE_ACTIVE));
                break;
            case 3:
                DELEGATE.getParentDelegate().start(MessageDetailDelegate.create(JiGuangMessage.TYPE_MOVIE));
                break;
            case 4:
                DELEGATE.getParentDelegate().start(ConanWebDelegate.create("https://tieba.baidu.com/f?kw=dodo%E5%8A%A8%E6%BC%AB&ie=utf-8","DoDo动漫吧"));
                break;
            case 5:
                DELEGATE.getParentDelegate().start(ConanWebDelegate.create("https://tieba.baidu.com/f?kw=%E6%9F%AF%E5%8D%97&ie=utf-8","柯南吧"));
                break;
            default:
                break;
        }
    }
}
