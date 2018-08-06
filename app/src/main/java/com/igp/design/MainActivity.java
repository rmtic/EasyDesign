package com.igp.design;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesignType;
import com.igp.easydesign.bean.easydesign.svg.SvgEasyDesign;
import com.igp.easydesign.bean.easydesign.text.TextEasyDesign;
import com.igp.easydesign.bean.icon.EasyIcon;
import com.igp.easydesign.bean.icon.EasyIconType;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;
import com.igp.easydesign.helper.EasyDesignHelper;
import com.igp.easydesign.view.EasyDesignView;
import com.igp.easydesign.view.OnEasyDesignViewListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   // DesignView designView;
    private EasyDesignView   mEasyDesignView;
    private Button btnRoate,btnRoateZero,btnSelect1,btnSelect2,btnNarrow,btnAmplification;

    ImageEasyDesign imageEasyDesign1;
    ImageEasyDesign imageEasyDesign2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_imagedesign:
                mEasyDesignView.addEasyDesign(imageEasyDesign1);
                break;
            case R.id.action_del_imagedesign:
                if(mEasyDesignView.getSelectedEasyDesign() != null){
                    mEasyDesignView.removeSelectedEasyDesign();
                }
                break;
            case R.id.action_del_alldesign:
                mEasyDesignView.removeAllEasyDesign();
                break;
            case R.id.action_sel_imagedesign1:
                if (imageEasyDesign1 != null) {
                    mEasyDesignView.setSelectedEasyDesign(imageEasyDesign1);
                }
            case R.id.action_amplification:
                if (mEasyDesignView.getSelectedEasyDesign() != null) {
                    mEasyDesignView.getSelectedEasyDesign().postScale(1.02f,1.02f);//新版本
                    mEasyDesignView.invalidate();
                }
                break;
            case R.id.action_narrow:
                if (mEasyDesignView.getSelectedEasyDesign() != null) {
                    mEasyDesignView.getSelectedEasyDesign().postScale(0.98f,0.98f);
                    mEasyDesignView.invalidate();
                }
                break;
            case R.id.action_anim_to_rotate0:
                if (mEasyDesignView.getSelectedEasyDesign() != null) {
                    mEasyDesignView.rotate2Degree(0);
                }
                break;
            case R.id.action_anim_rotate30:
                if (mEasyDesignView.getSelectedEasyDesign() != null) {
                    mEasyDesignView.rotateValueAnimator(30);
                }
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mEasyDesignView   = findViewById(R.id.easydesignview);


       /**创建相关图片*/
       Bitmap bitmap1           = EasyDesignHelper.getLocalBitmap(MainActivity.this,R.mipmap.test);                  //创建【图片设计1】
       Bitmap bitmap2           = EasyDesignHelper.getLocalBitmap(MainActivity.this,R.mipmap.a);                     //创建【图片设计2】
       Bitmap bitmapWarning     = EasyDesignHelper.getLocalBitmap(MainActivity.this,R.mipmap.ic_warning);            //创建【警告     】图片
       Bitmap draftBoxUncheck   = EasyDesignHelper.createEasyIconBitmap(EasyDesignHelper.getLocalBitmap(MainActivity.this,R.drawable.draft_box_uncheck));//创建【旋转+放大】图片
       imageEasyDesign1  = EasyDesignHelper.createImageDesign(bitmap1, ImageEasyDesignType.LOCAL_ALBUM);                                //创建【图片设计 】 本地图片类型
       imageEasyDesign2  = EasyDesignHelper.createImageDesign(bitmap2, ImageEasyDesignType.REMOTE_ALBUM);                               //创建【图片设计 】 远程图片类型



       RectF markerCopyRect    = new RectF(0, 0, draftBoxUncheck.getWidth(), draftBoxUncheck.getHeight());//旋转+放大 标记边界

        /** 平台 和 控制层 的配置     */

        EasySpace easySpace = EasyDesignHelper.createEasySpace(150,50,650,700, Color.WHITE,10,10);
        easySpace.setBgColor(Color.WHITE);
        mEasyDesignView.setEasySpace(easySpace);

        EasyMask easyMask = EasyDesignHelper.createEasyMask(EasyDesignHelper.getLocalBitmap(MainActivity.this,R.drawable.bg_mask));
        mEasyDesignView.setEasyMask(easyMask);

       List<EasyIcon> easyIconList = new ArrayList<>();                                             //控制器【图标集合】
       easyIconList.add(new EasyIcon(draftBoxUncheck,markerCopyRect, EasyIconType.RIGHT_BOTTOM));   //添加  【右下角图标】
       mEasyDesignView.setEasyIconList(easyIconList);                                               //给控制器设置一组图标
       mEasyDesignView.setOnEasyDesignViewListener(new OnEasyDesignViewListener() {
           @Override
           public void onEasyDesignChange(BaseEasyDesign easyDesign) {
               if (easyDesign instanceof ImageEasyDesign) {
                   Log.i("print", "((ImageEasyDesign) easyDesign).getDynamicWidthDp():"  + ((ImageEasyDesign) easyDesign).getDynamicWidthDp());
                   Log.i("print", "((ImageEasyDesign) easyDesign).getDynamicHeightDp():" + ((ImageEasyDesign) easyDesign).getDynamicHeightDp());
               }
           }

           @Override
           public void onEasyDesignSelected(BaseEasyDesign easyDesign) {
               if (easyDesign instanceof TextEasyDesign) {

               }else if(easyDesign instanceof ImageEasyDesign){

               }else if(easyDesign instanceof SvgEasyDesign){

               }
           }
       });

        /** 相关手动操作设计的实现 */

       mEasyDesignView.addEasyDesign(imageEasyDesign1);                                             //添加一个图片设计模型
       mEasyDesignView.addEasyDesign(imageEasyDesign2);                                             //添加一个图片设计模型



       TextEasyDesign textEasyDesign =  EasyDesignHelper.createTextEasyDesign("TEXT HERE"); //文本设计
       mEasyDesignView.addEasyDesign(textEasyDesign);                                               //添加文本设计
       mEasyDesignView.setSelectedEasyDesign(textEasyDesign);                                        //选中文本设计
       textEasyDesign.postTranslate(300,300);
        mEasyDesignView.invalidate();



       /** 相关手动操作设计的实现 */
       //mEasyDesignView.setSelectedEasyDesign(imageEasyDesign2);                                   //手动操作【选中】
       //imageEasyDesign2.postTranslate(300,500);                                                   //手动操作【移动】
       //imageEasyDesign2.postRotate(45);                                                           //手动操作【旋转】
       //动画旋转
       //动画放大
    }

    /**
     *方法：是否为模糊图片
     *说明：
     * ---------------------------------------------------------------------------------------------
     * 比例                   = 设计区宽  （PX） / 设计区宽（CM）
     * 图片最大印刷宽度（CM） = 图片原始宽（PX） * 0.02;
     * 图片最大印刷高度（CM） = 图片原始高（PX） * 0.02;
     * 如果（ 图片拖动后宽度（CM）> 图片最大印刷宽度（CM）|| 图片拖动后高度（CM） > 图片最大印刷高度（CM））
     * 返回 True
     * 否则 False
     * ---------------------------------------------------------------------------------------------
     * @param designAreaWidthPx     设计区（PX）
     * @param designAreaWidthCm     设计区宽（CM）
     * @param imageWidthPx          图片原始宽（PX）
     * @param imageHeightPx         图片原始高（PX）
     * @param easyDesignWidthPx     图片拖动后宽度（CM）
     * @param easyDesignHeightPx    图片拖动后高度（CM）
     * @return
     */
    public Boolean isBlur(int designAreaWidthPx , int designAreaWidthCm ,int imageWidthPx ,int imageHeightPx,int easyDesignWidthPx,int easyDesignHeightPx, float sizecn){
        float scale          = designAreaWidthPx / designAreaWidthCm;
        int maxImageWidthCm  = (int) (imageWidthPx  * 0.02 );
        int maxImageHeightCm = (int) (imageHeightPx * 0.02 );
        if(easyDesignWidthPx * sizecn * scale > maxImageWidthCm || easyDesignHeightPx * sizecn * scale > maxImageHeightCm){
            return true;
        }
        return false;
    }

}
