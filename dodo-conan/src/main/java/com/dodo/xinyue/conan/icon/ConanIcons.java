package com.dodo.xinyue.conan.icon;

import com.joanzapata.iconify.Icon;

/**
 * ConanIcons
 *
 * @author DoDo
 * @date 2018/10/2
 */
public enum ConanIcons implements Icon {

    /**
     * 主页
     */
    icon_home('\ue682'),
    /**
     * 电影
     */
    icon_movie('\ue667'),
    /**
     * 音乐
     */
    icon_music('\ue681'),
    /**
     * 个人中心
     */
    icon_mine('\ue602');

    //前面是别名，随便起。
    //关键是'\ue80a'要与图标一一对应（映射）
    //不需要将ttf里的全部添加进去

    private char character;

    ConanIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
