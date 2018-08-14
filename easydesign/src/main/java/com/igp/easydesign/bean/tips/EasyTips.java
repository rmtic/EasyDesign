package com.igp.easydesign.bean.tips;

import android.graphics.Paint;

/**
 * 提示语
 * 说明：主要用户调试用
 */
public class EasyTips {
    private Paint paint = new Paint();
    private String label;
    private int color;
    private int lineHeight;

    public EasyTips(String label, int color, int lineHeight) {
        this.label = label;
        this.color = color;
        this.lineHeight = lineHeight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }



}
