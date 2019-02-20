package com.dodo.xinyue.core.delegates.web.event;


import com.dodo.xinyue.core.util.log.DoDoLogger;

/**
 * UndefineEvent
 *
 * @author DoDo
 * @date 2017/10/30
 */
public class UndefineEvent extends BaseEvent {

    @Override
    public String execute(String params) {
        DoDoLogger.d("UndefineEvent", params);
        return null;
    }
}
