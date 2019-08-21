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

public class lookmyartActivity_item extends AppCompatActivity implements HttpCallBack {
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
    @BindView( R.id.deleteart )
    Button deleteart;
    String userid;
    private String path="http://10.0.2.2:8080/EIP/MyArticleController/deletemyart.action";
    //private String path="http://192.168.43.216:8080/EIP/MyArticleController/deletemyart.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lookmyart_item );
        ButterKnife.bind(lookmyartActivity_item.this);
        final Intent intent=getIntent();
        userid =intent.getStringExtra( "userid" );
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

    @OnClick({R.id.deleteart})
    public void  myclick(View view){
        switch (view.getId()){
            case R.id.deleteart:
                Article article=(Article)this.getIntent().getExtras( ).get("article");
                int cid=article.getArt_id();
                Map<String, String> map=new HashMap<String, String>();
                map.put( "artid",cid+" " );
                map.put("flag", "getdeleteart");
                String string1= Util.getPair(map);

                HttpTask htask=new HttpTask(lookmyartActivity_item.this);

                htask.setCallback(lookmyartActivity_item.this);

                htask.execute(path,string1);
                break;
            default:
                break;

        }
    }

    @Override
    public void callback(String respone) {
        if(respone!=null){
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);
            if (part1.equals("okdel")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent( lookmyartActivity_item.this,lookmyartActivity.class );
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
