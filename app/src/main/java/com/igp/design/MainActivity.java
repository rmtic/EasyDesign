package com.igp.design;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.LocalImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.RemoteImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.StickImageDesign;
import com.igp.easydesign.bean.easydesign.text.TextEasyDesign;
import com.igp.easydesign.bean.icon.EasyIcon;
import com.igp.easydesign.bean.icon.EasyIconType;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;
import com.igp.easydesign.helper.EasyDesignHelper;
import com.igp.easydesign.view.EasyDesignView;
import com.igp.easydesign.view.EasyEventType;
import com.igp.easydesign.view.OnEasyDesignViewListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EasyDesignView   mEasyDesignView;
    private EditText etInput;
    LocalImageEasyDesign  localImageDesign;
    RemoteImageEasyDesign remoteImageDesign;
    StickImageDesign      stickImageDesign;


    TextEasyDesign textEasyDesign;
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
                mEasyDesignView.addEasyDesign(localImageDesign);
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
                if (localImageDesign != null) {
                    mEasyDesignView.setSelectedEasyDesign(localImageDesign);
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
            case R.id.action_clone:
                ToastUtils.showLong("克隆待开发");
                /*TextEasyDesign textEasyDesign2 = (TextEasyDesign)textEasyDesign.clone();
                textEasyDesign2.postScale(5.5f,5.5f);
                textEasyDesign2.setTextColor(Color.BLUE);
                mEasyDesignView.addEasyDesign(textEasyDesign2);                                    //添加文本设计*/
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

        mEasyDesignView.setEnableDrawDstRectBg  (true);                                                                                                            //是否允许绘制背景
        mEasyDesignView.setEnableDrawEasySpace  (true);                                                                                                            //是否允许绘制设计区
        mEasyDesignView.setEnableDrawEasyMask   (true);                                                                                                            //是否允许绘制遮罩
        mEasyDesignView.setEnableDrawDstPsLine  (true);                                                                                                            //是否绘制小矩形边框
        mEasyDesignView.setEnableDrawDstRectLine(true);                                                                                                            //是否绘制矩阵范围的框
        mEasyDesignView.setEnableDrawGridLine   (true);                                                                                                            //是否绘制矩阵范围的网格
        mEasyDesignView.setEnableDrawDstpsPoint (true);                                                                                                            //是否绘制矩阵点
        mEasyDesignView.setEnableDrawLeetToptips(true);                                                                                                            //是否绘制提示文字
        mEasyDesignView.setEnableDrawEasyIcons  (true);                                                                                                            //是否绘制控制图标

       /**创建相关图片*/
       Bitmap bitmap1           = EasyDesignHelper.getLocalBitmap(MainActivity.this,R.mipmap.test);                                                      //创建【图片设计1】
       Bitmap bitmap2           = EasyDesignHelper.getLocalBitmap(MainActivity.this,R.mipmap.a);                                                         //创建【图片设计2】
       Bitmap stickBitmap       = EasyDesignHelper.getLocalBitmap(MainActivity.this,R.mipmap.cool);                                                      //创建【图片设计2】
       Bitmap draftBoxUncheck   = EasyDesignHelper.createEasyIconBitmap(EasyDesignHelper.getLocalBitmap(MainActivity.this,R.drawable.draft_box_uncheck));//创建【旋转+放大】图片


       localImageDesign  = EasyDesignHelper.createLoaclImageEasyDesign(bitmap1 ,200,200);                                     //创建【图片设计 】 本地图片类型
       remoteImageDesign = EasyDesignHelper.createRemoteImageEasyDesign(bitmap2,300,300);                                     //创建【图片设计 】 远程图片类型
       stickImageDesign  = EasyDesignHelper.createStickImageDesign(stickBitmap);





       RectF markerCopyRect    = new RectF(0, 0, draftBoxUncheck.getWidth(), draftBoxUncheck.getHeight());//旋转+放大 标记边界

        /** 平台 和 控制层 的配置     */

        EasySpace easySpace = EasyDesignHelper.createEasySpace(150,50,650,700, Color.WHITE,10,10);
        easySpace.setBgColor(Color.WHITE);
        mEasyDesignView.setEasySpace(easySpace);

        EasyMask easyMask = EasyDesignHelper.createEasyMask(EasyDesignHelper.getLocalBitmap(MainActivity.this,R.drawable.bg_mask));
        mEasyDesignView.setEasyMask(easyMask);

       List<EasyIcon> easyIconList = new ArrayList<>();                                             //控制器【图标集合】
       EasyIcon easyIcon = new EasyIcon(draftBoxUncheck,EasyIconType.RIGHT_BOTTOM);                //添加  【右下角图标】
       easyIconList.add(easyIcon);


       mEasyDesignView.setEasyIconList(easyIconList);                                               //给控制器设置一组图标
       mEasyDesignView.setOnEasyDesignViewListener(new OnEasyDesignViewListener() {
           @Override
           public void onEasyDesignChange(BaseEasyDesign easyDesign, EasyEventType easyEventType) {
               switch (easyEventType) {
                   case RESIZE:
                       if (easyDesign instanceof ImageEasyDesign) {
                           //用来判断模糊
                           Log.i("print", "((ImageEasyDesign) easyDesign).getDynamicWidthDp():"  + ((ImageEasyDesign) easyDesign).getDstPsWidthDp());
                           Log.i("print", "((ImageEasyDesign) easyDesign).getDynamicHeightDp():" + ((ImageEasyDesign) easyDesign).getDstPsHeightDp());
                       }
                       break;
                   case ADD:
                       break;
                   case REMOVE:
                       break;
                   case SELECTED:
                       break;
                   case LOCK:
                       break;
                   default:
                       break;
               }
           }
       });

        /** 相关手动操作设计的实现 */



       stickImageDesign.postTranslate(easySpace.getRect().centerX(),easySpace.getRect().centerY());

       mEasyDesignView.addEasyDesign(localImageDesign);                                             //添加一个图片设计模型
       mEasyDesignView.addEasyDesign(remoteImageDesign);                                            //添加一个图片设计模型
       mEasyDesignView.addEasyDesign(stickImageDesign);                                             //添加一个贴纸设计模型


       textEasyDesign =  EasyDesignHelper.createTextEasyDesign("TEXT HERE");//文本设计
       textEasyDesign.postScale(5.5f,5.5f);
       textEasyDesign.setTextColor(Color.RED);
       mEasyDesignView.addEasyDesign(textEasyDesign);                                               //添加文本设计


       etInput   = findViewById(R.id.et_input);
       etInput.setText(textEasyDesign.getContent());
       etInput.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (textEasyDesign != null) {
                   textEasyDesign.setContent(s.toString());
                   mEasyDesignView.invalidate();
               }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });



       mEasyDesignView.setSelectedEasyDesign(textEasyDesign);                                       //选中文本设计
       textEasyDesign.postTranslate(300,300);
       mEasyDesignView.invalidate();




        mEasyDesignView.setOnEasyDesignViewListener(new OnEasyDesignViewListener() {
            @Override
            public void onEasyDesignChange(BaseEasyDesign easyDesign, EasyEventType easyEventType) {
                //这是设计组件发生事件变化
                Log.i("print", "easyEventType:" + easyEventType.name());
            }
        });


       /** 相关手动操作设计的实现 */
       //mEasyDesignView.setSelectedEasyDesign(remoteImageDesign);                                   //手动操作【选中】
       //remoteImageDesign.postTranslate(300,500);                                                   //手动操作【移动】
       //remoteImageDesign.postRotate(45);                                                           //手动操作【旋转】
       //动画旋转
       //动画放大
    }
}
