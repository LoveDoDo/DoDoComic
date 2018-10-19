package com.dodo.xinyue.conan.main.index.bean;

import java.io.Serializable;

/**
 * IndexBean
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexBean implements Serializable{

    private long id;//唯一id
    private String number;//集数
    private String title;//标题
    private String cover;//封面图
    private String tvQipuId;//缩略图用
    private String vid;//缩略图用

    private String language;
    private String type;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
