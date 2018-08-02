package com.igp.easydesign.bean.easydesign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    /**
     * 获取图片的动态宽
     * @return
     */
    public int getDynamicWidthDp() {
        int width = 0;
        if (dstPs != null && dstPs.length > 0) {
            width  = (int) Math.round(Math.sqrt(Math.pow(dstPs[0] - dstPs[4],2)+ Math.pow(dstPs[1] - dstPs[5],2)));
        }
        return (int) width;
    }

    /**
     * 获取图片的动态高
     * @return
     */
    public int getDynamicHeightDp() {
        int height = 0;
        if (dstPs != null && dstPs.length > 0) {
        height = (int) Math.round(Math.sqrt(Math.pow(dstPs[4] - dstPs[8],2)+ Math.pow(dstPs[5] - dstPs[9],2)));
        }
        return  height;
    }


}
