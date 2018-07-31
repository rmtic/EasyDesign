package com.igp.easydesign.bean.easydesign;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

/**
 * Created by qiu on 2018/7/27.
 */

public class ImageEasyDesign extends BaseEasyDesign{

    public Bitmap bitmap;

    public ImageEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix, Bitmap bitmap) {
        super(srcPs, dstPs, srcRect, dstRect, matrix);
        this.bitmap = bitmap;
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }


}
