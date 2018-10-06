package com.dodo.xinyue.core.ui.dialog.list.bean;

/**
 * ThumbPreviewBean
 *
 * @author DoDo
 * @date 2018/9/19
 */
public class ListDialogBean {

    /**
     * item内容
     */
    private String mContent = null;
    /**
     * 当前item是否被选中
     */
    private boolean mSelected = false;

    public ListDialogBean(String content, boolean selected) {
        this.mContent = content;
        this.mSelected = selected;
    }

    public String getTitle() {
        return mContent;
    }

    public void setTitle(String title) {
        this.mContent = title;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
    }
}

