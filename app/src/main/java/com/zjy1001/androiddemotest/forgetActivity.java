package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class forgetActivity extends AppCompatActivity implements HttpCallBack {
    @BindView(R.id.userid)
    EditText ed1;
    @BindView(R.id.userphone)
    EditText ed2;
    @BindView(R.id.useremail)
    EditText ed3;
    @BindView(R.id.nextbtn)
    Button btnext;
    @BindView(R.id.back)
    Button btback;

           private String path="http://10.0.2.2:8080/EIP/myUserController/forget.action";
           //private String path="http://192.168.43.216:8080/EIP/myUserController/forget.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forget );
        ButterKnife.bind(this);
    }
    @OnClick({R.id.nextbtn,R.id.back})
    public void onclick(View view) {
        if(view.getId()==R.id.nextbtn){
            String userid=ed1.getText().toString();
            String phone=ed2.getText().toString();
            String email=ed3.getText().toString();
            Map<String, String> map=new HashMap<String, String>();
            map.put("userid", userid);
            map.put("phone", phone);
            map.put("email", email);
            map.put("flag", "forgetData");

            String string1= Util.getPair(map);

            HttpTask task=new HttpTask(forgetActivity.this);

            task.setCallback(forgetActivity.this);

            task.execute(path,string1);
        }
        if(view.getId()==R.id.back){
            Intent aIntent=new Intent(forgetActivity.this,LoginActivity.class);
            startActivity(aIntent);
            finish();
        }
    }

    @Override
    public void callback(String respone) {
        if(respone!=null){
            // TODO 自动生成的方法存根
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);

            if(part1.equals("uiderror")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }

            if(part1.equals("phoneerror")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }

            if(part1.equals("nextbutton")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
                Intent aIntent=new Intent(forgetActivity.this,forgetnextActivity.class);
                aIntent.putExtra( "userid",ed1.getText().toString() );
                startActivity(aIntent);


            }
            if(part1.equals("emailerror")){

                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
