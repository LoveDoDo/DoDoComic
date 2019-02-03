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
     * 影视精选
     */
    icon_yingshi('\ue688'),
    /**
     * 心灵鸡汤
     */
    icon_sun('\ue654'),
    /**
     * 免打扰
     */
    icon_quiet('\ue75e'),
    /**
     * 图片
     */
    icon_pic('\ue719'),
    /**
     * 刷新 WebView
     */
    icon_refresh_webview('\ue60f'),
    /**
     * 复制
     */
    icon_copy('\ue636'),
    /**
     * 右箭头
     */
    icon_arrow_right('\ue612'),
    /**
     * 消息
     */
    icon_message('\ue62a'),
    /**
     * 钥匙
     */
    icon_key('\ue683'),
    /**
     * 贴吧
     */
    icon_tieba('\ue606'),
    /**
     * 笑话
     */
    icon_joke('\ue6b0'),
    /**
     * 推理
     */
    icon_tuili('\ue614'),
    /**
     * 换肤
     */
    icon_skin('\ue603'),
    /**
     * 设置
     */
    icon_setting('\ue604'),
    /**
     * 漫画
     */
    icon_manhua('\ue600'),
    /**
     * 影音
     */
    icon_yingyin('\ue640'),
    /**
     * 播放2
     */
    icon_play2('\ue65b'),
    /**
     * 序号 No.
     */
    icon_no('\ue60c'),
    /**
     * 预览
     */
    icon_preview('\ue684'),
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
