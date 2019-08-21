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
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class articleActivity_item extends AppCompatActivity  implements HttpCallBack{
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
   @BindView( R.id.joinart )
    Button  joinart;
    String userid;
    private String path="http://10.0.2.2:8080/EIP/MyJoinArtController/joinarticle.action";
    //private String path="http://192.168.43.216:8080/EIP/MyJoinArtController/joinarticle.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_article_item );
        ButterKnife.bind(articleActivity_item.this);
        final Intent intent=getIntent();
        userid =intent.getStringExtra( "joinuserid" );
        ptoolbar.setTitle("社区文章");
        setSupportActionBar(ptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Article article=(Article)this.getIntent().getExtras( ).get("article");
        //int artid=article.getC_id();//得到文章id

        artname.setText( article.getArticle_name() );
        arttime.setText( article.getArticle_time() );
        artcreate.setText( article.getUser_name() );
        arttext.setText( article.getArticle_detail() );
    }

    @OnClick({R.id.joinart})
     public  void myclick(View view){
        switch (view.getId()){
            case R.id.joinart:
                Article article=(Article)this.getIntent().getExtras( ).get("article");
                int artid=article.getArt_id();
                String artname=article.getArticle_name();
                Map<String, String> map=new HashMap<String, String>();
                map.put( "joinartid", userid);
                map.put( "artid",artid+" " );
                map.put( "artname",artname );
                map.put("flag", "getjoinartData");

                String string1= Util.getPair(map);

                HttpTask htask=new HttpTask(articleActivity_item.this);

                htask.setCallback(articleActivity_item.this);

                htask.execute(path,string1);
        }

    }

    @Override
    public void callback(String respone) {
        if(respone!=null){
            // TODO 自动生成的方法存根
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);

            if(part1.equals("nokadd")){
                Toast.makeText(this, part2, Toast.LENGTH_LONG).show();
            }

            if(part1.equals("okadd")){
                Toast.makeText(this, part2, Toast.LENGTH_LONG).show();


            }
            if(part1.equals("noklikeadd")){
                Toast.makeText(this, part2, Toast.LENGTH_LONG).show();


            }
            if(part1.equals("nokaadd")){
                Toast.makeText(this, part2, Toast.LENGTH_LONG).show();


            }
        }
    }
}
