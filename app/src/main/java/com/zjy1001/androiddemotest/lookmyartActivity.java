package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjy1001.androiddemotest.bean.Activity;
import com.zjy1001.androiddemotest.bean.Article;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class lookmyartActivity extends AppCompatActivity implements HttpCallBack{
    @BindView(R.id.main_toolbarss)
    Toolbar ptoolbar;
    @BindView( R.id.toolbar_titless )
    TextView tv1;
    @BindView( R.id.myart_list )
    ListView lv;
    List<Article> list;
    String userid;

    private String[] from={"names","aturs","times","texts"};
    private int[] to={R.id.artname,R.id.artcreateid,R.id.arttime,R.id.arttext};
    private String path="http://10.0.2.2:8080/EIP/MyArticleController/lookmyart.action";
  //  private String path="http://192.168.43.216:8080/EIP/MyArticleController/lookmyart.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lookmyart );
        ButterKnife.bind(this);
        final Intent intent=getIntent();
        userid=intent.getStringExtra( "userid" );//登录者id
        ptoolbar.setTitle("我的文章");

        setSupportActionBar(ptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Map<String,String> map=new HashMap<String, String>();
        //区分请求来源，加入标杆
        map.put( "flag","lookmyart" );
        map.put( "userid",userid );
        //将map类型转成固定格式的字符串
        String string= Util.getPair(map);
        HttpTask task=new HttpTask(this);
        //设置数据回调
        task.setCallback(lookmyartActivity.this);
        //开始执行请求
        task.execute(path,string);
    }

    @Override
    public void callback(String respone) {
        if(respone!=null){
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);
            if(part1.equals("objectlookmyart")){
                //将jsonarray类型的数据转化成集合
                Gson gson = new Gson();
                Type type = new TypeToken<List<Article>>() {
                }.getType();
                list = gson.fromJson( part2, type );
                ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < list.size(); i++) {
                    HashMap<String, Object> item = new HashMap<String, Object>();
                    item.put( "names", list.get( i ).getArticle_name() );
                    item.put( "aturs", list.get( i ).getUser_name() );
                    item.put( "times", list.get( i ).getArticle_time() );
                    item.put( "texts", list.get( i ).getArticle_detail() );
                    data.add( item );
                }
                SimpleAdapter adapter = new SimpleAdapter( this, data, R.layout.article_item, from, to );
                lv.setAdapter( adapter );
                lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent aitent = new Intent();
                        aitent = aitent.setClass( lookmyartActivity.this, lookmyartActivity_item.class );
                        Bundle bundle = new Bundle();
                        bundle.putSerializable( "article", list.get( position ) );
                        aitent.putExtras( bundle );
                        aitent.putExtra( "userid",userid );
                        startActivity( aitent );
                        finish();
                    }
                } );
            }
        }
    }
}
