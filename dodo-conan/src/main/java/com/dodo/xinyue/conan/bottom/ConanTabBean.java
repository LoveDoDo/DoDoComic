package com.dodo.xinyue.conan.bottom;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.core.delegates.bottom.bean.BottomTabIconTitleBean;

/**
 * ConanTabBean
 *
 * @author DoDo
 * @date 2018/10/2
 */
public class ConanTabBean extends BottomTabIconTitleBean {

    public ConanTabBean(CharSequence icon, CharSequence title) {
        super(icon, title);
    }

    @Override
    public Object setTabLayout() {
        return R.layout.item_bottom_tab_conan;
    }

}
