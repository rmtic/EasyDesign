package com.igp.easydesign.bean.easydesign.text;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import com.igp.easydesign.bean.easydesign.BaseEasyDesign;

/**
 * Created by qiu on 2018/7/27.
 */

public class TextEasyDesign extends BaseEasyDesign {

    public Bitmap bitmap;
    public String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TextEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix,String label) {
        super(srcPs, dstPs, srcRect, dstRect, matrix);
        this.label = label;

        Rect rect = new Rect();
        paint.getTextBounds("TEXT HERE",0,"TEXT HERE".length(), rect);
        int width = rect.width();
        bitmap = createBitmap(rect.width(),rect.height(),"TEXT HERE");
        update();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));

        int width  = (int) Math.round(Math.sqrt(Math.pow(dstPs[0] - dstPs[4],2)+ Math.pow(dstPs[1] - dstPs[5],2)));
        int height = (int) Math.round(Math.sqrt(Math.pow(dstPs[4] - dstPs[8],2)+ Math.pow(dstPs[5] - dstPs[9],2)));

        bitmap = createBitmap(width,height,"TEXT HERE");
        canvas.drawBitmap(bitmap,matrix,null);
    }

  public Bitmap createBitmap(int width,int height,String label){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextSize(34);
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawText(label,width/2,height/2,paint);
        return resultBitmap;
    }


}
