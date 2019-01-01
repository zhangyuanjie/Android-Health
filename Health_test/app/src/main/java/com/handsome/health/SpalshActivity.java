package com.handsome.health;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.handsome.health.base.BaseActivity;



/**
 * Created by zyj on 2018/12/24.
 */
public class SpalshActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startIntent(LoginActivity.class);
                finish();
            }
        },2000);
    }
}
