package com.igp.easydesign.bean.easydesign.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

public class RemoteBackgroundEasyDesign extends ImageEasyDesign{
    public RemoteBackgroundEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix, Bitmap bitmap, int originalImgWidth, int originalImgHeight) {
        super(srcPs, dstPs, srcRect, dstRect, matrix, bitmap);
        this.originalWidthDp     = originalImgWidth;
        this.originalHeightDp    = originalImgHeight;
        this.imageEasyDesignType = ImageEasyDesignType.REMOTE_BLACKGROUND;
    }
}