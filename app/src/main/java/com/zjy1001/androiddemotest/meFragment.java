package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjy1001.androiddemotest.bean.Article;
import com.zjy1001.androiddemotest.bean.User;
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

public class meFragment extends android.support.v4.app.Fragment  implements HttpCallBack {

  @BindView( R.id.userxinxilv )
    ListView lv;
    @BindView( R.id.button3 )
    Button meself_activity;
    @BindView( R.id.button5 )
    Button mejoin_activity;
    @BindView( R.id.button6 )
    Button meself_artcle;
    @BindView( R.id.button7 )
    Button eipother;
    @BindView( R.id.button8 )
    Button mejoin_article;
     String str;
    List<User> list;
    private String path="http://10.0.2.2:8080/EIP/myUserController/getheadandname.action";
  // private String path="http://192.168.43.216:8080/EIP/myUserController/getheadandname.action";
    private String[] from={"image","name"};
    private int[] to={R.id.imageView3,R.id.textView31};
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_me, container,false);
        ButterKnife.bind(this,fragment);
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
            map.put("flag", "getheadandname");
        map.put( "userid",str );

        //将map类型转成固定格式的字符串
        String string= Util.getPair(map);
        HttpTask task=new HttpTask(this.getActivity());
        //设置数据回调
        task.setCallback(meFragment.this);
        //开始执行请求
        task.execute(path,string);

    }

    @OnClick({R.id.button3,R.id.button5,R.id.button6,R.id.button8,R.id.button7})
    public  void  myclick(View v){
        switch (v.getId())
        {
            case R.id.button3:
                Intent intent1=new Intent(  getActivity(),lookmyactActivity.class );
                intent1.putExtra( "userid",str );
                startActivity( intent1 );
                break;
            case R.id.button5:
                Intent intent2=new Intent(  getActivity(),lookmyjoinactActivity.class );
                intent2.putExtra( "userid",str );
                startActivity( intent2 );
                break;
            case R.id.button6:
                Intent intent3=new Intent(  getActivity(),lookmyartActivity.class );
                intent3.putExtra( "userid",str );
                startActivity( intent3 );
                break;
            case R.id.button7://other
                Intent intent4=new Intent(  getActivity(),otheractivity.class );
                intent4.putExtra( "userid",str );
                startActivity( intent4 );
                break;
            case R.id.button8:
                Intent intent5=new Intent(  getActivity(),lookmyjoinartActivity.class );
                intent5.putExtra( "userid",str );
                startActivity( intent5 );
                break;
                default:
                    break;

        }
    }

    @Override
    public void callback(String respone) {
        if (respone != null){
            String part1 = respone.substring( 0, respone.indexOf( "?" ) );
            String part2 = respone.substring( respone.indexOf( "?" ) + 1 );
            if (part1.equals( "objectuserdata" )){
                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>() {}.getType();
                list = gson.fromJson( part2, type );
                ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < list.size(); i++) {
                    HashMap<String, Object> item = new HashMap<String, Object>();
                    switch (list.get( i ).getUser_sex() ){
                        case "男":
                            item.put( "image", R.drawable.headman );
                            break;
                        case "女":
                            item.put( "image", R.drawable.headwoman );
                            break;
                            default:
                                break;
                    }
                    item.put( "name", list.get( i ).getUser_name() );
                    data.add( item );
                }
                SimpleAdapter adapter = new SimpleAdapter( getActivity(), data, R.layout.activity_mexinxi_item, from, to );
                lv.setAdapter( adapter );
                lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent aitent = new Intent();
                        aitent = aitent.setClass( getActivity(), activity_medata.class );
                        Bundle bundle = new Bundle();
                        bundle.putSerializable( "userdata", list.get( position ) );
                        aitent.putExtras( bundle );
                        aitent.putExtra( "userid",str );
                        startActivity( aitent );
                    }
                } );
            }
        }
    }
}
