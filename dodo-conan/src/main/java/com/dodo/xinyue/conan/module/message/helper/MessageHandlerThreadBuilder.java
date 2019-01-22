package com.dodo.xinyue.conan.module.message.helper;

import android.os.Handler;

import java.util.ArrayList;

/**
 * MessageHandlerThreadBuilder
 *
 * @author DoDo
 * @date 2019/1/22
 */
public class MessageHandlerThreadBuilder {

    private ArrayList<Integer> mMessageTypes = null;
    private Handler mUIHandler = null;

    MessageHandlerThreadBuilder() {
    }

    public final MessageHandlerThreadBuilder messageTypes(ArrayList<Integer> messageTypes) {
        this.mMessageTypes = messageTypes;
        return this;
    }

    public final MessageHandlerThreadBuilder handler(Handler uiHandler) {
        this.mUIHandler = uiHandler;
        return this;
    }

    public final MessageHandlerThread build() {
        if (mMessageTypes == null) {
            throw new IllegalArgumentException("mMessageTypes不合法!");
        }
        if (mUIHandler == null) {
            throw new IllegalArgumentException("UIHandler不合法!");
        }
        return new MessageHandlerThread(
                mMessageTypes,
                mUIHandler
        );
    }

}
