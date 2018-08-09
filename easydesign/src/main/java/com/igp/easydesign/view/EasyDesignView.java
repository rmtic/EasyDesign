package com.igp.easydesign.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.igp.easydesign.bean.easycontrol.EasyControl;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.icon.EasyIcon;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiu on 2018/7/27.
 */

public class EasyDesignView extends BaseEasyDesignView {


    private Context context;                                                                        //上下文
    public List<BaseEasyDesign> baseEasyDesigns = new ArrayList<>();                                //会员设计集合
    public EasyControl          easyControl     = new EasyControl();                                //控制器
    public EasySpace            easySpace;                                                          //设计区
    public EasyMask             easyMask;                                                           //遮罩层图片


    @Override
    public EasyMask getEasyMask() {
        return easyMask;
    }

    @Override
    public EasySpace getEasySpace() {
        return easySpace;
    }

    @Override
    public List<BaseEasyDesign> getBaseEasyDesigns() {
        return baseEasyDesigns;
    }


    @Override
    public void setEasyControl(EasyControl easyControl) {
        this.easyControl = easyControl;
    }

    @Override
    public EasyControl getEasyControl() {
        return this.easyControl;
    }

    public EasyDesignView(Context context) {
        super(context);
        init(context);
    }

    public EasyDesignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EasyDesignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void setEasyIconList(List<EasyIcon> easyIconList) {
        if (getEasyControl() != null) {
            getEasyControl().setEasyIconList(easyIconList);
        }
    }


    @Override
    public void setSelectedEasyDesign(BaseEasyDesign easyDesign) {
        //设置当前的为选中，其他为不选中
        //插入设计的时候，如果控制器没有绑定设计，则绑定设计；
        //如果已经绑定设计，则不再调用绑定
        //如果绑定的设计是用一个设计，则不再执行绑定；
        /**
         * 执行选中设计
         * 如果 （控制器绑定的设计 ！= null）{
         *     如果 （被选中 ！=  控制器绑定的设计）{
         *        则绑定当前设计
         *     }
         * }
         */
        //如果控制器为空则抛出一次
        /*if (getEasyControl() == null) {
            throw new NullPointerException("必须设置控制器哦!");
        }*/
        if (getEasyControl() == null) {
            return;
        }
        //刷新BaseEasyDesign 的 isSelected
        if(baseEasyDesigns.contains(easyDesign)){
            for (BaseEasyDesign baseEasyDesign : getBaseEasyDesigns()) {
                baseEasyDesign.setSelected(easyDesign.equals(baseEasyDesign));
            }
        }else{
            notSelectAll();
        }

        //刷新绑定，重复则无需再绑定
        if (getEasyControl()!= null && getEasyControl().getBindEasyDesign() != easyDesign){
            getEasyControl().bindEasyDesign(easyDesign);
        }

        //通知选中事件
        if (onEasyDesignViewListener != null) {
            onEasyDesignViewListener.onEasyDesignChange(easyDesign,EasyEventType.SELECTED);
        }
        invalidate();
    }

    @Override
    public BaseEasyDesign getSelectedEasyDesign() {
        for (BaseEasyDesign baseEasyDesign : baseEasyDesigns) {
            if(baseEasyDesign.isSelected()){
                return baseEasyDesign;
            }
        }
        return null;
    }

    private void init(Context context) {
        this.context = context;
        //easyControl = new EasyControl();
    }

    /**
     * 设置遮罩层
     * @param easyMask
     */
    public void setEasyMask(EasyMask easyMask) {
        this.easyMask = easyMask;
        invalidate();
    }

    /**
     * 设置设计区
     * @param easySpace
     */
    public void setEasySpace(EasySpace easySpace) {
        this.easySpace = easySpace;
        invalidate();
    }

    /**
     * 方法：新增设计
     * 说明：新增[设计]到[会员设计集合]
     * @param easyDesign
     */
    public void addEasyDesign(BaseEasyDesign easyDesign){
        if (baseEasyDesigns.contains(easyDesign)) {
            return;
        }
        baseEasyDesigns.add(easyDesign);
        setSelectedEasyDesign(easyDesign);
        invalidate();
    }


    /**
     * 方法：移除设计
     * 说明：从[会员设计集合]移除[设计]
     * @param easyDesign
     */
    public void removeEasyDesign(BaseEasyDesign easyDesign){
        if (!baseEasyDesigns.contains(easyDesign)) {
            return;
        }
        baseEasyDesigns.remove(easyDesign);
        setSelectedEasyDesign(null);
        invalidate();
    }

    /**
     * 移除当前的设计
     */
    public void removeSelectedEasyDesign(){
        if (getSelectedEasyDesign() != null) {
            baseEasyDesigns.remove(getSelectedEasyDesign());
            setSelectedEasyDesign(null);
        }
        invalidate();
    }

    /**
     * 清空所有设计
     */
    public void removeAllEasyDesign(){
        if (getSelectedEasyDesign() != null) {
            baseEasyDesigns.clear();
            setSelectedEasyDesign(null);
        }
        invalidate();
    }

    /**
     * 方法：取消所有选中
     * 说明：当会员点击空白区域的时候使用；
     */
    public void notSelectAll(){
        for (BaseEasyDesign baseEasyDesign : baseEasyDesigns) {
            baseEasyDesign.setSelected(false);
        }
        invalidate();
    }

    /**
     * 方法：创建DesignView的截图
     * 用途：生成整张设计图，提供给createDesignBimap()和ImageCrop（）方法使用
     * 说明：1. 创建新的Bitmap对象，宽和高等于DesignNowView的宽
     *      2. Bitmap上绘制当前会员的所有设计对象（ 注意：如果想文字在最上面，图片在最下面 这种特殊要求。
     *      可以根据设计的类型，分别进行绘制）
     *      3. 绘制的时候应该先锁定画布，等绘制完毕后在复原；
     *
     */
    public Bitmap createDesignViewBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        Paint p = new Paint();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if(null != getBaseEasyDesigns()) {
            for (BaseEasyDesign baseEasyDesign : getBaseEasyDesigns()) {
                baseEasyDesign.draw(canvas);
            }
        }
        canvas.restore();
        return bitmap;
    }
    /**
     *
     * 方法：截图
     * 用途：截取设计区区域的图片
     * 说明：
     * 注意: 使用Bitmap.createDesignViewBitmap()的时候需要知道如下
     * 1. x + (width - x )  < bitmap.getweidth();
     * 2. y + (height - y ) < bitmap.getheight();
     * kusdom,给出的width = right - left; height = bottom - top
     */
    public Bitmap createDesignBimap() {
        int x      = getEasySpace().getRect().left;
        int y      = getEasySpace().getRect().top;
        int right  = getEasySpace().getRect().right;
        int bottom = getEasySpace().getRect().bottom;
        int height = bottom - y;
        int width  = right  - x;
        return Bitmap.createBitmap(createDesignViewBitmap(),x,y,width,height);
    }

}
