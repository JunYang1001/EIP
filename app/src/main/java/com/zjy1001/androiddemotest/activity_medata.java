package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjy1001.androiddemotest.bean.Activity;
import com.zjy1001.androiddemotest.bean.User;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class activity_medata extends AppCompatActivity  implements HttpCallBack{
   @BindView( R.id.textView42 )
   TextView tvuserid;
   @BindView( R.id.editText6 )
   EditText ednickname;
    @BindView( R.id.spinner2 )
    Spinner spsex;
    @BindView( R.id.editText10 )
     EditText edage;
    @BindView( R.id.editText11 )
    EditText edemail;
    @BindView( R.id.editText12 )
    EditText  edcity;
    @BindView( R.id.editText13 )
    EditText edphone;
    @BindView( R.id.xiugaibt )
    Button  xiugaibt;
    @BindView( R.id.main_toolbars )
    Toolbar ptoolbar;

    String userid;
   private String path="http://10.0.2.2:8080/EIP/myUserController/updateuserdata.action";
    //private String path="http://192.168.43.216:8080/EIP/myUserController/updateuserdata.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_medata );
        ButterKnife.bind(this);
        final Intent intent=getIntent();
        ptoolbar.setTitle("个人资料");
        setSupportActionBar(ptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        User user=(User)this.getIntent().getExtras( ).get("userdata");
        userid =intent.getStringExtra( "userid" );
        tvuserid.setText( userid );
        ednickname.setText(user.getUser_name()  );
        if (user.getUser_sex().equals( "男" )){
            spsex.setSelection( 0 );
        } else if (user.getUser_sex().equals( "女" )){
            spsex.setSelection( 1 );
        }
        edage.setText( user.getUser_age()+"");
        edemail.setText( user.getUser_email() );
        edcity.setText( user.getUser_city() );
        edphone.setText( user.getUser_phone()+"" );

    }

    @OnClick({R.id.xiugaibt})
    public void myclick(View view){
             switch (view.getId()){
                 case R.id.xiugaibt:
                     init();
             }
    }

    public  void init(){
        String username=ednickname.getText().toString();
        String usersex=spsex.getSelectedItem().toString();
        String userage=edage.getText().toString();
        String useremail=edemail.getText().toString();
        String usercity=edcity.getText().toString();
        String userphone=edphone.getText().toString();
        Map<String,String> map=new HashMap<String, String>();
        //区分请求来源，加入标杆

        map.put( "userid",userid );
        map.put( "username",username );
        map.put( "usersex",usersex );
        map.put( "userage",userage );
        map.put( "useremail",useremail );
        map.put( "usercity",usercity );
        map.put( "userphone",userphone );
        map.put("flag", "getuserxinxilData");
        //将map类型转成固定格式的字符串
        String string= Util.getPair(map);
        HttpTask task=new HttpTask(activity_medata.this);
        //设置数据回调
        task.setCallback(activity_medata.this);
        //开始执行请求
        task.execute(path,string);

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



            if(part1.equals("okup")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();


            }
            if(part1.equals("nokup")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();


            }

        }
    }
}
