package com.igp.easydesign.bean.mask;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Created by Sam on 2018/7/31.
 */

public  class EasyMask extends BaseEasyMask {

    public Rect   rect;
    public Matrix matrix;
    public Paint  paint = new Paint();

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(maskBitmap,0,0,paint);
    }




}
