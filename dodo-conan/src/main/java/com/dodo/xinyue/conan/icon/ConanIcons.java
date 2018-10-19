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
     * 进度条
     */
    icon_progress('\ue631'),
    /**
     * 暂停
     */
    icon_pause('\ue961'),
    /**
     * 播放
     */
    icon_play('\ue642'),
    /**
     * 快进
     */
    icon_speed_go('\ue609'),
    /**
     * 快退
     */
    icon_speed_back('\ue73d'),
    /**
     * 返回
     */
    icon_back('\ue73c'),
    /**
     * 电视
     */
    icon_tv('\ue64b'),
    /**
     * 搜索
     */
    icon_search('\ue6a8'),
    /**
     * 历史记录
     */
    icon_history('\ue605'),
    /**
     * 赞助
     */
    icon_money('\ue6ea'),
    /**
     * 对勾
     */
    icon_selected('\ue693'),
    /**
     * 下拉箭头
     */
    icon_arrow('\ue695'),
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
