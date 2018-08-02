package com.igp.easydesign.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.igp.easydesign.bean.easycontrol.EasyControl;
import com.igp.easydesign.bean.easycontrol.OnBindEasyDesignListener;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiu on 2018/7/27.
 */

public class EasyDesignView extends BaseEasyDesignView implements OnBindEasyDesignListener {


    private Context context;                                        //上下文
    public List<BaseEasyDesign> baseEasyDesigns = new ArrayList<>();//会员设计集合
    public EasyControl          easyControl;                        //控制器
    public EasySpace            easySpace;                          //设计区
    public EasyMask             easyMask;                           //遮罩层图片

    /*
        private List<BaseEasyDesign> baseEasyDesigns = new ArrayList<>();   //会员设计集合
                                                                            //TODO 待开发 设计平台基本信息
    */



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
        this.easyControl.setOnBindEasyDesignListener(this);

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
        //选中产品
        if (getBaseEasyDesigns().contains(easyDesign)) {
            for (BaseEasyDesign baseEasyDesign : getBaseEasyDesigns()) {
                baseEasyDesign.setSelected(easyDesign.equals(baseEasyDesign));
            }
        }
        //刷新绑定
        if (getEasyControl()!= null && getEasyControl().getBindEasyDesign() != easyDesign){
            getEasyControl().bindEasyDesign(easyDesign);
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

    @Override
    public void onBindEasyDesignListener(BaseEasyDesign easyDesign) {
        //设置选中
        if(baseEasyDesigns.contains(easyDesign)){
            setSelectedEasyDesign(easyDesign);
        }else{
            notSelectAll();
        }
        invalidate();
    }
}