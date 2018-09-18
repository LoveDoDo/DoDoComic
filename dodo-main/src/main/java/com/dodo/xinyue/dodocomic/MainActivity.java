package com.dodo.xinyue.dodocomic;

import com.dodo.xinyue.core.activitys.ProxyActivity;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.test.thumb.ThumbPreviewDelegate;

/**
 * 全局唯一Activity
 *
 * @author DoDo
 * @date 2018/09/12
 */
public class MainActivity extends ProxyActivity {

    @Override
    public DoDoDelegate setRootDelegate() {
        return new ThumbPreviewDelegate();
    }
}
