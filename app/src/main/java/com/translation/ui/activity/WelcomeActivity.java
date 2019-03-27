package com.translation.ui.activity;

import android.content.Intent;
import android.os.Handler;

import com.translation.R;
import com.translation.component.base.BaseActivity;
import com.translation.component.constant.SpCons;
import com.translation.ui.MainActivity;


/**
 * Created by Darren on 2018/12/13.
 */
public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler();
    private Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            if (SpCons.getLoginState(getAppContext())) {
                startActivity(new Intent(getAppContext(), MainActivity.class));
            } else {
                startActivity(new Intent(getAppContext(), LoginActivity.class));
            }
            WelcomeActivity.this.finish();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews() {
        setTitle(false, "");
    }

    @Override
    protected void initViewsData() {
        handler.postDelayed(delayRunnable, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(delayRunnable);
        delayRunnable = null;
    }


}
