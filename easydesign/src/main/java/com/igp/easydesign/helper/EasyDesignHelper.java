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
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesignType;
import com.igp.easydesign.bean.easydesign.image.LocalImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.RemoteImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.StickImageDesign;
import com.igp.easydesign.bean.easydesign.text.TextEasyDesign;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;
import com.igp.easydesign.view.BaseEasyDesignView;

import org.michaelevans.colorart.library.ColorArt;

import static com.igp.easydesign.bean.easydesign.text.TextEasyDesign.getTextWidth;

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
        Bitmap resultBitmap = Bitmap.createBitmap(50,50,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        RectF rectF = new RectF(0,0,50,50);
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,rect,rectF,null);
        return resultBitmap;
    }

    public static EasyMask createEasyMask(int easyDesignWidth ,int easyDesignHeight , Bitmap bitmap){
        EasyMask easyMask = null;
        if(easyDesignHeight != 0 || easyDesignWidth != 0 ){
            Bitmap resultBitmap = Bitmap.createBitmap(easyDesignWidth,easyDesignHeight,Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(resultBitmap);
            RectF rectF = new RectF(0,0,easyDesignWidth,easyDesignWidth);
            Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
            canvas.drawBitmap(bitmap,rect,rectF,null);

            //获取图片的主题色，并且绘制遮罩图片无法覆盖的部分
            ColorArt colorArt = new ColorArt(resultBitmap);
            RectF rectF2 = new RectF(0,easyDesignWidth - 1,easyDesignWidth,easyDesignHeight);
            Paint paint = new Paint();
            paint.setColor(colorArt.getBackgroundColor());
            canvas.drawRect(rectF2,paint);

            easyMask = new EasyMask();
            easyMask.setMaskBitmap(resultBitmap);
        }
        return easyMask;
    }

    /**
     * 创建一个设计区
     * createEasySpace(150,50,650,700,Color.WHITE);
     * @return
     */
    public static EasySpace createEasySpace(int left,int top,int right,int bottom,int color,int paramsWidthCM,int paramsHeightCM){
        Matrix matrix = new Matrix();
        Paint  paint  = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect(left, top, right, bottom);
        return new EasySpace( rect , matrix ,paint ,color , paramsWidthCM, paramsHeightCM );
    }


    /**
     * 创建本地图片设计
     * @param bitmap
     * @param originalImgWidth
     * @param originalImgHeight
     * @return
     */
    public static LocalImageEasyDesign createLoaclImageEasyDesign(Bitmap bitmap ,int originalImgWidth ,int originalImgHeight){
        int     bitmapWidth  = bitmap.getWidth();
        int     bitmapHeight = bitmap.getHeight();
        RectF srcRect        = new RectF(0, 0, bitmapWidth, bitmapHeight);
        RectF   dstRect      = new RectF();
        float[] srcPs        = new float[]{
                0,0,
                bitmapWidth/2,0,
                bitmapWidth,0,
                bitmapWidth,bitmapHeight/2,
                bitmapWidth,bitmapHeight,
                bitmapWidth/2,bitmapHeight,
                0,bitmapHeight,
                0,bitmapHeight/2,
                bitmapWidth/2,bitmapHeight/2};
        float[]  dstPs       = srcPs.clone();
        Matrix matrix        = new Matrix();
        LocalImageEasyDesign localImageEasyDesign = new LocalImageEasyDesign(srcPs,dstPs,srcRect,dstRect,matrix,bitmap,originalImgWidth,originalImgHeight);
        return localImageEasyDesign;
    }

    /**
     * 创建远程图片设计
     * @param bitmap
     * @param originalImgWidth
     * @param originalImgHeight
     * @return
     */
    public static RemoteImageEasyDesign createRemoteImageEasyDesign(Bitmap bitmap , int originalImgWidth , int originalImgHeight){
        int     bitmapWidth  = bitmap.getWidth();
        int     bitmapHeight = bitmap.getHeight();
        RectF srcRect        = new RectF(0, 0, bitmapWidth, bitmapHeight);
        RectF   dstRect      = new RectF();
        float[] srcPs        = new float[]{
                0,0,
                bitmapWidth/2,0,
                bitmapWidth,0,
                bitmapWidth,bitmapHeight/2,
                bitmapWidth,bitmapHeight,
                bitmapWidth/2,bitmapHeight,
                0,bitmapHeight,
                0,bitmapHeight/2,
                bitmapWidth/2,bitmapHeight/2};
        float[]  dstPs       = srcPs.clone();
        Matrix matrix        = new Matrix();
        RemoteImageEasyDesign remoteImageEasyDesign = new RemoteImageEasyDesign(srcPs,dstPs,srcRect,dstRect,matrix,bitmap,originalImgWidth,originalImgHeight);
        return remoteImageEasyDesign;
    }

    /**
     * 创建贴图设计
     * @param bitmap
     * @return
     */
    public static StickImageDesign createStickImageDesign(Bitmap bitmap){
        int     bitmapWidth  = bitmap.getWidth();
        int     bitmapHeight = bitmap.getHeight();
        RectF   srcRect      = new RectF(0, 0, bitmapWidth, bitmapHeight);
        RectF   dstRect      = new RectF();
        float[] srcPs        = new float[]{
                0,0,
                bitmapWidth/2,0,
                bitmapWidth,0,
                bitmapWidth,bitmapHeight/2,
                bitmapWidth,bitmapHeight,
                bitmapWidth/2,bitmapHeight,
                0,bitmapHeight,
                0,bitmapHeight/2,
                bitmapWidth/2,bitmapHeight/2};
        float[]  dstPs       = srcPs.clone();
        Matrix matrix        = new Matrix();
        StickImageDesign stickImageDesign = new StickImageDesign(srcPs,dstPs,srcRect,dstRect,matrix,bitmap);
        return stickImageDesign;
    }

    /**
     * 创建一个图片设计
     * @return
     */
    public static ImageEasyDesign createImageDesign(Bitmap bitmap){
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
    public static TextEasyDesign createTextEasyDesign(String label){

        //新版本文本设计
        Paint paint     = new Paint();
        Rect  bound     =  new Rect();
        paint.getTextBounds(label,0,label.length(), bound);

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
        Matrix matrix        = new Matrix();
        TextEasyDesign textEasyDesign = new TextEasyDesign(srcPs,dstPs,srcRect,dstRect,matrix);
        textEasyDesign.setContent(label);
        //旧版本文本设计
       /*Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(label,0,label.length(), rect);
        TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        int text_width = getTextWidth(textPaint,label);
        Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;//居中对齐
        StaticLayout staticLayout = new StaticLayout(label, textPaint,text_width,alignment, 1.0f, 0.0f, false);

        int     mainBmpWidth  = staticLayout.getWidth();
        int     mainBmpHeight = staticLayout.getHeight();

        RectF srcRect         = new RectF(0, 0, staticLayout.getWidth(), staticLayout.getHeight());
        RectF dstRect         = new RectF();
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
        TextEasyDesign textEasyDesign =  new TextEasyDesign(srcPs,dstPs,srcRect,dstRect,matrix);
        textEasyDesign.setContent(label);*/
        return textEasyDesign;
    }

    public static Bitmap textAsBitmap(String text, float textSize) {
        TextPaint textPaint = new TextPaint();

        // textPaint.setARGB(0x31, 0x31, 0x31, 0);
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);

        StaticLayout layout = new StaticLayout(text, textPaint, 450,
                Layout.Alignment.ALIGN_NORMAL, 1.3f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20,
                layout.getHeight() + 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(10, 10);
        // canvas.drawColor(Color.GRAY);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
        layout.draw(canvas);
        return bitmap;
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
        int tran_x = p1.x - p2.x;
        int tran_y = p1.y - p2.y;
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
        //如果是贴图则返回false
       if(easyDesign.getImageEasyDesignType() == ImageEasyDesignType.STICKER){
            return false;
        }

        int     imageDynamicWidthDp   = ((ImageEasyDesign) easyDesign).getDstPsWidthDp();           //获取图片的动态宽度Dp
        int     imageDynamicHeightDp  = ((ImageEasyDesign) easyDesign).getDstPsHeightDp();          //设置图片的动态高度Dp

        int     imageDynamicWidthPx   = ConvertUtils.dp2px(imageDynamicWidthDp);                    //动态宽度Px
        int     imageDynamicHeightPx  = ConvertUtils.dp2px(imageDynamicHeightDp);                   //动态高度Px

        int     imageOriginalWidthPx  = ConvertUtils.dp2px(imageOriginalWidthDp);                   //原始宽度Px
        int     imageOriginalHeightPx = ConvertUtils.dp2px(imageOriginalHeightDp);                  //原始高度Px
        float   sizecn                = (float)(paramsWidthCM / 0.02 / areaDesignWidthPx);          //SizeCn
        int     imageMaxWidthPx       = (int) (imageOriginalWidthPx * sizecn);                      //图片最多宽度
        int     imageMaxHeightPx      = (int) (imageOriginalHeightPx * sizecn);                     //图片最大高度

        Log.i("EasyDesignHelper", "imageDynamicWidthDp:" + imageDynamicWidthDp);
        Log.i("EasyDesignHelper", "imageDynamicHeightDp:" + imageDynamicHeightDp);
        Log.i("EasyDesignHelper", "imageDynamicWidthPx:" + imageDynamicWidthPx);
        Log.i("EasyDesignHelper", "imageOriginalWidthPx:" + imageOriginalWidthPx);
        Log.i("EasyDesignHelper", "imageDynamicHeightPx:" + imageDynamicHeightPx);
        Log.i("EasyDesignHelper", "imageOriginalHeightPx:" + imageOriginalHeightPx);
        Log.i("EasyDesignHelper", "sizecn:" + sizecn);
        Log.i("EasyDesignHelper", "imageMaxWidthPx:" + imageMaxWidthPx);
        Log.i("EasyDesignHelper", "imageMaxHeightPx:" + imageMaxHeightPx);

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
