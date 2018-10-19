package com.dodo.xinyue.conan.main.index.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    private int mForm = 0;
    private int mLanguage = 0;
    private int mType = 0;

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

            final String language;
            switch (mLanguage) {
                case 0:
                    language = "国语";
                    break;
                case 1:
                    language = "日语";
                    break;
                default:
                    language = "未知";
                    break;
            }

            final String type;
            switch (mType) {
                case 0:
                    type = "TV版";
                    break;
                case 1:
                    type = "剧场版";
                    break;
                case 2:
                    type = "OVA";
                    break;
                case 3:
                    type = "特别篇";
                    break;
                case 4:
                    type = "剧情篇";
                    break;
                case 5:
                    type = "主线篇";
                    break;
                default:
                    type = "未知";
                    break;
            }

            final IndexBean comicBean = new IndexBean();
            comicBean.setNumber(number);
            comicBean.setTitle(title);
            comicBean.setCover(cover);
            comicBean.setTvQipuId(tvQipuId);
            comicBean.setVid(vid);
            comicBean.setLanguage(language);
            comicBean.setType(type);

            final int itemType;
            switch (mForm) {
                case 0:
                    itemType = IndexItemType.COMIC_TEXT;
                    break;
                case 1:
                    itemType = IndexItemType.COMIC_NUMBER;
                    break;
                case 2:
                    itemType = IndexItemType.COMIC_IMAGE_TEXT;
                    break;
                case 3:
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

    public final IndexDataConverter setForm(int form) {
        this.mForm = form;
        return this;
    }

    public final IndexDataConverter setLanguage(int language) {
        this.mLanguage = language;
        return this;
    }

    public final IndexDataConverter setType(int type) {
        this.mType = type;
        return this;
    }

    private String handleCover(String url) {
        final StringBuilder sb = new StringBuilder(url);
        final String cover = sb.substring(0, url.length() - 4) + "_180_101.jpg";// _160_90
        return cover;
    }

}
