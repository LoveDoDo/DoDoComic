package com.dodo.xinyue.core.delegates.web;

/**
 * IPageLoadListener
 *
 * @author DoDo
 * @date 2017/10/31
 */
public interface IPageLoadListener {

    void onLoadStart();

    void onLoadEnd(boolean isLoadError);

}
