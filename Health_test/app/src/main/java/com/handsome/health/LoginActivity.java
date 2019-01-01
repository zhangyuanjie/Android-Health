package com.handsome.health;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.handsome.health.base.BaseActivity;
import com.handsome.health.net.Httputils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by zyj on 2018/12/24.
 */
public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private EditText accountEdit, pwdEdit;
    private Button regBtn, loginBtn;
    private CheckBox rememberCheckBox;
    private SharedPreferences spf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        //设置按钮的监听事件
        regBtn.setOnClickListener(listener);
        loginBtn.setOnClickListener(listener);
        rememberCheckBox.setOnCheckedChangeListener(this);
        spf = getSharedPreferences("user",MODE_PRIVATE);

    }

    private void initview() {
        accountEdit = (EditText) findViewById(R.id.login_account);
        pwdEdit = (EditText) findViewById(R.id.login_password);
        regBtn = (Button) findViewById(R.id.login_reg_btn);
        loginBtn = (Button) findViewById(R.id.login_login_btn);
        rememberCheckBox = (CheckBox) findViewById(R.id.login_remember_password);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_login_btn:
                    if(TextUtils.isEmpty(accountEdit.getText())){
                        toast("用户名不能为空!");
                        return;
                    }else if(TextUtils.isEmpty(pwdEdit.getText())){
                        toast("密码不能为空!");
                        return;
                    }else {
                        //执行登录
                        login();
                    }
                    break;
                case R.id.login_reg_btn:
                    startIntent(RegisterActivity.class);
                    break;
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //获取编辑对象
        //SharedPreferences.Editor editor = spf.edit();
        if(isChecked){
            //选中
        }else {
            //未选中
        }
    }

    private void login(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HashMap<String,Object> map = new HashMap<>();
                map.put("account",accountEdit.getText().toString().trim());
                map.put("password",pwdEdit.getText().toString().trim());
                final String result = Httputils.doPost("login.aspx",map);
                Log.e("TAG","------->"+result);
                //运行在主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //主线程
                        //toast(result);
                        try {
                            //将数据转化为json格式
                            JSONObject jsonObject = new JSONObject(result);
                            //取出json数据
                            int code = jsonObject.getInt("code");
                            if(code == 0){
                                //登录成功
                                AppApplication.account = accountEdit.getText().toString().trim();
                                toast("登录成功");
                                startIntent(CheckActivity.class);
                            }
                            else{
                                toast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        //启动线程
        new Thread(runnable).start();
    }
}
