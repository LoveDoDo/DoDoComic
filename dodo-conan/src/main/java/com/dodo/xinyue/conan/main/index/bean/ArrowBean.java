package com.dodo.xinyue.conan.main.index.bean;

/**
 * ArrowBean
 *
 * @author DoDo
 * @date 2018/10/4
 */
public class ArrowBean {

    public static final int STATUS_CLOSE = 0;//默认
    public static final int STATUS_OPEN = 1;

    private int status = STATUS_CLOSE;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
