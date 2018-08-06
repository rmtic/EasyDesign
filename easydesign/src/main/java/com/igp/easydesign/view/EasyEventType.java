package com.igp.easydesign.view;

/**
 * 设计的操作事件
 */
public enum  EasyEventType {
    RESIZE       (0),
    SELECTED     (1),
    ADD          (2),
    REMOVE       (3),
    LOCK         (4);
    EasyEventType(int ni) {
        nativeInt = ni;
    }
    final int nativeInt;
}
