package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class updatepwdactivity extends AppCompatActivity  implements HttpCallBack{
    @BindView(R.id.main_toolbarss)
    Toolbar ptoolbar;
    @BindView( R.id.toolbar_titless )
    TextView tv1;
    @BindView( R.id.olduserpwd )
    EditText olduserpwd;
    @BindView( R.id.userpassword )
    EditText userpwd;
    @BindView( R.id.userpassword2 )
    EditText userpwd2;
    @BindView( R.id.confbtn )
    Button confbtn;

    String userid;
        private String path="http://10.0.2.2:8080/EIP/myUserController/updpwd.action";
       // private String path="http://192.168.43.216:8080/EIP/myUserController/updpwd.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_updatepwdactivity );
        ButterKnife.bind(this);
        final Intent intent=getIntent();
        userid=intent.getStringExtra( "userid" );//登录者id
        ptoolbar.setTitle("修改密码");

        setSupportActionBar(ptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
@OnClick({R.id.confbtn})
public void myclick(View view){
        switch (view.getId()){
            case R.id.confbtn:
                String oldpassword=olduserpwd.getText().toString();
                String password=userpwd.getText().toString();
                String apwds=userpwd2.getText().toString();
                Map<String, String> map=new HashMap<String, String>();
                map.put( "userid",userid );
                map.put("oldpassword", oldpassword);
                map.put("password", password);
                map.put("apassword", apwds);
                map.put("flag", "updpwdData");
                String string1= Util.getPair(map);

                HttpTask task=new HttpTask(updatepwdactivity.this);

                task.setCallback(updatepwdactivity.this);

                task.execute(path,string1);
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
            if(part1.equals("ookerror")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }
            if(part1.equals("nokupd")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }



            if(part1.equals("okupd")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
                Intent aIntent=new Intent(updatepwdactivity.this,LoginActivity.class);
                startActivity(aIntent);
                finish();

            }

        }
    }
}
