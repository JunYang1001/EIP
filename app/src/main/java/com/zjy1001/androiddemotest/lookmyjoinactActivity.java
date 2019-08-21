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
import com.zjy1001.androiddemotest.bean.JoActivity;
import com.zjy1001.androiddemotest.bean.Joinactivity;
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

public class lookmyjoinactActivity extends AppCompatActivity  implements HttpCallBack{
    @BindView(R.id.main_toolbarss)
    Toolbar ptoolbar;
    @BindView( R.id.toolbar_titless )
    TextView tv1;
    @BindView( R.id.myjoinact_list )
    ListView lv;
    List<JoActivity> list;
    String userid;
   private String path="http://10.0.2.2:8080/EIP/MyJoinActController/lookmyjoinact.action";
    //private String path="http://192.168.43.216:8080/EIP/MyJoinActController/lookmyjoinact.action";
    private String[] from={"images","names","times","places","numbers"};
    private int[] to={R.id.tuijianimage_view,R.id.tuijianActivity_name,R.id.tuijianActivity_time,R.id.tuijianActivity_place,R.id.tuijianActivity_number};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lookmyjoinact );
        ButterKnife.bind(this);
        final Intent intent=getIntent();
        userid=intent.getStringExtra( "userid" );//登录者id
        ptoolbar.setTitle("我参加的活动");

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
        map.put( "flag","lookmyjoinact" );
        map.put( "userid",userid );
        //将map类型转成固定格式的字符串
        String string= Util.getPair(map);
        HttpTask task=new HttpTask(this);
        //设置数据回调
        task.setCallback(lookmyjoinactActivity.this);
        //开始执行请求
        task.execute(path,string);
    }

    @Override
    public void callback(String respone) {
        if(respone!=null) {
            String part1 = respone.substring( 0, respone.indexOf( "?" ) );
            String part2 = respone.substring( respone.indexOf( "?" ) + 1 );
            if(part1.equals("objectlookmyjoinact")){
                Gson gson=new Gson();
                Type type=new TypeToken<List<JoActivity>>(){}.getType();
                list=gson.fromJson(part2, type);

                ArrayList<HashMap<String,Object>> data=new   ArrayList<HashMap<String,Object>>();
                for (int i=0;i<list.size();i++){
                    HashMap<String,Object> item=new HashMap<String,Object>();
                    switch (list.get( i ).getAct_type()){
                        case "运动":
                            item.put( "images",R.drawable.yundong );
                            break;
                        case "娱乐":
                            item.put( "images",R.drawable.yule );
                            break;
                        case "亲子":
                            item.put( "images",R.drawable.family );
                            break;
                        case "创业":
                            item.put( "images",R.drawable.chuangye );
                            break;
                        case "公益":
                            item.put( "images",R.drawable.love );
                            break;
                        case "音乐":
                            item.put( "images",R.drawable.music );
                            break;
                        case "科技":
                            item.put( "images",R.drawable.keji );
                            break;
                        case "游戏":
                            item.put( "images",R.drawable.game );
                            break;
                        case "互联网":
                            item.put( "images",R.drawable.intent );
                            break;

                        default:
                            break;

                    }

                    //item.put( "images",images[i] );
                    item.put( "names",list.get( i ).getAct_name() );
                    item.put( "times",list.get( i ).getAct_time());
                    item.put( "places",list.get( i ).getAct_place());
                    item.put( "numbers",list.get( i ).getAct_number() );
                    data.add( item );
                }


                SimpleAdapter adapter=new SimpleAdapter( this,data,R.layout.tuijian_item,from,to );
                lv.setAdapter( adapter );
                lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent aitent=new Intent(  );
                        aitent = aitent.setClass( lookmyjoinactActivity.this, lookmyjoinactActivity_item.class );
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("activity",list.get( position ));
                        aitent.putExtras(bundle);
                        aitent.putExtra( "userid",userid );
                        startActivity( aitent );
                        finish();
                    }
                } );
            }

        }
    }
}
