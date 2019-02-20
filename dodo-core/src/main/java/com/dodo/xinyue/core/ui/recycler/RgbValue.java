package com.dodo.xinyue.core.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * RgbValue
 *
 * @author DoDo
 * @date 2017/9/11
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }

}
