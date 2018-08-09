package com.igp.easydesign.bean.easydesign.image;

/**
 * Created by qiu on 2018/8/3.
 * 图片设计类型
 */
public enum ImageEasyDesignType{
    REMOTE_ALBUM     (0),
    STICKER          (1),
    LOCAL_ALBUM      (2);
    ImageEasyDesignType(int ni) {
        nativeInt = ni;
    }
    final int nativeInt;
}