package com.igp.easydesign.bean.space;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Created by Sam on 2018/7/31.
 */

public abstract class BaseEasySpace{
    public Rect rect;
    public Matrix matrix;
    public Paint paint = new Paint();

    public BaseEasySpace(Rect rect, Matrix matrix, Paint paint) {
        this.rect = rect;
        this.matrix = matrix;
        this.paint = paint;
    }

    /**
     * 绘制设计
     * @param canvas
     */
    public abstract void draw(@NonNull Canvas canvas);

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
