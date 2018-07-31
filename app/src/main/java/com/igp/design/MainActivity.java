package com.igp.design;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.igp.easydesign.bean.easycontrol.EasyControl;
import com.igp.easydesign.bean.easydesign.ImageEasyDesign;
import com.igp.easydesign.bean.icon.EasyIcon;
import com.igp.easydesign.bean.icon.EasyIconType;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;
import com.igp.easydesign.helper.EasyDesignHelper;
import com.igp.easydesign.view.EasyDesignView;

import org.michaelevans.colorart.library.ColorArt;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   // DesignView designView;
    private EasyDesignView   mEasyDesignView;
    private EasyDesignHelper mEasyDesignHelper;
    private Button btnRoate,btnRoateZero,btnSelect1,btnSelect2;

    ImageEasyDesign imageEasyDesign1;
    ImageEasyDesign imageEasyDesign2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mEasyDesignView   = findViewById(R.id.easydesignview);
       btnRoate          = findViewById(R.id.btn_roate);
       btnRoateZero      = findViewById(R.id.btn_roate_zero);
       btnSelect1        = findViewById(R.id.btn_select1);
       btnSelect2        = findViewById(R.id.btn_select2);

       /**帮助类*/
      mEasyDesignHelper = new EasyDesignHelper(this);                                      //帮助会员相关创建

       /**创建相关图片*/
       Bitmap bitmap1           = mEasyDesignHelper.getLocalBitmap(R.mipmap.test);                  //创建【图片设计1】
       Bitmap bitmap2           = mEasyDesignHelper.getLocalBitmap(R.mipmap.a);                     //创建【图片设计2】
       Bitmap bitmapWarning     = mEasyDesignHelper.getLocalBitmap(R.mipmap.ic_warning);            //创建【警告     】图片
       Bitmap draftBoxUncheck   = mEasyDesignHelper.createEasyIconBitmap(mEasyDesignHelper.getLocalBitmap(R.drawable.draft_box_uncheck));   //创建【旋转+放大】图片
       imageEasyDesign1  = mEasyDesignHelper.createImageDesign(bitmap1);                            //创建【图片设计 】
       imageEasyDesign2  = mEasyDesignHelper.createImageDesign(bitmap2);                            //创建【图片设计 】

        RectF markerCopyRect    = new RectF(0, 0, draftBoxUncheck.getWidth(), draftBoxUncheck.getHeight());//旋转+放大 标记边界

        /** 平台 和 控制层 的配置 */

        EasySpace easySpace = EasyDesignHelper.createEasySpace();
        mEasyDesignView.setEasySpace(easySpace);

        EasyMask easyMask = EasyDesignHelper.createEasyMask(mEasyDesignHelper.getLocalBitmap(R.drawable.bg_mask));
        mEasyDesignView.setEasyMask(easyMask);



       List<EasyIcon> easyIconList = new ArrayList<>();                                             //控制器【图标集合】
       easyIconList.add(new EasyIcon(draftBoxUncheck,markerCopyRect, EasyIconType.RIGHT_BOTTOM));   //添加  【右下角图标】
       EasyControl easyControl = new EasyControl();                                                 //创建一个控制器模型
       easyControl.setEasyIconList(easyIconList);                                                   //给控制器设置一组图标
       mEasyDesignView.setEasyControl(easyControl);                                                 //设置控制层的模型

        /** 相关手动操作设计的实现 */
       mEasyDesignView.addEasyDesign(imageEasyDesign1);                                             //添加一个图片设计模型
       mEasyDesignView.addEasyDesign(imageEasyDesign2);                                             //添加一个图片设计模型

       /** 相关手动操作设计的实现 */
       //mEasyDesignView.setSelectedEasyDesign(imageEasyDesign2);                                   //手动操作【选中】
       //imageEasyDesign2.postTranslate(300,500);                                                   //手动操作【移动】
       //imageEasyDesign2.postRotate(45);                                                           //手动操作【旋转】
       //动画旋转
       //动画放大

       /**MainActivity 按钮操作的事件*/
       btnRoate    .setOnClickListener(this);                                                       //旋转
       btnRoateZero.setOnClickListener(this);                                                       //旋转
       btnSelect1  .setOnClickListener(this);                                                       //选中1
       btnSelect2  .setOnClickListener(this);                                                       //选中2
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_roate:
                /*if ( mEasyDesignView.getSelectedEasyDesign() != null) {
                    mEasyDesignView.getSelectedEasyDesign().postRotate(10);
                    mEasyDesignView.invalidate();
                }*/
                mEasyDesignView.rotateValueAnimator(30);
                break;
            case R.id.btn_roate_zero:
                mEasyDesignView.rotate2Degree(0);
                break;
            case R.id.btn_select1:
                if (imageEasyDesign1 != null) {
                    mEasyDesignView.setSelectedEasyDesign(imageEasyDesign1);
                }
                break;
            case R.id.btn_select2:
                if (imageEasyDesign2 != null) {
                    mEasyDesignView.setSelectedEasyDesign(imageEasyDesign2);
                }
                break;
            default:
                break;
        }
    }
}
