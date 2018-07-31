package com.igp.easydesign.bean.icon;

/**
 * Created by Sam on 2018/7/30.
 */

public enum EasyIconType {
    /**
     * dstPs[0] , dst[1]
     */
    LEFT_TOP     (0),
    /**
     * dstPs[2] , dst[3]
     */
    MIDDLE_TOP   (1),
    /**
     * dstPs[4] , dst[5]
     */
    RIGHT_TOP    (2),
    /**
     * dstPs[6] , dst[7]
     */
    RIGHT_MIDDLE (3),
    /**
     * dstPs[8] , dst[9]
     */
    RIGHT_BOTTOM (4),
    /**
     * dstPs[10] , dst[11]
     */
    MIDDLE_BOTTOM(5),
    /**
     * dstPs[12] , dst[13]
     */
    LEFT_BOTTOM  (6),
    /**
     * dstPs[14] , dst[15]
     */
    LEFT_MIDDLE  (7),
    /**
     * dstPs[16] , dst[17]
     */
    CENTER       (8);

    EasyIconType(int ni) {
        nativeInt = ni;
    }
    final int nativeInt;
}
