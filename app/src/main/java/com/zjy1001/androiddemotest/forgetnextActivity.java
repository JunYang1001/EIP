package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class forgetnextActivity extends AppCompatActivity  implements HttpCallBack{
    @BindView(R.id.userpassword)
    EditText ed1;
    @BindView(R.id.userpassword2)
    EditText ed2;
    @BindView(R.id.confbtn)
    Button btconf;
    @BindView(R.id.back)
    Button btback;
    String userid;
    private String path="http://10.0.2.2:8080/EIP/myUserController/cofpwd.action";
     //private String path="http://192.168.43.216:8080/EIP/myUserController/cofpwd.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forgetnext );
        ButterKnife.bind(this);
        Intent intent=getIntent();
         userid=intent.getStringExtra( "userid" );
    }
    @OnClick({R.id.confbtn,R.id.back})
    public void onclick(View view){
        if(view.getId()==R.id.confbtn) {
            String password=ed1.getText().toString();
            String apwds=ed2.getText().toString();
            Map<String, String> map=new HashMap<String, String>();
            map.put( "userid",userid );
            map.put("password", password);
            map.put("apassword", apwds);
            map.put("flag", "cofpwdData");
            String string1= Util.getPair(map);

            HttpTask task=new HttpTask(forgetnextActivity.this);

            task.setCallback(forgetnextActivity.this);

            task.execute(path,string1);
        }
        if(view.getId()==R.id.back){
            Intent aIntent=new Intent(forgetnextActivity.this,forgetActivity.class);
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



            if(part1.equals("okcof")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
                Intent aIntent=new Intent(forgetnextActivity.this,LoginActivity.class);
                startActivity(aIntent);
                finish();

            }

        }
    }
}
