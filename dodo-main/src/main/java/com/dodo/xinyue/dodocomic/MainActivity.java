package com.dodo.xinyue.dodocomic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.main.ConanBottomDelegate;
import com.dodo.xinyue.core.activitys.ProxyActivity;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.dodocomic.launch.BeforeLaunchDelegate;

import cn.jpush.android.api.JPushInterface;
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
        if (ApiHelper.isQuicklyOpenApp()) {
            return new ConanBottomDelegate();
        }
        return new BeforeLaunchDelegate();
    }

    /**
     * 防止内容不足，Activity销毁重建，由于保存的fragment的状态，导致fragment发生重叠问题
     */
    @Override
    public Class<? extends DoDoDelegate> getMayBeExistDelegate() {
        return ConanBottomDelegate.class;
    }

    /**
     * 全部是设置当前Fragment的动画,和之前之后的Fragment无关
     * <p>
     * 参数解释：
     * enter: Fragment的进栈动画
     * exit: Fragment的出栈动画(pop时的)
     * popEnter: 下一个Fragment出栈时，该Fragment从hide状态变为show状态时的动画
     * popExit：下一个Fragment进栈时，该Fragment从show变为hide状态时的动画
     * <p>
     * A -> B(当前Fragment) -> C
     * enter:    A -> B(进栈) B的进栈动画
     * exit:     A <- B(pop) B的出栈动画
     * popEnter: B <- C(pop) B的伪进栈动画(重回栈顶)
     * popExit： B -> C(进栈) B的伪出栈动画(不再是栈顶)
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = super.onCreateFragmentAnimator();
        fragmentAnimator.setEnter(R.anim.global_enter);
        fragmentAnimator.setExit(R.anim.global_exit);
        fragmentAnimator.setPopEnter(R.anim.global_pop_enter);
        fragmentAnimator.setPopExit(R.anim.global_pop_exit);
        return fragmentAnimator;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}
