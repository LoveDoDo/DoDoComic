package com.dodo.xinyue.conan.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * ConanIconFontModule
 *
 * @author DoDo
 * @date 2018/10/2
 */
public class ConanIconFontModule implements IconFontDescriptor {

    @Override
    public String ttfFileName() {
        return "conan_icon_font.ttf";//assets目录下的字体文件（.ttf）
    }

    @Override
    public Icon[] characters() {
        return ConanIcons.values();
    }

}
