package com.zjy1001.androiddemotest;

/**
 * Created by JunYang on 2018/2/19.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zjy1001.androiddemotest.bean.Activity;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;


import butterknife.BindView;
import butterknife.ButterKnife;


public class homeFragment extends android.support.v4.app.Fragment  implements HttpCallBack{
    @BindView(R.id.fc_gv)
    GridView gridview;
    @BindView(R.id.roll_view_pager)
    RollPagerView mRollViewPager;
    @BindView( R.id.tj_list )
    ListView aListview;
    @BindView( R.id.citymap )
    TextView city;
    List<Activity> list;
    String str;
    String gpscity;

    private String path="http://10.0.2.2:8080/EIP/MyActivityController/limitactivity.action";
     //private String path="http://192.168.43.216:8080/EIP/MyActivityController/limitactivity.action";
    //定义图标数组
    private int[] imageRes = {
            R.drawable.newss,
            R.drawable.pop,
            R.drawable.nearby,

    };

   private String[] from={"images","names","times","places","numbers"};
    private int[] to={R.id.tuijianimage_view,R.id.tuijianActivity_name,R.id.tuijianActivity_time,R.id.tuijianActivity_place,R.id.tuijianActivity_number};



    //定义图标下方的名称数组
    private String[] name = {
            "最新",
            "热门",
            "附近",

    };
    public LocationClient mLocationClient;
    private BaiduMap baiduMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLocationClient = new LocationClient(getContext().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        View fragment = inflater.inflate(R.layout.activity_home, container,false);


        ButterKnife.bind(this,fragment);
        //定位权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else {
            requestLocation();
        }

       ;

        Map<String,String> map=new  HashMap<String, String>();
        //区分请求来源，加入标杆
        map.put("flag", "getactivitylimitData");

        //将map类型转成固定格式的字符串
        String string= Util.getPair(map);
        HttpTask task=new HttpTask(this.getActivity());
        //设置数据回调
        task.setCallback(homeFragment.this);
        //开始执行请求
        task.execute(path,string);





        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        mRollViewPager.setHintView(new ColorPointHintView(fragment.getContext(),Color.YELLOW,Color.WHITE));


        int length = imageRes.length;

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map2 = new HashMap<String, Object>();
            map2.put("ItemImage", imageRes[i]);//添加图像资源的ID
            map2.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map2);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(this.getActivity(),
                lstImageItem,//数据来源
                R.layout.home_item,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.img_item, R.id.txt_item});
        //添加并且显示
        gridview.setAdapter(saImageItems);
        //从登录获取userid
        Bundle bundle = getActivity().getIntent().getExtras();
        str = bundle.getString("id");
        //添加消息处理
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent();
                intent.setClass(getActivity(), newcamActivity.class);
                switch (position){
                    case 0:
                        intent.putExtra( "cam",name[position] );
                        intent.putExtra( "user",str );
                        startActivity( intent );
                        break;
                    case 1:
                        intent.putExtra( "cam",name[position] );
                        intent.putExtra( "user",str );
                        startActivity( intent );
                        break;
                    case 2:

                        intent.putExtra( "cam",name[position] );
                        intent.putExtra( "gpscity",gpscity );
                        intent.putExtra( "user",str );
                        startActivity( intent );
                        break;
                        default:
                            break;

                }

               // ToastUtil.showTextToast(getActivity(),name[position], Toast.LENGTH_SHORT);
            }
        });


        return fragment;


    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);

        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();

       // baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            //finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
                   // finish();
                }
                break;
            default:
        }
    }
    public class MyLocationListener implements BDLocationListener {


        public void onReceiveLocation(BDLocation location) {
            //StringBuilder currentPosition = new StringBuilder();
            //currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
            //currentPosition.append("经线：").append(location.getLongitude()).append("\n");
          // currentPosition.append("国家：").append(location.getCountry()).append("\n");
          //  currentPosition.append("地址：").append(location.getProvince()).append(" ");
            //currentPosition.append("").append(location.getCity()).append(" ");
            //currentPosition.append("区：").append(location.getDistrict()).append("\n");
            //currentPosition.append("街道：").append(location.getStreet()).append("\n");
           // currentPosition.append("定位方式：");
           // if (location.getLocType() == BDLocation.TypeGpsLocation) {
           //     currentPosition.append("GPS");
           // } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
             //   currentPosition.append("网络");
           // }
            //System.out.print( currentPosition );
            //地址：null null

            city.setText( "地址："+location.getProvince()+location.getCity());
                gpscity = location.getCity() ;


        }

    }


    @Override
    public void callback(String respone) {
        if(respone!=null){
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);
            if(part1.equals("objectactivitylimit")){

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

                    //item.put( "images",images[i] );
                    item.put( "names",list.get( i ).getAct_name() );
                    item.put( "times",list.get( i ).getAct_time());
                    item.put( "places",list.get( i ).getAct_place());
                    item.put( "numbers",list.get( i ).getAct_number() );
                    data.add( item );
                }


                final SimpleAdapter adapter=new SimpleAdapter( getActivity(),data,R.layout.tuijian_item,from,to );
                aListview.setAdapter( adapter );
                aListview.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
        }
    }


    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.banner01,
                R.drawable.banner02,
                R.drawable.banner03,
                R.drawable.banner04,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
