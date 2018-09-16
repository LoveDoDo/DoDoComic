package com.dodo.xinyue.core.delegates;

/**
 * 需要被其他delegate继承
 *
 * @author DoDo
 * @date 2017/8/30
 */
public abstract class DoDoDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends DoDoDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }

}
