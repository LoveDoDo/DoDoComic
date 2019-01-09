package com.dodo.xinyue.conan.constant;

/**
 * ApiURL
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class ApiURL {

    /**
     * 爱奇艺
     */
    private static final String IQIYI_BASE_URL = "http://cache.video.iqiyi.com/jp/avlist/";

    private static final String IQIYI_CHINESE_TV_ID = "106741901";//国语-TV版
    private static final String IQIYI_CHINESE_MOVIE_ID = "205054601";//国语-剧场版
    private static final String IQIYI_JAPANESE_TV_ID = "202134201";//日语-TV版
    private static final String IQIYI_JAPANESE_MOVIE_ID = "205055601";//日语-剧场版

    public static final String IQIYI_CHINESE_TV_URL = IQIYI_BASE_URL + IQIYI_CHINESE_TV_ID + "/{page}/50/?albumId=" + IQIYI_CHINESE_TV_ID + "&pageNum=50&pageNo={page}";
    public static final String IQIYI_CHINESE_MOVIE_URL = IQIYI_BASE_URL + IQIYI_CHINESE_MOVIE_ID + "/{page}/50/?albumId=" + IQIYI_CHINESE_MOVIE_ID + "&pageNum=50&pageNo={page}";
    public static final String IQIYI_JAPANESE_TV_URL = IQIYI_BASE_URL + IQIYI_JAPANESE_TV_ID + "/{page}/50/?albumId=" + IQIYI_JAPANESE_TV_ID + "&pageNum=50&pageNo={page}";
    public static final String IQIYI_JAPANESE_MOVIE_URL = IQIYI_BASE_URL + IQIYI_JAPANESE_MOVIE_ID + "/{page}/50/?albumId=" + IQIYI_JAPANESE_MOVIE_ID + "&pageNum=50&pageNo={page}";

}
