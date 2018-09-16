package com.dodo.xinyue.core.ui.recycler;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 数据转换 约束
 *
 * @author DoDo
 * @date 2017/9/9
 */
public abstract class DataConverter {

    protected final ArrayList<MulEntity> ENTITIES = new ArrayList<>();

    private String mJsonData = null;

    public abstract ArrayList<MulEntity> convert();

    protected String getJsonData() {
        if (TextUtils.isEmpty(mJsonData)) {
            throw new NullPointerException("JSON DATA IS NULL OR EMPTY!");
        }
        return mJsonData;
    }

    public DataConverter setJsonData(String jsonData) {
        this.mJsonData = jsonData;
        return this;
    }

    public void clearData() {
        ENTITIES.clear();
    }
}
