package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class article_add extends AppCompatActivity  implements HttpCallBack {
    @BindView(R.id.main_toolbar)
    Toolbar ptoolbar;
    @BindView( R.id.editarticle1)
    EditText articleyname;
    @BindView( R.id.editarticle2)
    EditText articletext;
    @BindView( R.id.addarticle)
    Button addarticle;
    String userid;
    private Calendar aCalendar;
    private int year;
    private int mouthOfYear;
    private int dayOfMonth;
    private int hour;
    private int minute;
    protected long t;
    private String date;
    private String path="http://10.0.2.2:8080/EIP/MyArticleController/addarticle.action";
    //private String path="http://192.168.43.216:8080/EIP/MyArticleController/addarticle.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_article_add );
        ButterKnife.bind( article_add.this );
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
    private void init(){
        aCalendar.setTime(new Date());
        year=aCalendar.get(Calendar.YEAR);
        mouthOfYear=aCalendar.get(Calendar.MONTH);
        dayOfMonth=aCalendar.get(Calendar.DAY_OF_MONTH);
        hour=aCalendar.get(Calendar.HOUR_OF_DAY);
        minute=aCalendar.get(Calendar.MINUTE);
        t=aCalendar.getTimeInMillis();
        date=year+"年"+(mouthOfYear+1)+"月"+dayOfMonth+"日"+hour+"时"+minute+"分";


    }

    @OnClick({R.id.addarticle})
    public  void myclick(View view){
            switch (view.getId()){
                case R.id.addarticle:
                    aCalendar = Calendar.getInstance( TimeZone.getTimeZone("GMT+8"));
                    init();
                    String time=date;
                    String artname=articleyname.getText().toString();
                    String arttext=articletext.getText().toString();
                    Map<String, String> map=new HashMap<String, String>();
                    map.put( "id",userid );
                    map.put( "time",time );
                    map.put( "artname", artname);
                    map.put( "arttext",arttext );
                    map.put( "flag","getaddarticle" );
                    String string1= Util.getPair( map );
                    HttpTask task=new HttpTask( article_add.this );
                    task.setCallback( article_add.this );
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
                articleyname.setText( "" );
                articletext.setText( "" );


            }  if(part1.equals("nokcre")){

                Toast.makeText(this, part2, Toast.LENGTH_LONG).show();
            }

        }
    }
}
