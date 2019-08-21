package com.zjy1001.androiddemotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjy1001.androiddemotest.bean.Activity;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class tuijianActivity_item extends AppCompatActivity implements HttpCallBack {
  @BindView( R.id.main_toolbars )
    Toolbar ptoolbar;
  @BindView( R.id.actname )
    TextView actname;
  @BindView( R.id.textView3 )
    TextView acttime;
    @BindView( R.id.textView5)
    TextView actplace;
    @BindView( R.id.textView16 )
    TextView acttype;
    @BindView( R.id.textView10 )
    TextView actnumber;
    @BindView( R.id.textView12 )
    TextView actcreatname;
    @BindView( R.id.textView14 )
    TextView actdetail;
    @BindView( R.id.imageView )
    ImageView actimage;
    @BindView( R.id.joinact )
    Button btjoin;
    String userid;

    private String path="http://10.0.2.2:8080/EIP/MyJoinActController/joinactivity.action";
   //  private String path="http://192.168.43.216:8080/EIP/MyJoinActController/joinactivity.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tuijian_iteminfo );
        ButterKnife.bind(tuijianActivity_item.this);
        final Intent intent=getIntent();
        ptoolbar.setTitle("活动介绍");
        setSupportActionBar(ptoolbar);
        Activity activity=(Activity)this.getIntent().getExtras( ).get("activity");
        userid =intent.getStringExtra( "joinuserid" );
        switch (activity.getAct_type()){
            case "运动":
               actimage.setImageResource( R.drawable.yundong );
                break;
            case "娱乐":
                actimage.setImageResource( R.drawable.yule );
                break;
            case "亲子":
                actimage.setImageResource( R.drawable.family );
                break;
            case "创业":
                actimage.setImageResource( R.drawable.chuangye );
                break;
            case "公益":
                actimage.setImageResource( R.drawable.love );
                break;
            case "音乐":
                actimage.setImageResource( R.drawable.music );
                break;
            case "科技":
                actimage.setImageResource( R.drawable.keji );
                break;
            case "游戏":
                actimage.setImageResource( R.drawable.game );
                break;
            case "互联网":
                actimage.setImageResource( R.drawable.intent );
                break;
            default:
                break;
        }

        int actid=activity.getA_id();//得到活动id

        actname.setText( activity.getAct_name() );
        acttime.setText( activity.getAct_time() );
        actplace.setText( activity.getAct_place() );
        acttype.setText( activity.getAct_type() );
        actnumber.setText( activity.getAct_number()+"" );
        actcreatname.setText( activity.getAct_createname() );
        actdetail.setText( activity.getAct_detail() );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @OnClick({R.id.joinact})
    public void  myclick(View view){
        switch (view.getId()){
            case R.id.joinact:
                Activity activity=(Activity)this.getIntent().getExtras( ).get("activity");
                int aid=activity.getA_id();
                String actcreaterid=activity.getAct_createid();
                String actname=activity.getAct_name();
                Map<String, String> map=new HashMap<String, String>();
                map.put( "joinactid", userid);
                map.put( "actid",aid+" " );
                map.put( "actcreaterid",actcreaterid );
                map.put( "actname",actname );
                map.put("flag", "getjoinactData");
                String string1= Util.getPair(map);

                HttpTask htask=new HttpTask(tuijianActivity_item.this);

                htask.setCallback(tuijianActivity_item.this);

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
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
            }

            if(part1.equals("okadd")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();


            }
            if(part1.equals("noklikeadd")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();


            }
            if(part1.equals("nokaadd")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();


            }
        }
    }
}
