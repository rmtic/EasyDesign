package com.igp.easydesign.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.igp.easydesign.bean.easydesign.ImageEasyDesign;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;

import org.michaelevans.colorart.library.ColorArt;

/**
 * Created by qiu on 2018/7/27.
 * 说明：会员设计的帮助类
 */

public class EasyDesignHelper {

    private Context context;

    public EasyDesignHelper(Context context) {
        this.context = context;
    }

    /**
     * 获取本地图片的Bitmap
     * @param res
     * @return
     */
    public Bitmap getLocalBitmap(int res){
        return BitmapFactory.decodeResource(this.context.getResources(), res);
    }

    /**
     * 创建图标
     * @param bitmap
     * @return
     */
    public Bitmap createEasyIconBitmap(Bitmap bitmap){
        Bitmap resultBitmap = Bitmap.createBitmap(80,80,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        RectF rectF = new RectF(0,0,80,80);
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,rect,rectF,null);
        return resultBitmap;
    }

    public static EasyMask createEasyMask(Bitmap bitmap){
        Bitmap resultBitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight() - (BarUtils.getStatusBarHeight() + BarUtils.getActionBarHeight()),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        RectF rectF = new RectF(0,0,ScreenUtils.getScreenWidth(),ScreenUtils.getScreenWidth());
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,rect,rectF,null);

        //获取图片的主题色，并且绘制遮罩图片无法覆盖的部分
        ColorArt colorArt = new ColorArt(resultBitmap);
        RectF rectF2 = new RectF(0,ScreenUtils.getScreenHeight() - ScreenUtils.getScreenWidth() + BarUtils.getNavBarHeight()+ BarUtils.getStatusBarHeight(),ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight());
        Paint paint = new Paint();
        paint.setColor(colorArt.getBackgroundColor());
        canvas.drawRect(rectF2,paint);

        EasyMask easyMask = new EasyMask();
        easyMask.setMaskBitmap(resultBitmap);
        return easyMask;
    }

    /**
     * 创建一个设计区
     * @return
     */
    public static EasySpace createEasySpace(){
        Matrix matrix = new Matrix();
        Paint  paint  = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect(150,50,650,700);
        EasySpace easySpace = new EasySpace(rect,matrix,paint);
        return easySpace;
    }

    /**
     * 创建一个图片设计
     * @return
     */
    public ImageEasyDesign createImageDesign(Bitmap bitmap){
        //Bitmap mainBmp       = BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.test);
        int     mainBmpWidth  = bitmap.getWidth();
        int     mainBmpHeight = bitmap.getHeight();
        RectF srcRect         = new RectF(0, 0, mainBmpWidth, mainBmpHeight);
        RectF   dstRect       = new RectF();
        float[] srcPs         = new float[]{
                0,0,
                mainBmpWidth/2,0,
                mainBmpWidth,0,
                mainBmpWidth,mainBmpHeight/2,
                mainBmpWidth,mainBmpHeight,
                mainBmpWidth/2,mainBmpHeight,
                0,mainBmpHeight,
                0,mainBmpHeight/2,
                mainBmpWidth/2,mainBmpHeight/2};
        float[]  dstPs       = srcPs.clone();
        Matrix matrix        = new Matrix();
        return new ImageEasyDesign(srcPs,dstPs,srcRect,dstRect,matrix,bitmap);
    }
    //创建一个文本设计
    //创建一个SVG设计

    /**
     * 计算两点与垂直方向夹角
     *  用法(02,03) 与 (16,17) 的垂直夹角
     * dstP[18]
     * (00,01)(02,03)(04,05)
     * (14,15)(16,17)(06,07)
     * (12,13)(10,11)(08,09)
     *
     * @param p1
     * @param p2
     * @return
     */
    public static float computeDegree(Point p1, Point p2){
        float tran_x = p1.x - p2.x;
        float tran_y = p1.y - p2.y;
        float degree = 0.0f;
        float angle = (float)(Math.asin(tran_x/Math.sqrt(tran_x*tran_x + tran_y* tran_y))*180/Math.PI);
        if(!Float.isNaN(angle)){
            if(tran_x >= 0 && tran_y <= 0){//第一象限
                degree = angle;
            }else if(tran_x <= 0 && tran_y <= 0){//第二象限
                degree = angle;
            }else if(tran_x <= 0 && tran_y >= 0){//第三象限
                degree = -180 - angle;
            }else if(tran_x >= 0 && tran_y >= 0){//第四象限
                degree = 180 - angle;
            }
        }
        return degree;
    }

}
