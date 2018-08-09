package com.igp.easydesign.bean.easydesign.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * 贴图设计
 */
public class StickImageDesign extends ImageEasyDesign{

    public StickImageDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix, Bitmap bitmap) {
        super(srcPs, dstPs, srcRect, dstRect, matrix, bitmap);
        this.imageEasyDesignType = ImageEasyDesignType.STICKER;
    }

}
