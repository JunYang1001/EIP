package com.zjy1001.androiddemotest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class otheractivity extends AppCompatActivity {

    @BindView(R.id.main_toolbarss)
    Toolbar ptoolbar;
    @BindView( R.id.toolbar_titless )
    TextView tv1;
    @BindView( R.id.dialog )
    Button dialog;
    @BindView( R.id.xiugaipwd )
    Button xiugaipwd;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_otheractivity );
        ButterKnife.bind(this);
        final Intent intent=getIntent();
        userid=intent.getStringExtra( "userid" );//登录者id
        ptoolbar.setTitle("关于EIP");

        setSupportActionBar(ptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick({R.id.dialog,R.id.xiugaipwd})
    public  void  myclick(View view){

        switch (view.getId()){
            case R.id.dialog:
                AlertDialog.Builder dialog=new AlertDialog.Builder( otheractivity.this );
                dialog.setTitle( "app版本" );
                dialog.setMessage( "EIP活动邀约平台1.0\n作者：张俊阳" );
                dialog.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } );
                dialog.setCancelable( false ).show();
                break;
            case R.id.xiugaipwd:
                Intent intent=new Intent( otheractivity.this,updatepwdactivity.class );
                intent.putExtra( "userid",userid );
                startActivity( intent );
        }
    }
}
