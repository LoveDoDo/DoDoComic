package com.dodo.xinyue.conan.module.setting.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.module.BaseModuleDelegate;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.dodo.xinyue.core.util.file.FileUtil;

import butterknife.BindView;

/**
 * SaySomethingDelegate
 *
 * @author DoDo
 * @date 2019/1/14
 */
public class SaySomethingDelegate extends BaseModuleDelegate {

    @BindView(R2.id.tvContent)
    TextView mTvContent = null;
    @BindView(R2.id.iv)
    ImageView mIv = null;

    public static SaySomethingDelegate create() {
        return new SaySomethingDelegate();
    }

    @Override
    public Object setChildLayout() {
        return R.layout.delegate_say_something;
    }

    @Override
    public String setTitle() {
        return "作者的话";
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);

        final int deviceWidth = DimenUtil.getScreenWidth();
        final int imageHeight = deviceWidth * 439 / 670;
        mIv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageHeight));

        String sayContent = FileUtil.getRawFile(R.raw.say);
        String[] tempArray = sayContent.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tempArray.length; i++) {
            sb.append("\t\t\t\t");//一个汉字=\t\t
            sb.append(tempArray[i].trim());
            if (i != tempArray.length - 1) {
                //除了最后一行
                sb.append("\n");
            }
        }
        mTvContent.setText(sb.toString());
    }

}
