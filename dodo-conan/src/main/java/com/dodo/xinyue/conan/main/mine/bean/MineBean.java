package com.dodo.xinyue.conan.main.mine.bean;

/**
 * MineBean
 *
 * @author DoDo
 * @date 2018/10/25
 */
public class MineBean {

    private String icon;
    private String title;

    public MineBean(String icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
