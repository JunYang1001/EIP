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
import com.zjy1001.androiddemotest.bean.JoActivity;
import com.zjy1001.androiddemotest.net.HttpCallBack;
import com.zjy1001.androiddemotest.net.HttpTask;
import com.zjy1001.androiddemotest.net.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class lookmyjoinactActivity_item extends AppCompatActivity implements HttpCallBack{
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
    @BindView( R.id.deletejoinact )
    Button btdelete;
    String userid;
    private String path="http://10.0.2.2:8080/EIP/MyJoinActController/deletejoinmyact.action";
   // private String path="http://192.168.43.216:8080/EIP/MyJoinActController/deletejoinmyact.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lookmyjoinact_item );
        ButterKnife.bind(lookmyjoinactActivity_item.this);
        final Intent intent=getIntent();
        userid=intent.getStringExtra( "userid" );//登录者id
        ptoolbar.setTitle("活动介绍");
        setSupportActionBar(ptoolbar);
        JoActivity activity=(JoActivity)this.getIntent().getExtras( ).get("activity");
        switch (activity.getAct_type()) {
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
        int jid=activity.getJ_id();
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

    @OnClick({R.id.deletejoinact})
    public void  myclick(View view){
        switch (view.getId()){
            case R.id.deletejoinact:
               // Activity activity=(Activity)this.getIntent().getExtras( ).get("activity");
                JoActivity activity=(JoActivity)this.getIntent().getExtras( ).get("activity");
                int jid=activity.getJ_id();
                Map<String, String> map=new HashMap<String, String>();
                map.put( "joinid",jid+" " );
                map.put("flag", "getdeletejoinact");
                String string1= Util.getPair(map);

                HttpTask htask=new HttpTask(lookmyjoinactActivity_item.this);

                htask.setCallback(lookmyjoinactActivity_item.this);

                htask.execute(path,string1);
        }
    }

    @Override
    public void callback(String respone) {
        if(respone!=null){
            String part1=respone.substring(0, respone.indexOf("?"));
            String part2=respone.substring(respone.indexOf("?")+1);
            if (part1.equals("okdel")){
                Toast.makeText(this, part2, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent( lookmyjoinactActivity_item.this,lookmyjoinactActivity.class );
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


