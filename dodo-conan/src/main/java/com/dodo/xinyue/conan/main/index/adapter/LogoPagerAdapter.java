package com.dodo.xinyue.conan.main.index.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;

/**
 * LogoPagerAdapter
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class LogoPagerAdapter extends PagerAdapter {
    /**
     * FragmentPagerAdapter:销毁视图
     * FragmentStatePagerAdapter:销毁视图+实例
     * <p>
     * setsetOffscreenPageLimit():预加载 同时保存的实例(视图)数 默认是1 也就是最多保存3个实例(在中间位置) 超过3个会根据选择的Adapter判断是销毁视图还是实例
     */

    private final int[] IMG_RES;

    public LogoPagerAdapter(int[] imgRes) {
        this.IMG_RES = imgRes;
    }

    @Override
    public int getCount() {
        return IMG_RES.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final AppCompatImageView iv = new AppCompatImageView(container.getContext());
        iv.setImageResource(IMG_RES[position]);
//        if (position == 1) {
//            iv.setOnClickListener(v -> ToastUtils.showShort("恭喜找到第二条线索"));
//        }
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
