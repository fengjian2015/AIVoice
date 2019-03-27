package com.translation.component.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.translation.R;
import com.translation.androidlib.observer.EventManager;
import com.translation.androidlib.observer.EventMsg;
import com.translation.androidlib.observer.OnEventMsgListener;
import java.lang.reflect.Field;
import butterknife.ButterKnife;

/**
 * Created by Darren on 2018/12/13.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements View.OnClickListener, OnEventMsgListener {

    protected BaseActivity baseInstance;
    private RelativeLayout baseLayoutRl;
    private ImageView baseBackIv, baseRightIv;
    private TextView baseCenterTv, baseRightTv, baseDividerTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        baseInstance = this;
        init();
        initViews();
        getIntentData();
        initViewsData();
        setListener();

    }

    protected abstract int getLayoutId();


    protected void onWindowFocus() {

    }

    protected void initViews(){}

    protected void initViewsData(){

    }

    protected void setListener(){

    }

    protected void getIntentData(){

    }

    @Override
    public void onClick(View v) {

    }

    protected Context getAppContext(){
        return getApplicationContext();
    }

    protected Activity getHostActivity(){
        return this;
    }

    @Override
    public void onEventMsg(EventMsg msg) {

    }

    /**
     * 设置titleLayout
     */
    protected void setTitle(boolean isShow, int stringId) {
        if (isShow){
            setTitle(true, getString(stringId));
        }else {
            setTitle(false, "");
        }
    }

    protected void setTitle(boolean isShow, String title){
        if (isShow) {
            baseCenterTv.setText(title);
            baseLayoutRl.setVisibility(View.VISIBLE);
            baseBackIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        } else {
            baseLayoutRl.setVisibility(View.GONE);
            baseBackIv = null;
        }
    }

    protected void setDividerInvisible(){
        baseDividerTv.setVisibility(View.INVISIBLE);
    }

    protected void setRightImg(int resourceId){
        baseRightIv.setVisibility(View.VISIBLE);
        baseRightIv.setImageResource(resourceId);
        baseRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightImgClick();
            }
        });
    }

    protected void setRightTv(int stringId){
        baseRightTv.setVisibility(View.VISIBLE);
        baseRightTv.setText(getString(stringId));
        baseRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightTvClick();
            }
        });
    }

    protected void onRightImgClick(){

    }



    protected void onRightTvClick(){

    }

    /**
     * 初始化
     */
    private void init() {
        View view = View.inflate(this, getLayoutId(), null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        ViewFlipper containerView = findViewById(R.id.vf_base_layout_container);
        containerView.addView(view, lp);

        baseLayoutRl = findViewById(R.id.rl_base_title_layout);
        baseBackIv = findViewById(R.id.iv_base_left_back);
        baseCenterTv = findViewById(R.id.tv_base_center_title);
        baseRightIv = findViewById(R.id.iv_base_right_img);
        baseRightTv = findViewById(R.id.tv_base_right_text);
        baseDividerTv = findViewById(R.id.tv_base_title_divider);

        ButterKnife.bind(this);
        EventManager.getInstance().register(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            //点击空白位置 隐藏软键盘
            InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (manager != null) {
                return manager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            onWindowFocus();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixInputMethod(this);
        EventManager.getInstance().unRegister(this);
    }

    /**
     * 解决输入法导致的内存泄漏
     */
    public static void fixInputMethod(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = null;
        try {
            inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (inputMethodManager == null) {
            return;
        }
        Field[] declaredFields = inputMethodManager.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(inputMethodManager);
                if (obj == null || !(obj instanceof View)) {
                    continue;
                }
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(inputMethodManager, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

}
