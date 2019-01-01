package com.handsome.health;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.handsome.health.base.BaseActivity;
import com.handsome.health.net.Httputils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by zyj on 2018/12/26.
 */
public class AddActivity extends BaseActivity implements View.OnClickListener {
    private Button canaelBtn,addBtn,morningStartBtn,morningEndBtn;
    private EditText mileageEdit,bikeMileageEdit,nightRunEdit;
    private Button morningExerciseStartBtn,morningExerciseEndBtn;
    private Button walkStartBtn,walkEndBtn;
    private Button bikeStartBtn,bikeEndBtn;
    private Button swimStartBtn,swimEndBtn;
    private Button ballStartBtn,ballEndBtn;
    private Button nightStartBtn,nightEndBtn;
    private  Calendar calendar;
    private TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        calendar = Calendar.getInstance();
        initview();

        canaelBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        morningStartBtn.setOnClickListener(this);
        morningEndBtn.setOnClickListener(this);
        morningExerciseStartBtn.setOnClickListener(this);
        morningExerciseEndBtn.setOnClickListener(this);
        walkStartBtn.setOnClickListener(this);
        walkEndBtn.setOnClickListener(this);
        bikeStartBtn.setOnClickListener(this);
        bikeEndBtn.setOnClickListener(this);
        swimStartBtn.setOnClickListener(this);
        swimEndBtn.setOnClickListener(this);
        ballStartBtn.setOnClickListener(this);
        ballEndBtn.setOnClickListener(this);
        nightStartBtn.setOnClickListener(this);
        nightEndBtn.setOnClickListener(this);

    }
    private void initview(){
        canaelBtn = (Button)findViewById(R.id.add_cancel_btn);
        addBtn = (Button)findViewById(R.id.add_submit_btn);
        morningStartBtn = (Button)findViewById(R.id.add_morning_start_btn);
        morningEndBtn = (Button)findViewById(R.id.add_morning_end_btn);
        morningExerciseStartBtn = (Button)findViewById(R.id.add_morning_exercise_start_btn);
        morningExerciseEndBtn = (Button)findViewById(R.id.add_morning_exercise_end_btn);
        walkStartBtn = (Button)findViewById(R.id.add_walk_start_btn);
        walkEndBtn = (Button)findViewById(R.id.add_walk_end_btn);
        bikeStartBtn = (Button)findViewById(R.id.add_ball_start_btn);
        bikeEndBtn = (Button)findViewById(R.id.add_ball_end_btn);
        swimStartBtn = (Button)findViewById(R.id.add_swim_start_btn);
        swimEndBtn = (Button)findViewById(R.id.add_swim_end_btn);
        ballStartBtn = (Button)findViewById(R.id.add_ball_start_btn);
        ballEndBtn = (Button)findViewById(R.id.add_ball_end_btn);
        nightStartBtn = (Button)findViewById(R.id.add_night_run_start_btn);
        nightEndBtn = (Button)findViewById(R.id.add_night_run_end_btn);
        mileageEdit = (EditText)findViewById(R.id.add_mileage_edit);
        bikeMileageEdit = (EditText)findViewById(R.id.add_bike_mileage_edit);
        nightRunEdit = (EditText)findViewById(R.id.add_night_run_mileage_edit);
    }

    @Override
    public void onClick(final View v) {
        switch(v.getId()){
            case R.id.add_submit_btn:
                //提交信息
                if(morningStartBtn.getText().toString().trim().matches("[\\u4e00-\\u9fa5]*")){
                    toast("请选择开始时间");
                    return;
                }
                else if(morningEndBtn.getText().toString().trim().matches("[\\u4e00-\\u9fa5]*")){
                    toast("请选择结束时间");
                    return;
                }
                else if(TextUtils.isEmpty(mileageEdit.getText())){
                    toast("请输入里程数");
                    return;
                }
                insertData();
                //startIntent(CheckActivity.class);
                break;
            case R.id.add_cancel_btn:
                startIntent(CheckActivity.class);
                break;
            default:
                //晨跑的时间
                timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ((Button)v).setText(hourOfDay + ":" + minute);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                timePickerDialog.show();
                break;
        }
    }

    //提交网络数据
    private void insertData() {
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String today = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+"";
                HashMap<String ,Object> map = new HashMap<>();
                map.put("sport_name","晨跑");
                map.put("start_time",morningStartBtn.getText().toString().trim());
                map.put("end_time",morningEndBtn.getText().toString().trim());
                map.put("account", AppApplication.account);
                map.put("distance", mileageEdit.getText().toString().trim());
                final String result = Httputils.doPost("add_record.aspx",map);//insertSport     add_record.aspx    get_record.aspx
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            Log.e("TAG",result);
                            if(jsonObject.getInt("code")==0){
                                //添加成功
                                toast("添加成功");
                                finish();
                            }
                            else{
                                //添加失败
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
