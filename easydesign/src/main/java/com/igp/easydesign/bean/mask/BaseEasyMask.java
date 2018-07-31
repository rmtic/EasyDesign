package com.igp.easydesign.bean.mask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

/**
 * Created by Sam on 2018/7/31.
 */

public abstract class BaseEasyMask {
    public Bitmap maskBitmap;
    /**
     * 绘制设计
     * @param canvas
     */
    public abstract void draw(@NonNull Canvas canvas);

    public Bitmap getMaskBitmap() {
        return maskBitmap;
    }

    public void setMaskBitmap(Bitmap maskBitmap) {
        this.maskBitmap = maskBitmap;
    }
}
