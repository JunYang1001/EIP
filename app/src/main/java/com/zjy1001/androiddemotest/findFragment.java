package com.zjy1001.androiddemotest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjy1001.androiddemotest.bean.Activity;
import com.zjy1001.androiddemotest.bean.Article;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JunYang on 2018/2/19.
 */



public class findFragment extends android.support.v4.app.Fragment  implements HttpCallBack{
    //    @BindView( R.id.spinner_sort )
//    Spinner spinner;
    @BindView( R.id.articleinput )
    EditText arte;
    @BindView( R.id.art_list1 )
    ListView lv;
    @BindView( R.id.artinput )
    Button bt;
    @BindView( R.id.textView29 )
          TextView tettt;

    String str;
    List<Article> list;


    private String[] from={"names","aturs","times","texts"};
    private int[] to={R.id.artname,R.id.artcreateid,R.id.arttime,R.id.arttext};

   private String path="http://10.0.2.2:8080/EIP/MyArticleController/allarticle.action";
     //private String path="http://192.168.43.216:8080/EIP/MyArticleController/allarticle.action";

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View fragment=inflater.inflate( R.layout.activity_find,container,false );
        ButterKnife.bind( this,fragment );
        //从登录获取userid
        Bundle bundle = getActivity().getIntent().getExtras();
        str = bundle.getString("id");
        init();

        return fragment;
    }

    private boolean isGetData = false;
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
           init(); //   这里可以做网络请求或者需要的数据刷新操作
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
           init(); //   这里可以做网络请求或者需要的数据刷新操作
            isGetData = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    public  void init(){
       Map<String,String> map=new HashMap<String, String>();
       //区分请求来源，加入标杆
       map.put("flag", "getarticleallData");

       //将map类型转成固定格式的字符串
       String string= Util.getPair(map);
       HttpTask task=new HttpTask(this.getActivity());
       //设置数据回调
       task.setCallback(findFragment.this);
       //开始执行请求
       task.execute(path,string);

   }


    @OnClick({R.id.artinput})
    public void  myclick(View view){
        if(view.getId()==R.id.artinput){
            String artname=arte.getText().toString();
            Map<String, String> map = new HashMap<String, String>();
            map.put( "artname", artname);
            map.put( "flag", "getinputData" );
            String string = Util.getPair( map );
            HttpTask task = new HttpTask( this.getActivity() );
            task.setCallback( (HttpCallBack) findFragment.this );
            task.execute( path, string );
        }
    }

    @Override
    public void callback(String respone) {
        if (respone != null) {
            String part1 = respone.substring( 0, respone.indexOf( "?" ) );
            String part2 = respone.substring( respone.indexOf( "?" ) + 1 );
            if (part1.equals( "objectallarticle" )) {
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
                    SimpleAdapter adapter = new SimpleAdapter( getActivity(), data, R.layout.article_item, from, to );
                    lv.setAdapter( adapter );
                    lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent aitent = new Intent();
                            aitent = aitent.setClass( getActivity(), articleActivity_item.class );
                            Bundle bundle = new Bundle();
                            bundle.putSerializable( "article", list.get( position ) );
                            aitent.putExtras( bundle );
                            aitent.putExtra( "joinuserid",str );
                            startActivity( aitent );
                        }
                    } );
                }
            if (part1.equals( "objectinputarticle" )) {
                //将jsonarray类型的数据转化成集合
                Gson gson = new Gson();
                Type type = new TypeToken<List<Article>>() {}.getType();
                list = gson.fromJson( part2, type );
                //ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < list.size(); i++) {
                    HashMap<String, Object> item = new HashMap<String, Object>();
                    tettt.setText( list.get( i ).getArticle_name() );

                    item.put( "names", list.get( i ).getArticle_name() );
                    item.put( "aturs", list.get( i ).getUser_name() );
                    item.put( "times", list.get( i ).getArticle_time() );
                    item.put( "texts", list.get( i ).getArticle_detail() );
                    data.add( item );
                }
               // tettt=data;
                    SimpleAdapter adapter = new SimpleAdapter( getActivity(), data, R.layout.article_item, from, to );
                    lv.setAdapter( adapter );
                    lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent aitent = new Intent();
                            aitent = aitent.setClass( getActivity(), articleActivity_item.class );
                            Bundle bundle = new Bundle();
                            bundle.putSerializable( "article", list.get( position ) );
                            aitent.putExtras( bundle );
                            aitent.putExtra( "joinuserid",str );
                            startActivity( aitent );
                        }
                    } );



//                    lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Intent aitent = new Intent();
//                            aitent = aitent.setClass( getActivity(), articleActivity_item.class );
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable( "article", list.get( position ) );
//                            aitent.putExtras( bundle );
//                            aitent.putExtra( "joinuserid",str );
//                            startActivity( aitent );
//                        }
//                    } );

                }

            }

    }
}