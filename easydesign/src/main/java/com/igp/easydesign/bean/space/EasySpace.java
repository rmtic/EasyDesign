package com.igp.easydesign.bean.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Created by qiu on 2018/7/27.
 * 设计区
 */

public class EasySpace extends BaseEasySpace{

    public int bgColor = Color.TRANSPARENT;

    public EasySpace(Rect rect, Matrix matrix, Paint paint,int bgColor) {
        super(rect, matrix, paint);
        this.bgColor = bgColor;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (rect != null) {
            paint.setColor(bgColor);
            canvas.drawRect(rect,paint);
        }
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
