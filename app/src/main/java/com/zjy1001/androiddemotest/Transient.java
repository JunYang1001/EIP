package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Transient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transient);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Transient.this, LoginActivity.class); //跳转登入页
                startActivity(intent);
                finish();
            }
        }, 2000); //延时两秒跳转到主页
    }
}
