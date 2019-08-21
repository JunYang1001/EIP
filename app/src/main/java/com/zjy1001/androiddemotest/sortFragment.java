package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjy1001.androiddemotest.bean.Activity;
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
import butterknife.OnClick;

/**
 * Created by JunYang on 2018/2/19.
 */

public class sortFragment extends android.support.v4.app.Fragment implements HttpCallBack {
//    @BindView( R.id.spinner_sort )
//    Spinner spinner;
    @BindView( R.id.sort_edittext )
    EditText sorted;
    @BindView( R.id.sort_list )
    ListView lv;
    @BindView( R.id.button2 )
    Button bt;

    List<Activity> list;

    Map<String,String> map;
    String str;

    private String[] from={"images","names","times","places","numbers"};
    private int[] to={R.id.tuijianimage_view,R.id.tuijianActivity_name,R.id.tuijianActivity_time,R.id.tuijianActivity_place,R.id.tuijianActivity_number};

   private String path="http://10.0.2.2:8080/EIP/MyActivityController/allactivity.action";
     //private String path="http://192.168.43.216:8080/EIP/MyActivityController/allactivity.action";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment=inflater.inflate( R.layout.activity_sort,container,false );
        ButterKnife.bind(this,fragment);
        //从登录获取userid
        Bundle bundle = getActivity().getIntent().getExtras();
        str = bundle.getString("id");
        init();
       // String acttype=spinner.getSelectedItem().toString();


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

public void init(){
    map =new  HashMap<String, String>();
    //区分请求来源，加入标杆


    map.put("flag", "getallactivityData");
    //将map类型转成固定格式的字符串
    String string= Util.getPair(map);
    HttpTask task=new HttpTask(this.getActivity());
    //设置数据回调
    task.setCallback(sortFragment.this);
    //开始执行请求
    task.execute(path,string);

}


    @OnClick({R.id.button2})
    public void  myclick(View view){
        if(view.getId()==R.id.button2){
           String actname=sorted.getText().toString();

           Map<String, String> map = new HashMap<String, String>();
            map.put( "actname", actname );
            map.put( "flag", "getinputData" );
            String string = Util.getPair( map );

            HttpTask task = new HttpTask( this.getActivity() );

            task.setCallback( (HttpCallBack) sortFragment.this );

            task.execute( path, string );
        }
    }


    @Override
    public void callback(String respone) {
        if(respone!=null){
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);
            if(part1.equals("objectallactivity")){
                Gson gson=new Gson();
                Type type=new TypeToken<List<Activity>>(){}.getType();
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
                    item.put( "names",list.get( i ).getAct_name() );
                    item.put( "times",list.get( i ).getAct_time());
                    item.put( "places",list.get( i ).getAct_place());
                    item.put( "numbers",list.get( i ).getAct_number() );
                    data.add( item );
                }
                SimpleAdapter adapter=new SimpleAdapter( getActivity(),data,R.layout.tuijian_item,from,to );
                lv.setAdapter( adapter );
                lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent aitent=new Intent(  );
                        aitent = aitent.setClass( getActivity(), tuijianActivity_item.class );
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("activity",list.get( position ));
                        aitent.putExtras(bundle);
                        aitent.putExtra( "joinuserid",str );
                        startActivity( aitent );
                    }
                } );
            }
            if (part1.equals("objectinputactivity")){
                Gson gson=new Gson();
                Type type=new TypeToken<List<Activity>>(){}.getType();
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

                  //  tetxt.setText( list.get( i ).getAct_name() );
                    item.put( "names",list.get( i ).getAct_name() );
                    item.put( "times",list.get( i ).getAct_time());
                    item.put( "places",list.get( i ).getAct_place());
                    item.put( "numbers",list.get( i ).getAct_number() );
                    data.add( item );
                }
                SimpleAdapter adapter=new SimpleAdapter( getActivity(),data,R.layout.tuijian_item,from,to );
                lv.setAdapter( adapter );
                lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent aitent=new Intent(  );
                        aitent = aitent.setClass( getActivity(), tuijianActivity_item.class );
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("activity",list.get( position ));
                        aitent.putExtras(bundle);
                        aitent.putExtra( "joinuserid",str );
                        startActivity( aitent );
                    }
                } );
            }
//            if (part1.equals("objecttypesactivity")){
//                Gson gson=new Gson();
//                Type type=new TypeToken<List<Activity>>(){}.getType();
//                list=gson.fromJson(part2, type);
//                ArrayList<HashMap<String,Object>> data=new   ArrayList<HashMap<String,Object>>();
//                for (int i=0;i<list.size();i++){
//                    HashMap<String,Object> item=new HashMap<String,Object>();
//                    switch (list.get( i ).getAct_type()){
//                        case "运动":
//                            item.put( "images",R.drawable.yundong );
//                            break;
//                        case "娱乐":
//                            item.put( "images",R.drawable.yule );
//                            break;
//                        case "亲子":
//                            item.put( "images",R.drawable.family );
//                            break;
//                        case "创业":
//                            item.put( "images",R.drawable.chuangye );
//                            break;
//                        case "公益":
//                            item.put( "images",R.drawable.love );
//                            break;
//                        case "音乐":
//                            item.put( "images",R.drawable.music );
//                            break;
//                        case "科技":
//                            item.put( "images",R.drawable.keji );
//                            break;
//                        case "游戏":
//                            item.put( "images",R.drawable.game );
//                            break;
//                        case "互联网":
//                            item.put( "images",R.drawable.intent );
//                            break;
//                        default:
//                            break;
//
//                    }
//                    item.put( "names",list.get( i ).getAct_name() );
//                    item.put( "times",list.get( i ).getAct_time());
//                    item.put( "places",list.get( i ).getAct_place());
//                    item.put( "numbers",list.get( i ).getAct_number() );
//                    data.add( item );
//                }
//                SimpleAdapter adapter=new SimpleAdapter( getActivity(),data,R.layout.tuijian_item,from,to );
//                lv.setAdapter( adapter );
//                lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent aitent=new Intent(  );
//                        aitent = aitent.setClass( getActivity(), tuijianActivity_item.class );
//                        Bundle bundle=new Bundle();
//                        bundle.putSerializable("activity",list.get( position ));
//                        aitent.putExtras(bundle);
//                        startActivity( aitent );
//                    }
//                } );
//            }
        }
    }
}
