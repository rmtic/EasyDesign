package com.igp.easydesign.bean.easydesign.text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.helper.EasyDesignHelper;

/**
 * Created by qiu on 2018/7/27.
 */
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;


/**
 * 开 发 者： qiu
 * 创建时间： 2017/7/5
 * 功能描述：
 */

public class TextEasyDesign extends BaseEasyDesign {


    //上下文
    private int intrinsicWidth = 200;

    public String rbg;
    public String fontFamily;

    private TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    private StaticLayout staticLayout; //可以换行的换行的布局

    private Layout.Alignment alignment;//对齐
    public String content = "";//显示内容

    //最大最小字体尺寸，行距和填充；
    private float maxTextSizePixels;
    private float minTextSizePixels;
    private float lineSpacingMultiplier = 1.0f;
    private float lineSpacingExtra = 0.0f;


    //网页需要通过这个控制文本的大小（0 0 200 400）原图的尺寸，而Text的尺寸
    private String viewBox;

    private float minTextSize = 24;
    private float textSize = minTextSize;

    int textColor;

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
    }

    public String getViewBox() {
        return viewBox;
    }

    public void setViewBox(String viewBox) {
        this.viewBox = viewBox;
    }

    public float getTextSize(){
        return staticLayout.getPaint().getTextSize();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
      /*  //获取宽度
        int text_width = getTextWidth(textPaint,content);
        if( text_width < intrinsicWidth){
            staticLayout = new StaticLayout(content, textPaint,text_width,alignment, 1.0f, 0.0f, false);
            srcRect = new RectF(0, 0,text_width,staticLayout.getHeight());//矩形
        }else{
            staticLayout = new StaticLayout(content, textPaint,intrinsicWidth,alignment, 1.0f, 0.0f, false);
            srcRect = new RectF(0, 0,intrinsicWidth ,staticLayout.getHeight());//矩形
        }*/

    }

    public int getKTextDesignWidth(){
        return getTextWidth(textPaint,content);
    }


    public TextEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix,StaticLayout staticLayout) {
        super(srcPs, dstPs, srcRect, dstRect, matrix);
        this.staticLayout = staticLayout;
        //设置上下文
        //获取图片
       /* if (drawable == null) {
            this.drawable = ContextCompat.getDrawable(context, R.drawable.sticker_transparent_background);
        }*/
        //创建文字
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        alignment = Layout.Alignment.ALIGN_CENTER;//居中对齐
        textPaint.setTextSize(textSize);//设置字体大小
        update();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //绘制文本
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        canvas.save();
        canvas.concat(matrix);//合并多个,矩阵
        if(null != staticLayout){
            staticLayout.draw(canvas);
        }
        canvas.restore();
    }

    /**
     * 设置视图的克隆的文本大小
     * Sets the text size of a clone of the view's {@link TextPaint} object
     * and uses a {@link StaticLayout} instance to measure the height of the text.
     *
     * @return the height of the text when placed in a view
     * with the specified width
     * and when the text has the specified size.
     */
    protected int getTextHeightPixels(@NonNull CharSequence source, int availableWidthPixels, float textSizePixels) {
        textPaint.setTextSize(textSizePixels);
        StaticLayout staticLayout = new StaticLayout(
                source,
                textPaint,
                availableWidthPixels,
                Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiplier,
                lineSpacingExtra,
                true);
        return staticLayout.getHeight();
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
     * 修改
     * #############################################################################################
     */

    /**
     * 设置文字颜色
     * @param color
     * @return
     */
    public TextEasyDesign setTextColor(@ColorInt int color) {
        textPaint.setColor(color);
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
        textPaint.setTypeface(typeface);
        int text_width = getTextWidth(textPaint,content);
        //rectF = new RectF(0, 0,drawable.getIntrinsicWidth(),staticLayout.getHeight());//矩形
        if( text_width < intrinsicWidth){
            staticLayout = new StaticLayout(content, textPaint,text_width,alignment, 1.0f, 0.0f, false);
            srcRect = new RectF(0, 0,text_width,staticLayout.getHeight());//矩形
        }else{
            staticLayout = new StaticLayout(content, textPaint,intrinsicWidth,alignment, 1.0f, 0.0f, false);
            srcRect = new RectF(0, 0,intrinsicWidth,staticLayout.getHeight());//矩形
        }
        return this;
    }
}
