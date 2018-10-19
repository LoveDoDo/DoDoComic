package com.dodo.xinyue.conan.constant;

/**
 * ApiURL
 *
 * @author DoDo
 * @date 2018/10/15
 */
public class ApiURL {

    private static final String BASE_URL = "http://cache.video.iqiyi.com/jp/avlist/";

    private static final String GUO_YU_TV_ID = "106741901";
    private static final String GUO_YU_JU_CHANG_ID = "205054601";
    private static final String GUO_YU_ZHU_XIAN_ID = "202931401";

    private static final String RI_YU_TV_ID = "202134201";
    private static final String RI_YU_JU_CHANG_ID = "205055601";

    public static final String GUO_YU_TV_URL = BASE_URL + GUO_YU_TV_ID + "/{page}/50/?albumId=" + GUO_YU_TV_ID + "&pageNum=50&pageNo={page}";
    public static final String GUO_YU_JU_CHANG_URL = BASE_URL + GUO_YU_JU_CHANG_ID + "/{page}/50/?albumId=" + GUO_YU_JU_CHANG_ID + "&pageNum=50&pageNo={page}";
    public static final String GUO_YU_ZHU_XIAN_URL = BASE_URL + GUO_YU_ZHU_XIAN_ID + "/{page}/50/?albumId=" + GUO_YU_ZHU_XIAN_ID + "&pageNum=50&pageNo={page}";

    public static final String RI_YU_TV_URL = BASE_URL + RI_YU_TV_ID + "/{page}/50/?albumId=" + RI_YU_TV_ID + "&pageNum=50&pageNo={page}";
    public static final String RI_YU_JU_CHANG_URL = BASE_URL + RI_YU_JU_CHANG_ID + "/{page}/50/?albumId=" + RI_YU_JU_CHANG_ID + "&pageNum=50&pageNo={page}";

//    public static final int GUO_YU_TV_COUNT = 837;
//    public static final int RI_YU_TV_COUNT = 934;

}
