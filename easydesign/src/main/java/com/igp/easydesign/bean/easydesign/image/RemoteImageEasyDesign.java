package com.igp.easydesign.bean.easydesign.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * 远程图片设计
 */
public class RemoteImageEasyDesign extends ImageEasyDesign{
    public RemoteImageEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix, Bitmap bitmap, int originalImgWidth, int originalImgHeight) {
        super(srcPs, dstPs, srcRect, dstRect, matrix, bitmap);
        this.originalWidthDp     = originalImgWidth;
        this.originalHeightDp    = originalImgHeight;
        this.imageEasyDesignType = ImageEasyDesignType.REMOTE_ALBUM;
    }
}
