package com.dodo.xinyue.conan.main.index.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.main.index.bean.IndexBean;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;

import java.util.ArrayList;

/**
 * IndexDataConverter
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class IndexDataConverter extends DataConverter {

    private JSONObject mData = null;
    private int mLanguageIndex = 0;
    private int mTypeIndex = 0;
    private int mFormIndex = 0;

    @Override
    public ArrayList<MulEntity> convert() {

        mData = JSON.parseObject(getJsonData()).getJSONObject("data");
        if (mData == null) {
            return ENTITIES;
        }

        //vlist
        final JSONArray vlist = mData.getJSONArray("vlist");
        if (vlist == null) {
            return ENTITIES;
        }

        final int vlistSize = vlist.size();
        if (vlistSize <= 0) {
            return ENTITIES;
        }

        for (int i = 0; i < vlistSize; i++) {
            final JSONObject vlistBean = vlist.getJSONObject(i);

            final long id = vlistBean.getLong("id");//唯一
            final String number = vlistBean.getString("pds");
            final String title = vlistBean.getString("vt");
            final String cover = handleCover(vlistBean.getString("vpic"));
            final String tvQipuId = vlistBean.getString("tvQipuId");
            final String vid = vlistBean.getString("vid");

            final String language=ApiHelper.getLanguageStr(mLanguageIndex);
            final String type= ApiHelper.getTypeStr(mTypeIndex);
//            final String form = ApiHelper.getFormStr(mFormIndex);

            final IndexBean comicBean = new IndexBean();
            comicBean.setNumber(number);
            comicBean.setTitle(title);
            comicBean.setCover(cover);
            comicBean.setTvQipuId(tvQipuId);
            comicBean.setVid(vid);
            comicBean.setLanguage(language);
            comicBean.setType(type);

            final int itemType;
            switch (mFormIndex) {
                case ApiConstants.FORM_TEXT:
                    itemType = IndexItemType.COMIC_TEXT;
                    break;
                case ApiConstants.FORM_NUMBER:
                    itemType = IndexItemType.COMIC_NUMBER;
                    break;
                case ApiConstants.FORM_IMAGE_TEXT:
                    itemType = IndexItemType.COMIC_IMAGE_TEXT;
                    break;
                case ApiConstants.FORM_GRID:
                    itemType = IndexItemType.COMIC_GRID;
                    break;
                default:
                    itemType = IndexItemType.COMIC_TEXT;
                    break;
            }

            final MulEntity comicEntity = MulEntity.builder()
                    .setItemType(itemType)
                    .setBean(comicBean)
                    .build();
            ENTITIES.add(comicEntity);

            //插入数据库


        }

        return ENTITIES;
    }

    public final IndexDataConverter setFormIndex(int formIndex) {
        this.mFormIndex = formIndex;
        return this;
    }

    public final IndexDataConverter setLanguageIndex(int languageIndex) {
        this.mLanguageIndex = languageIndex;
        return this;
    }

    public final IndexDataConverter setTypeIndex(int typeIndex) {
        this.mTypeIndex = typeIndex;
        return this;
    }

    private String handleCover(String url) {
        final StringBuilder sb = new StringBuilder(url);
        final String cover = sb.substring(0, url.length() - 4) + "_180_101.jpg";// _160_90
        return cover;
    }

}
