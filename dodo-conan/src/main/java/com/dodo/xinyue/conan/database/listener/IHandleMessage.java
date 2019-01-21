package com.dodo.xinyue.conan.database.listener;

import com.dodo.xinyue.conan.database.bean.JiGuangMessage;

import java.util.List;

/**
 * IHandleMessage
 *
 * @author DoDo
 * @date 2019/1/21
 */
public interface IHandleMessage {

    void onSuccess(List<JiGuangMessage> result);

    void onFailure();

}
