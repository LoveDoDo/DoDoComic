package com.dodo.xinyue.conan.main.index.view.dialog.list.bean;

/**
 * ThumbPreviewBean
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ListDialogBean {

    private String mTitle = null;
    private boolean mSelected = false;

    public ListDialogBean(String mTitle, boolean mSelected) {
        this.mTitle = mTitle;
        this.mSelected = mSelected;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
    }
}

