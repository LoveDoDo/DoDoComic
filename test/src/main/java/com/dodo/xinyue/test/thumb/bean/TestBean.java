package com.dodo.xinyue.test.thumb.bean;

import java.io.Serializable;

/**
 * TestBean
 *
 * @author DoDo
 * @date 2018/9/23
 */
public class TestBean implements Serializable {

    private String tvQipuId = null;
    private String vid = null;

    public TestBean() {
    }

    public String getTvQipuId() {
        return tvQipuId;
    }

    public void setTvQipuId(String tvQipuId) {
        this.tvQipuId = tvQipuId;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
