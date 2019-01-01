package com.handsome.health.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by zyj on 2018/12/24.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    protected void startIntent(Class classIntent){
        //实例化
        Intent intent = new Intent();
        //设置跳转的目的地
        intent.setClass(this,classIntent);
        //启动意图
        startActivity(intent);
    }
}
