package com.handsome.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.handsome.health.base.BaseActivity;

/**
 * Created by zyj on 2018/12/26.
 */
public class CheckActivity extends BaseActivity implements View.OnClickListener {
    private Button backBtn,addBtn,morningBtn,morningExerciseBtn,walkBtn,bikeBtn,swimBtn,ballBtn,nightRunBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initview();
        backBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        morningBtn.setOnClickListener(this);
        morningExerciseBtn.setOnClickListener(this);
        walkBtn.setOnClickListener(this);
        bikeBtn.setOnClickListener(this);
        swimBtn.setOnClickListener(this);
        ballBtn.setOnClickListener(this);
        nightRunBtn.setOnClickListener(this);
    }
    private void initview(){
        backBtn = (Button)findViewById(R.id.main_back_btn);
        addBtn = (Button)findViewById(R.id.main_add_btn);
        morningBtn = (Button)findViewById(R.id.main_morning_btn);
        morningExerciseBtn = (Button)findViewById(R.id.main_morning_exercise_btn);
        walkBtn = (Button)findViewById(R.id.main_walk_btn);
        bikeBtn = (Button)findViewById(R.id.main_bike_btn);
        swimBtn = (Button)findViewById(R.id.main_swim_btn);
        ballBtn = (Button)findViewById(R.id.main_ball_btn);
        nightRunBtn = (Button)findViewById(R.id.main_night_run_btn);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SearchActivity.class);
        switch(v.getId()){
            case R.id.main_back_btn:
                //返回登录
                startIntent(LoginActivity.class);
                break;
            case R.id.main_add_btn:
                //添加数据
                startIntent(AddActivity.class);
                break;
            case R.id.main_morning_btn:
                //晨跑
                intent.putExtra("name","晨跑");
                break;
            case R.id.main_morning_exercise_btn:
                //早操晨练
                intent.putExtra("name","早操晨练");
                break;
            case R.id.main_walk_btn:
                //日间行走
                intent.putExtra("name","日间行走");
                break;
            case R.id.main_bike_btn:
                //骑行
                intent.putExtra("name","骑行");
                break;
            case R.id.main_swim_btn:
                //游泳
                intent.putExtra("name","游泳");
                break;
            case R.id.main_ball_btn:
                //球类运动
                //startIntent(SearchActivity.class);
                intent.putExtra("name","球类运动");
                break;
            case R.id.main_night_run_btn:
                //夜间跑步
                //传递参数
                intent.putExtra("name","夜间跑步");
                break;
        }
        if(v.getId()!=R.id.main_add_btn&&v.getId()!=R.id.main_back_btn){
            //不是录入按钮就进行跳转
            startActivity(intent);
        }
    }
}
