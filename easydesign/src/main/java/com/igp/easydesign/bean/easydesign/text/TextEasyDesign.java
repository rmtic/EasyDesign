package com.igp.easydesign.bean.easydesign.text;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;

/**
 * Created by qiu on 2018/7/27.
 * ImageEasyDesign 图片设计
 * 概览
 * *************************************************************************************************
 * 属性：
 *
 * 方法：
 *      01.设置字体类型（）
 *      02.设置文字内容（）
 * *************************************************************************************************
 */
public class TextEasyDesign extends BaseEasyDesign {

    public String rbg;
    public int textColor;
    public String fontFamily;
    public String content = "";//显示内容
    private Layout.Alignment alignment;//对齐
    private String viewBox;//网页需要通过这个控制文本的大小（0 0 200 400）原图的尺寸，而Text的尺寸
    private float minTextSize = 24;
    private float textSize = minTextSize;

    //上下文
    //private int intrinsicWidth = 200;
    //最大最小字体尺寸，行距和填充；
  /*  private float maxTextSizePixels;
    private float minTextSizePixels;
    private float lineSpacingMultiplier = 1.0f;
    private float lineSpacingExtra = 0.0f;*/

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        paint.setTextSize(textSize);
        //textPaint.setTextSize(textSize);
    }

    public String getViewBox() {
        return viewBox;
    }

    public void setViewBox(String viewBox) {
        this.viewBox = viewBox;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        Paint paint     = new Paint();
        Rect  bound     =  new Rect();
        paint.getTextBounds(content,0,content.length(), bound);
        int boundWidth  = bound.width();
        int boundHeight = bound.height();
        RectF srcRect   = new RectF(0, 0, bound.width(), bound.height());
        RectF dstRect   = new RectF();
        float[] srcPs         = new float[]{
                0,0,
                boundWidth/2,0,
                boundWidth,0,
                boundWidth,boundHeight/2,
                boundWidth,boundHeight,
                boundWidth/2,boundHeight,
                0,boundHeight,
                0,boundHeight/2,
                boundWidth/2,boundHeight/2};
        float[]  dstPs       = srcPs.clone();
        this.srcPs = srcPs;
        this.dstPs = dstPs;
        this.srcRect = srcRect;
        this.dstRect = dstRect;
        update();
    }

    public int getKTextDesignWidth(){
        return getTextWidth(paint,content);
    }

    public TextEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix) {
        super(srcPs, dstPs, srcRect, dstRect, matrix);
        paint.setAntiAlias(true);
        update();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
      /*  canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        canvas.save();*/
        /* float degree = (float) Math.floor(EasyDesignHelper.computeDegree(new Point((int)dstPs[2], (int)dstPs[3]),new Point((int)dstPs[16], (int)dstPs[17])));//点与点的垂直夹角
        canvas.rotate(degree,dstPs[12],dstPs[13]);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
       // canvas.drawText(content,dstPs[12],dstPs[13] -( height / 2 - bounds.height() / 2),paint);
        canvas.restore();*/
       /* Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(70);
        paint.setStrokeWidth(5);
        canvas.drawLine(dstPs[0], dstPs[1], dstPs[4], dstPs[5], paint);
        canvas.drawLine(dstPs[4], dstPs[5], dstPs[8], dstPs[9], paint);
        canvas.drawLine(dstPs[8], dstPs[9], dstPs[12], dstPs[13], paint);
        canvas.drawLine(dstPs[0], dstPs[1], dstPs[12], dstPs[13], paint);*/
        //旋转文字，绘制文字
        long width  = Math.round(Math.sqrt(Math.pow(dstPs[0] - dstPs[4],2)+ Math.pow(dstPs[1] - dstPs[5],2)));
        long height = Math.round(Math.sqrt(Math.pow(dstPs[4] - dstPs[8],2)+ Math.pow(dstPs[5] - dstPs[9],2)));
        setTextSizeForWidth(paint,width,content);
        Path path = new Path();
        path.moveTo(dstPs[12],dstPs[13]);
        path.lineTo(dstPs[8],dstPs[9]);
        canvas.drawTextOnPath(content,path,0,0,paint);

    }

    /**
     * 获取设计宽度内字体的大小
     * Sets the text size for a Paint object so a given string of text will be a
     * given width.
     *
     * @param paint
     *            the Paint to set the text size for
     * @param desiredWidth
     *            the desired width
     * @param text
     *            the text that should be that width
     */
    private static void setTextSizeForWidth(Paint paint, float desiredWidth,String text) {
        final float testTextSize = 24f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        paint.setTextSize(desiredTextSize);
    }

    /**
     * 计算字体宽度
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public float getTextSize(){
        return paint.getTextSize();
    }

    public String getRbg() {
        return rbg;
    }

    public void setRbg(String rbg) {
        this.rbg = rbg;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * 1. 设置字体颜色
     * 2. 设置字体类型
     * #############################################################################################
     */

    /**
     * 设置文字颜色
     * @param color
     * @return
     */
    public TextEasyDesign setTextColor(@ColorInt int color) {
        paint.setColor(color);
        this.textColor = color;
        return this;
    }

    /**
     * 获取文字颜色
     * @return
     */
    public int getTextColor(){
        return textColor;
    }

    /**
     * 设置字体
     * @param typeface
     * @return
     */
    public TextEasyDesign setTypeface(@Nullable Typeface typeface) {
        paint.setTypeface(typeface);
        setContent(content);//重新设置宽高
        return this;
    }
}
