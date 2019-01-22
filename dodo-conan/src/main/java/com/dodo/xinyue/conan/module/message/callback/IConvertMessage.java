package com.dodo.xinyue.conan.module.message.callback;

import com.dodo.xinyue.core.ui.recycler.MulEntity;

import java.util.List;

/**
 * IConvertMessage
 *
 * @author DoDo
 * @date 2019/1/22
 */
public interface IConvertMessage {

    void onCompleted(List<MulEntity> data);

}
