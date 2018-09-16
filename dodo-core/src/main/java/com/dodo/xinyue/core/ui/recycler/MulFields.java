package com.dodo.xinyue.core.ui.recycler;

/**
 * MulFields
 *
 * @author DoDo
 * @date 2017/9/10
 */
@SuppressWarnings("unused")
public enum MulFields {
    //类型（必须）
    ITEM_TYPE,
    //每个item所占空间（必须）
    SPAN_SIZE,

    //通用类型，方便赋值
    BEAN,

    //简单类型
    TEXT,
    IMAGE,
    //特定类型
    BANNER,
    TITLE,
    ID,
    NAME,

    //备用类型
    TAG,

    //给嵌套rv提供数据源
    DATA,
    //嵌套rv 多个list 当前索引
    CURRENT_INDEX
//    //banner图片
//    BANNER_IMAGE,
//    //banner标题
//    BANNER_TITLE
}
