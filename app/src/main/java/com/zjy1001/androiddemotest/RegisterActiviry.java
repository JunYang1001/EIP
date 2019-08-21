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

public class RegisterActiviry extends AppCompatActivity implements HttpCallBack {

    @BindView(R.id.userid)
    EditText ed1;
    @BindView(R.id.userpassword)
    EditText ed2;
    @BindView(R.id.userpassword2)
    EditText ed3;
    @BindView(R.id.uname)
    EditText ed4;
    @BindView(R.id.radio_man)
    RadioButton man;
    @BindView(R.id.radio_woman)
    RadioButton woman;
    @BindView(R.id.userage)
    EditText ed5;
    @BindView(R.id.userphone)
    EditText ed6;
    @BindView(R.id.useremail)
    EditText ed7;
    @BindView(R.id.usercity)
    EditText ed8;
    @BindView(R.id.regbtn)
    Button btreg;
    @BindView(R.id.back)
    Button btback;
    String uname;
   private String path="http://10.0.2.2:8080/EIP/myUserController/checkreg.action";
  // private String path="http://192.168.43.216:8080/EIP/myUserController/checkreg.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        ButterKnife.bind(this);
    }
    @OnClick({R.id.regbtn,R.id.back})
        public void onclick(View view){
        if(view.getId()==R.id.regbtn){
            String userid=ed1.getText().toString();
            String password=ed2.getText().toString();
            String apwds=ed3.getText().toString();
             uname=ed4.getText().toString();
            String sex="";
            if(man.isChecked()){
                sex= (String) man.getText();
            }else{
                sex=(String) woman.getText();
            }
            String age=ed5.getText().toString();
            String phone=ed6.getText().toString();
            String email=ed7.getText().toString();
            String city=ed8.getText().toString();

            Map<String, String> map=new HashMap<String, String>();
            map.put("userid", userid);
            map.put("uname", uname);
            map.put("password", password);
            map.put("apassword", apwds);
            map.put("sex", sex);
            map.put("age", age);
            map.put("phone", phone);
            map.put("email", email);
            map.put("city", city);

            map.put("flag", "getregData");

            String string1= Util.getPair(map);

            HttpTask task=new HttpTask(RegisterActiviry.this);

            task.setCallback(RegisterActiviry.this);

            task.execute(path,string1);


        }
        if(view.getId()==R.id.back){
            Intent aIntent=new Intent(RegisterActiviry.this,LoginActivity.class);
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

            if(part1.equals("okerror")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }

            if(part1.equals("nokrep")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }

            if(part1.equals("okreg")){
                Toast.makeText(this, uname+" "+part2, Toast.LENGTH_SHORT).show();
                Intent aIntent=new Intent(RegisterActiviry.this,LoginActivity.class);
                startActivity(aIntent);
                finish();

            }
            if(part1.equals("nokreg")){

                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
