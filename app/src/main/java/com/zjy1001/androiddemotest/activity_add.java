package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class activity_add extends AppCompatActivity  implements HttpCallBack{
    @BindView(R.id.main_toolbar)
    Toolbar ptoolbar;
    @BindView( R.id.editText2)
    EditText activityname;
    @BindView( R.id.editText4)
    EditText activitytime;
    @BindView( R.id.editText5)
    EditText activityplace;
    @BindView( R.id.editText7)
    EditText activitynumber;
    @BindView( R.id.typespinner )
    Spinner typespinner;
    @BindView( R.id.editText8)
    EditText activityjianjie;
    @BindView( R.id.addactivity )
    Button addactivity;

    String userid;

    private String path="http://10.0.2.2:8080/EIP/MyActivityController/addactivity.action";
    //private String path="http://192.168.43.216:8080/EIP/MyActivityController/addactivity.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add );
        ButterKnife.bind( activity_add.this );
        Intent intent=getIntent();
        userid=intent.getStringExtra( "id" );
        setSupportActionBar(ptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.addactivity})
    public  void  myclick(View v){
          switch (v.getId()){
              case R.id.addactivity:
                String actname=activityname.getText().toString();
                String acttime=activitytime.getText().toString();
                String actplace=activityplace.getText().toString();
                String actnumber=activitynumber.getText().toString();
                String acttype=typespinner.getSelectedItem().toString();
                String actdetail=activityjianjie.getText().toString();

                  Map<String, String> map=new HashMap<String, String>();
                  map.put( "id",userid );
                  map.put( "actname" ,actname);
                  map.put( "acttime",acttime );
                  map.put( "actplace",actplace );
                  map.put( "actnumber",actnumber );
                  map.put( "acttype",acttype );
                  map.put( "actdetail",actdetail );

                  map.put( "flag","getregactivity" );
                  String string1= Util.getPair( map );
                  HttpTask task=new HttpTask( activity_add.this );
                  task.setCallback( activity_add.this );
                  task.execute( path,string1 );



          }
    }

    @Override
    public void callback(String respone) {
        if(respone!=null){
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);
            if(part1.equals("okcre")){
                Toast.makeText(this, part2, Toast.LENGTH_LONG).show();
                activityname.setText( "" );
                activitytime.setText( "" );
                activityplace.setText( "" );
                activitynumber.setText( "" );
                typespinner.setSelection( 1 );
                activityjianjie.setText( "" );

            }  if(part1.equals("nokcre")){

                Toast.makeText(this, part2, Toast.LENGTH_LONG).show();
            }

        }
    }
}
