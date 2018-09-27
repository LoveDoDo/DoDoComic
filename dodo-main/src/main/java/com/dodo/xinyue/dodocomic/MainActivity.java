package com.dodo.xinyue.dodocomic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dodo.xinyue.core.activitys.ProxyActivity;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.dodocomic.launch.BeforeLaunchDelegate;

import me.yokeyword.fragmentation.anim.FragmentAnimator;
import qiu.niorgai.StatusBarCompat;

/**
 * 全局唯一Activity
 *
 * @author DoDo
 * @date 2018/09/12
 */
public class MainActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全局配置
        DoDo.getConfigurator().withActivity(this);
        //透明状态栏
        StatusBarCompat.translucentStatusBar(this, true);
        //设置delegate默认背景色
        getSupportDelegate().setDefaultFragmentBackground(R.color.default_fragment_background);
    }

    @Override
    public DoDoDelegate setRootDelegate() {
//        final TestBean bean = new TestBean();
//        bean.setTvQipuId("1088268004");
//        bean.setVid("db15bf19135042a089dc1adfecc89cef");
//        return ThumbPreviewDelegate.create(bean);
        return new BeforeLaunchDelegate();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.global_enter);
        fragmentAnimator.setExit(R.anim.global_exit);
        fragmentAnimator.setPopEnter(R.anim.global_pop_enter);
        fragmentAnimator.setPopExit(R.anim.global_pop_exit);
        return fragmentAnimator;
    }
}
