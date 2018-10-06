package com.dodo.xinyue.core.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;

import com.dodo.xinyue.core.R;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 容器Activity
 *
 * @author DoDo
 * @date 2017/8/30
 */
public abstract class ProxyActivity extends AppCompatActivity
        implements ISupportActivity {

    private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

    public abstract DoDoDelegate setRootDelegate();

    /**
     * 注意这里有一个重载的onCreate方法，Activity启动时走一个参数的方法
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DELEGATE.onCreate(savedInstanceState);
        //防止app进入后台重新打开发生重启的现象
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        initContainer(savedInstanceState);
    }

    /**
     * 设置Activity布局 &加载根delegate
     */
    private void initContainer(@Nullable Bundle savedInstanceState) {
        @SuppressLint("RestrictedApi")
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        //装载根Activity视图
        setContentView(container);

        //第一次加载
        if (savedInstanceState == null) {
            //加载根delegate TODO 将根delegate加入到回退栈,方便测试（找了好久的bug。。。）
            DELEGATE.loadRootFragment(R.id.delegate_container, setRootDelegate(),true,true);
        }
    }

    @Override
    protected void onDestroy() {
        DELEGATE.onDestroy();
        super.onDestroy();

        //释放Configurator持有的Activity等引用(重启会崩溃)
//        DoDo.getConfigurator().destroy();
//        DoDoLogger.d("销毁");

        //释放资源
        System.gc();//垃圾回收（回收时间不确定）
        System.runFinalization();//对已经失去引用的对象强制调用该对象的finalize方法
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
//        DoDoLogger.d("回收");
    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(new DefaultHorizontalAnimator());
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable) {
        DELEGATE.post(runnable);
    }

    @Override
    public void onBackPressedSupport() {
        DELEGATE.onBackPressedSupport();
    }

    @Override
    public void onBackPressed() {
        DELEGATE.onBackPressed();
    }
}
