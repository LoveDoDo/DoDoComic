package com.dodo.xinyue.conan.helper;

import android.content.Intent;
import android.net.Uri;

import com.dodo.xinyue.core.app.DoDo;

/**
 * AppLinkHelper
 *
 * @author DoDo
 * @date 2018/10/23
 */
public class AppLinkHelper {

    public static void openIQiYi(String tvid, String vid) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("iqiyi://mobile/player?tvid=" + tvid + "&vid=" + vid));
        DoDo.getActivity().startActivity(intent);
    }

    public static void openYouKu(String vid) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("youku://play?vid=" + vid));//XMzgwNjkxODQ4NA==
        DoDo.getActivity().startActivity(intent);
    }

}
