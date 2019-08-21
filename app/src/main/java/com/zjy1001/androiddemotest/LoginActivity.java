package com.zjy1001.androiddemotest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class LoginActivity extends AppCompatActivity  implements HttpCallBack {
    @BindView(R.id.login_btn)
    Button pButton;
    @BindView(R.id.login_reg)
    Button rButton;
    @BindView(R.id.login_ac)
    EditText ed1;
    @BindView(R.id.login_pass)
    EditText ed2;
    @BindView( R.id.forgetpwd )
    TextView forgetpwd;
    String username;
    private String path="http://10.0.2.2:8080/EIP/myUserController/checklogin.action";
    //private String path="http://192.168.2.122:8080/EIP/myUserController/checklogin.action";
    //private String path="http://192.168.43.216:8080/EIP/myUserController/checklogin.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        ButterKnife.bind(this);
    }
    @OnClick({R.id.login_btn,R.id.login_reg,R.id.forgetpwd})
    public void loginClick(View view)
    {
        if(view.getId()==R.id.login_btn) {
            username = ed1.getText().toString();
            String password = ed2.getText().toString();
            Map<String, String> map = new HashMap<String, String>();
            map.put( "username", username );
            map.put( "password", password );
            map.put( "flag", "getloginData" );
            String string = Util.getPair( map );
            HttpTask task = new HttpTask( LoginActivity.this );
            task.setCallback( (HttpCallBack) LoginActivity.this );
            task.execute( path, string );
        }
        if (view.getId() == R.id.login_reg) {
            Intent aIntent = new Intent( LoginActivity.this, RegisterActiviry.class );
            startActivity( aIntent );

        }
        if (view.getId() == R.id.forgetpwd) {
            Intent aIntent = new Intent( LoginActivity.this, forgetActivity.class );
            startActivity( aIntent );

        }

    }
    @Override
    public void callback(String respone) {
        if(respone!=null){
            // TODO 自动生成的方法存根
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);

            if(part1.equals("ok")){
                Toast.makeText(LoginActivity.this, "用户:"+username+","+"登陆成功", Toast.LENGTH_SHORT).show();

                Intent aIntent;
                aIntent = new Intent(LoginActivity.this,IndexActivity.class);
                //  SharedPreferences sharedPreferences = getSharedPreferences("zhanghao", Context.MODE_PRIVATE);
                aIntent.putExtra("id", username);

                startActivity(aIntent);
                this.finish();



            }
            if(part1.equals("nok")){

                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_LONG).show();
            }
        }
    }
}
