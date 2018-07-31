package com.igp.easydesign.bean.easydesign;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;


/**
 * Created by qiu on 2018/7/27.
 */

public abstract class BaseEasyDesign  {

    public Paint paint = new Paint();
    public boolean isLocked   = false;
    public boolean isSelected = false;

    public float[] srcPs  , dstPs;
    public RectF   srcRect,dstRect;
    public Matrix  matrix;

    public BaseEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix) {
        this.srcPs   = srcPs;
        this.dstPs   = dstPs;
        this.srcRect = srcRect;
        this.dstRect = dstRect;
        this.matrix  = matrix;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float[] getSrcPs() {
        return srcPs;
    }

    public void setSrcPs(float[] srcPs) {
        this.srcPs = srcPs;
    }

    public float[] getDstPs() {
        return dstPs;
    }

    public void setDstPs(float[] dstPs) {
        this.dstPs = dstPs;
    }

    public RectF getSrcRect() {
        return srcRect;
    }

    public void setSrcRect(RectF srcRect) {
        this.srcRect = srcRect;
    }

    public RectF getDstRect() {
        return dstRect;
    }

    public void setDstRect(RectF dstRect) {
        this.dstRect = dstRect;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * 绘制设计
     * @param canvas
     */
    public abstract void draw(@NonNull Canvas canvas);


    /**
     * 递增移动设计
     * @param dx
     * @param dy
     */
    public void postTranslate(float dx, float dy){
        if (matrix == null || dstPs == null || dstPs.length < 1) {
            return;
        }
        matrix.postTranslate(dx,dy);
        update();
    }


    /**
     * 递增旋转设计
     * dstPs[16] is matrix centerX
     * dstPs[17] is matrix centerY
     * @param degrees
     */
    public void postRotate(float degrees){
        if (matrix == null || dstPs == null || dstPs.length < 1) {
            return;
        }
        matrix.postRotate(degrees,dstPs[16],dstPs[17]);
        update();
    }

    /**
     * 会员自定义旋转点，递增旋转设计
     * @param degrees
     * @param px
     * @param py
     */
    public void postRotate(float degrees,float px ,float py){
        if (matrix == null || dstPs == null || dstPs.length < 1) {
            return;
        }
        matrix.postRotate(degrees,px,py);
        update();
    }

    /**
     * 围绕中心旋转
     * dstPs[16] is matrix centerX
     * dstPs[17] is matrix centerY
     * @param sx is scale X
     * @param sy is scale Y
     */
    public void postScale(float sx ,float sy){
        if (matrix == null || dstPs == null || dstPs.length < 1) {
            return;
        }
        matrix.postScale(sx, sy, dstPs[16], dstPs[17]);
        update();
    }

    /**
     * 刷新数据
     */
    public void update(){
        if (matrix == null || srcPs == null || dstPs == null || srcPs.length < 1 || srcPs.length < 1 || srcRect == null || dstRect == null) {
            return;
        }
        matrix.mapPoints(dstPs, srcPs);
        matrix.mapRect(dstRect, srcRect);
    }




}
