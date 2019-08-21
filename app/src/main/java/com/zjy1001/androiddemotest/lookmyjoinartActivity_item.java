package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zjy1001.androiddemotest.bean.Article;
import com.zjy1001.androiddemotest.bean.JoActivity;
import com.zjy1001.androiddemotest.bean.Joarticle;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class lookmyjoinartActivity_item extends AppCompatActivity implements HttpCallBack{
    @BindView( R.id.main_toolbars )
    Toolbar ptoolbar;
    @BindView( R.id.textView24)
    TextView artname;
    @BindView( R.id.textView25)
    TextView arttime;
    @BindView( R.id.textView26)
    TextView artcreate;
    @BindView( R.id.textView27)
    TextView arttext;
    @BindView( R.id.deletejoinart )
    Button deletejoinart;
    String userid;
    private String path="http://10.0.2.2:8080/EIP/MyJoinArtController/deletejoinmyart.action";
   // private String path="http://192.168.43.216:8080/EIP/MyJoinArtController/deletejoinmyart.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lookmyjoinart_item );
        ButterKnife.bind(lookmyjoinartActivity_item.this);
        final Intent intent=getIntent();
        userid=intent.getStringExtra( "userid" );//登录者id
        ptoolbar.setTitle("社区文章");
        setSupportActionBar(ptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Joarticle article=(Joarticle)this.getIntent().getExtras( ).get("article");
        //int artid=article.getC_id();//得到文章id

        artname.setText( article.getArticle_name() );
        arttime.setText( article.getArticle_time() );
        artcreate.setText( article.getUser_name() );
        arttext.setText( article.getArticle_detail() );
    }
    @OnClick({R.id.deletejoinart})
    public void  myclick(View view){
        switch (view.getId()){
            case R.id.deletejoinart:
                // Activity activity=(Activity)this.getIntent().getExtras( ).get("activity");
                Joarticle article=(Joarticle)this.getIntent().getExtras( ).get("article");
                int jid=article.getC_id();
                Map<String, String> map=new HashMap<String, String>();
                map.put( "joinid",jid+" " );
                map.put("flag", "getdeletejoinart");
                String string1= Util.getPair(map);

                HttpTask htask=new HttpTask(lookmyjoinartActivity_item.this);

                htask.setCallback(lookmyjoinartActivity_item.this);

                htask.execute(path,string1);
        }
    }

    @Override
    public void callback(String respone) {
        if(respone!=null){
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);
            if (part1.equals("okdel")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent( lookmyjoinartActivity_item.this,lookmyjoinartActivity.class );
                intent.putExtra( "userid",userid );
                startActivity( intent );
                finish();
            }
            if (part1.equals("nokdel")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
