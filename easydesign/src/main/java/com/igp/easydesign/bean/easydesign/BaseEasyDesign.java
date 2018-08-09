package com.igp.easydesign.bean.easydesign;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.igp.easydesign.helper.EasyDesignHelper;


/**
 * Created by qiu on 2018/7/27.
 * BaseEasyDesign 设计的基类
 * 概览
 * *************************************************************************************************
 * 属性：
 *      1.是否锁定
 *      2.是否选中
 *      3.画笔
 *      4.源点数组，运算结果数组
 *      5.源矩型  ，运算结果矩型
 *      6.矩阵
 * 操作：
 *      1.移动
 *      2.旋转
 *      3.旋转（某点）
 *      4.放大
 *      5.更新
 * 获取：
 *      1.宽
 *      2.高
 *      3.旋转角度
 * *************************************************************************************************
 */

public abstract class BaseEasyDesign{

    public boolean isLocked   = false;
    public boolean isSelected = false;

    public Paint paint = new Paint();
    public float[] srcPs  , dstPs;
    public RectF   srcRect,dstRect;
    public Matrix  matrix;
    public BaseEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix) {
        this.srcPs   = srcPs;
        this.dstPs   = dstPs;
        this.srcRect = srcRect;
        this.dstRect = dstRect;
        this.matrix  = matrix;
        update();//初次创建需要刷新
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

    /***
     * 1.移动
     * 2.旋转
     * 3.旋转（某点）
     * 4.放大
     * 5.更新
     * *********************************************************************************************
     */

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

    /***
     * 1.宽
     * 2.高
     * 3.旋转角度
     * *********************************************************************************************
     */

    /**
     * EasyDesign Dst动态宽度
     * @return
     */
    public int getDstPsWidthDp() {
        int width = 0;
        if (dstPs != null && dstPs.length > 0) {
            width  = (int) Math.round(Math.sqrt(Math.pow(dstPs[0] - dstPs[4],2)+ Math.pow(dstPs[1] - dstPs[5],2)));
        }
        return (int) width;
    }

    /**
     * EasyDesign Dst动态高度
     * @return
     */
    public int getDstPsHeightDp() {
        int height = 0;
        if (dstPs != null && dstPs.length > 0) {
            height = (int) Math.round(Math.sqrt(Math.pow(dstPs[4] - dstPs[8],2)+ Math.pow(dstPs[5] - dstPs[9],2)));
        }
        return  height;
    }

    /**
     * EasyDesign 的旋转角度
     * @return
     */
    public float getDegree(){
        float degree = 0;
        if (dstPs != null && dstPs.length > 0) {
            degree = (float) Math.floor(EasyDesignHelper.computeDegree(new Point((int)dstPs[2], (int)dstPs[3]),new Point((int)dstPs[16], (int)dstPs[17])));
        }
        return degree;
    }

}
