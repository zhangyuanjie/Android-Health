package com.handsome.health;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.handsome.health.base.BaseActivity;
import com.handsome.health.net.Httputils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by zyj on 2018/12/24.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button backBtn,regBtn,birthBtn;
    private EditText accountEdit,nameEdit,pwdEdit,repwdEdit;
    private RadioGroup rg;
    private RadioButton rb_man,rb_woman;
    private Calendar calendar;
    private String sex = "男";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        //注册监听事件
        backBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        birthBtn.setOnClickListener(this);
        //获取日期对象
        calendar = Calendar.getInstance();
        birthBtn.setText(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_man){
                    //男
                    sex = "男";
                }
                else if(checkedId == R.id.radio_woman){
                    //女
                    sex = "女";
                }
                else{
                    //保密
                    sex = "保密";
                }
            }
        });
    }
    private void initView(){
        backBtn = (Button)findViewById(R.id.reg_back_btn);
        regBtn = (Button)findViewById(R.id.reg_ok_btn);
        birthBtn = (Button)findViewById(R.id.reg_birthday);
        accountEdit = (EditText)findViewById(R.id.reg_account);
        nameEdit = (EditText)findViewById(R.id.reg_name);
        pwdEdit = (EditText)findViewById(R.id.reg_password);
        repwdEdit = (EditText)findViewById(R.id.reg_repassword);
        rg = (RadioGroup)findViewById(R.id.reg_sex);
        rb_man = (RadioButton)findViewById(R.id.radio_man);
        rb_woman = (RadioButton)findViewById(R.id.radio_woman);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reg_back_btn:
                //返回
                finish();
                break;
            case R.id.reg_birthday:
                //生日
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthBtn.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.reg_ok_btn:
                //确定注册
                if(TextUtils.isEmpty(accountEdit.getText())){
                    toast("账号不能为空");
                    return ;
                }
                else if(TextUtils.isEmpty(nameEdit.getText())){
                    toast("姓名不能为空");
                    return;
                }
                else if(TextUtils.isEmpty(pwdEdit.getText())){
                    toast("密码不能为空");
                    return;
                }
                else if(!pwdEdit.getText().toString().trim().equals(repwdEdit.getText().toString().trim())) {
                        toast("俩次密码输入不一致");
                    return;
                }
                else{
                    //提交
                    reg();
                }
                break;

        }
    }
    //提交注册信息
    private void reg() {
        //创建线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HashMap<String ,Object> map = new HashMap<>();
                map.put("account",accountEdit.getText().toString().trim());
                map.put("name",nameEdit.getText().toString().trim());
                map.put("sex",sex);
                map.put("birthday",birthBtn.getText().toString().trim());
                map.put("password", pwdEdit.getText().toString().trim());
                final String result = Httputils.doPost("regist.aspx", map);
                if(result!=null){
                    //运行在主线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getInt("code")==0){
                                    //注册成功
                                    toast("注册成功");
                                    finish();
                                }
                                else{
                                    //注册失败
                                    toast("msg");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        };
        //启动线程
        new Thread(runnable).start();
    }
}
