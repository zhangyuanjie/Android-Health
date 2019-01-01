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
                //���ص�¼
                startIntent(LoginActivity.class);
                break;
            case R.id.main_add_btn:
                //�������
                startIntent(AddActivity.class);
                break;
            case R.id.main_morning_btn:
                //����
                intent.putExtra("name","����");
                break;
            case R.id.main_morning_exercise_btn:
                //��ٳ���
                intent.putExtra("name","��ٳ���");
                break;
            case R.id.main_walk_btn:
                //�ռ�����
                intent.putExtra("name","�ռ�����");
                break;
            case R.id.main_bike_btn:
                //����
                intent.putExtra("name","����");
                break;
            case R.id.main_swim_btn:
                //��Ӿ
                intent.putExtra("name","��Ӿ");
                break;
            case R.id.main_ball_btn:
                //�����˶�
                //startIntent(SearchActivity.class);
                intent.putExtra("name","�����˶�");
                break;
            case R.id.main_night_run_btn:
                //ҹ���ܲ�
                //���ݲ���
                intent.putExtra("name","ҹ���ܲ�");
                break;
        }
        if(v.getId()!=R.id.main_add_btn&&v.getId()!=R.id.main_back_btn){
            //����¼�밴ť�ͽ�����ת
            startActivity(intent);
        }
    }
}
