package com.zjy1001.androiddemotest;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;



import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;



import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JunYang on 2018/2/19.
 */


public class IndexActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.main_toolbar)
    Toolbar ptoolbar;
    @BindView( R.id.toolbar_title )
    TextView tv1;
    @BindView(R.id.main_ib1)
    ImageButton bt1;
    @BindView(R.id.main_ib2)
    ImageButton bt2;
    @BindView(R.id.main_ib3)
    ImageButton bt3;
    @BindView(R.id.main_ib4)
    ImageButton bt4;
    @BindView(R.id.main_ib5)
    ImageButton bt5;

    @BindView(R.id.viewPager)
    ViewPager viewPager;


    boolean[] fragmentsUpdateFlag = { false, false, false, false };

    FragmentManager fragmentManager;
    List<Fragment> fragmentList = new ArrayList<>();

    private View currentButton; //表示当前被选中的按钮

    public LocationClient mLocationClient;

    private TextView positionText;

    private MapView mapView;

    private BaiduMap baiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册监听函数
        //mLocationClient.registerLocationListener(new MyLocationListener());
        //initLocation();
        setContentView(R.layout.activity_index);
        ButterKnife.bind(IndexActivity.this);

        //黄油刀启用



      ptoolbar.setTitle("EIPDemo");//标题

        //设置toolbar
        setSupportActionBar(ptoolbar);
        fragmentList.add(new homeFragment());//首页
        fragmentList.add(new sortFragment());//分类
        fragmentList.add( new releaseFragment() );//发布
        fragmentList.add(new findFragment());//发现
        fragmentList.add(new meFragment());//个人
        fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyFragmentPagerAdapter());
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        setButton(bt1);
    }









    public String getData(){
        //返回的是选择后,textview的值
        return "eip";
    }


        //menu菜单的显示,MainActivity.java重写 onCreateOptionsMenu(Menu menu)，接着加载菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return  true;
    }


    @OnClick({R.id.main_ib1, R.id.main_ib2, R.id.main_ib3, R.id.main_ib4,R.id.main_ib5})
    public void qiehuanonClick(View v) {

        switch (v.getId()) {
            case R.id.main_ib1:   //首页
                viewPager.setCurrentItem(0, false);
                setButton((ImageButton) v);
                break;

            case R.id.main_ib2:   //分类页面
                viewPager.setCurrentItem(1, false);
                setButton((ImageButton) v);
                break;
            case R.id.main_ib5:   //发布页面
                viewPager.setCurrentItem(2, false);
                setButton((ImageButton) v);
                break;

            case R.id.main_ib3:   //发现
                viewPager.setCurrentItem(3, false);
                setButton((ImageButton) v);
                break;
            case R.id.main_ib4:   //个人中心
                viewPager.setCurrentItem(4, false);
                setButton((ImageButton) v);
                break;
        }
    }
    /**
     *
     * @param v  指代当前被按下的按钮
     */
    private void setButton(View v){
        if(currentButton!=null&&currentButton.getId()!=v.getId()){
            //将之前的被选中的按钮设置为可以被点击
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);  //将当前被选中的按钮设置为不可点击
        currentButton=v;

    }

    /**
     * Tollbar menu的item事件
     */



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                setButton(bt1);
                break;
            case 1:
                setButton(bt2);
                break;
            case 2:
                setButton(bt5);
                break;

            case 3:
                setButton(bt3);
                break;
            case 4:
                setButton(bt4);
                break;
        }
        Log.i("onPageSelected1", "" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        MyFragmentPagerAdapter() {
            super(IndexActivity.this.fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return IndexActivity.this.fragmentList.get(position);
        }
        @Override

        public int getItemPosition(Object object) {

            return PagerAdapter.POSITION_NONE;

        }
        @Override
        public int getCount() {
            return IndexActivity.this.fragmentList.size();
        }
    }
}