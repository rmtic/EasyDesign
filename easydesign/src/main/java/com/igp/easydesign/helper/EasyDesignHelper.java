package com.igp.easydesign.helper;

import android.content.Context;
import android.content.res.Resources;
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
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesignType;
import com.igp.easydesign.bean.easydesign.text.TextEasyDesign;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;
import com.igp.easydesign.view.BaseEasyDesignView;

import org.michaelevans.colorart.library.ColorArt;

/**
 * Created by qiu on 2018/7/27.
 * 说明：会员设计的帮助类
 */

public class EasyDesignHelper {

    /**
     * 获取本地图片的Bitmap
     * @param res
     * @return
     */
    public static Bitmap getLocalBitmap(Context context,int res){
        return BitmapFactory.decodeResource(context.getResources(), res);
    }


    /**
     * 获取图片的原始宽
     * @param res
     * @param drawableId
     * @return
     */
    public static int getImageOrgWidth(Resources res,int drawableId){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,drawableId , opts);
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;
        int width = opts.outWidth;
        return width;
    }

    /**
     * 获取图片的原始高
     * @param res
     * @param drawableId
     * @return
     */
    public static int getImageOrgHeight(Resources res,int drawableId){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,drawableId , opts);
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;
        int height = opts.outHeight;
        return height;
    }

    /**
     * 创建图标
     * @param bitmap
     * @return
     */
    public static Bitmap createEasyIconBitmap(Bitmap bitmap){
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
     * createEasySpace(150,50,650,700,Color.WHITE);
     * @return
     */
    public static EasySpace createEasySpace(int left,int top,int right,int bottom,int color){
        Matrix matrix = new Matrix();
        Paint  paint  = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect(left, top, right, bottom);
        return new EasySpace(rect,matrix,paint,color);
    }

    /**
     * 创建一个图片设计
     * @return
     */
    public static ImageEasyDesign createImageDesign(Bitmap bitmap, ImageEasyDesignType imageEasyDesignType){
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
        return new ImageEasyDesign(srcPs,dstPs,srcRect,dstRect,matrix,bitmap,imageEasyDesignType);
    }
    //创建一个文本设计
    public static TextEasyDesign createTextEasyDesign(String label){

        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(label,0,label.length(), rect);

        int     mainBmpWidth  = rect.width();
        int     mainBmpHeight =rect.height();
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
        return new TextEasyDesign(srcPs,dstPs,srcRect,dstRect,matrix,label);
    }



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

    /**
     *
     * @param easyDesign                    图片设计
     * @param imageOriginalWidthDp          图片的原始宽度
     * @param imageOriginalHeightDp         图片的原始高度
     * @param areaDesignWidthPx             设计去的宽度
     * @param areaDesignHeightPx            设计区的高度
     * @param paramsWidthCM                 产品打印区的CM
     * @return
     */
    public static boolean isBlurImage(ImageEasyDesign easyDesign,int imageOriginalWidthDp,int imageOriginalHeightDp,int areaDesignWidthPx,int areaDesignHeightPx,int paramsWidthCM){
        int imageDynamicWidthDp = ((ImageEasyDesign) easyDesign).getDynamicWidthDp();
        int imageDynamicHeightDp = ((ImageEasyDesign) easyDesign).getDynamicHeightDp();

        int                imageDynamicWidthPx   = ConvertUtils.dp2px(imageDynamicWidthDp);     //动态宽度
        int                imageDynamicHeightPx  = ConvertUtils.dp2px(imageDynamicHeightDp);    //动态高度

        int                imageOriginalWidthPx  = ConvertUtils.dp2px(imageOriginalWidthDp);     //原始宽度
        int                imageOriginalHeightPx = ConvertUtils.dp2px(imageOriginalHeightDp);    //原始高度

        //int                areaDesignWidthPx   = Integer.parseInt(mProduct.getDesign().getFront().get(0).get(1).getWidth().replace("px",""));  //设计区宽度
        //int                areaDesignHeightPx  = Integer.parseInt(mProduct.getDesign().getFront().get(0).get(1).getHeight().replace("px","")); //设计区高度

        //Params params              = new Gson().fromJson(mProduct.getDesign().getParams().get("front-0"),Params.class);
        //int                paramsWidthCM       = (int) Float.parseFloat(params.width);                                                                                         //paramsWidth
        //int                paramsHeightCM      = (int) Float.parseFloat(params.height);                                                                                        //paramsHeight
        float              sizecn              = (float)(paramsWidthCM / 0.02 / areaDesignWidthPx);                                                                            //SizeCn
        int                imageMaxWidthPx     = (int) (imageOriginalWidthPx * sizecn);                                                                                        //图片最多宽度
        int                imageMaxHeightPx    = (int) (imageOriginalHeightPx * sizecn);                                                                                       //图片最大高度

        if(imageDynamicHeightPx > imageMaxHeightPx || imageDynamicWidthPx > imageMaxWidthPx){
            return true;

        }
        return false;
    }

    /**
     * 获取添加设计平台的中心X点
     * @param easyDesignView
     * @param easyDesign
     * @return
     */
    public static float getAddDesignCenterX(BaseEasyDesignView easyDesignView, BaseEasyDesign easyDesign){
        return (float)(easyDesignView.getWidth() / 2  - easyDesign.getDstRect().centerX()+ easyDesignView.getLeft());
    }

    /**
     * 获取添加设计平台的中心Y点
     * @param easyDesignView
     * @param easyDesign
     * @return
     */
    public static float getAddDesignCenterY(BaseEasyDesignView easyDesignView, BaseEasyDesign easyDesign){
        return (float)(easyDesignView.getHeight() / 2  - easyDesign.getDstRect().centerY() + easyDesignView.getTop());
    }

}
