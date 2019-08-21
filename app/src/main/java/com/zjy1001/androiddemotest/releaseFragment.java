package com.zjy1001.androiddemotest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JunYang on 2018/2/21.
 */


public class releaseFragment extends android.support.v4.app.Fragment {

    @BindView( R.id.activityadd )
    Button bt1;
    @BindView( R.id.articleadd )
    Button bt2;
    String str;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View fragment=inflater.inflate( R.layout.activity_release_add,container,false );
        ButterKnife.bind(this,fragment);
        Bundle bundle = getActivity().getIntent().getExtras();
        str = bundle.getString("id");
        return fragment;
    }

    @OnClick({R.id.activityadd, R.id.articleadd })
   public void   myclick(View v){


        switch (v.getId()){
            case R.id.activityadd:
                Intent intent=new Intent( getActivity(),activity_add.class );
                intent.putExtra("id", str);
                startActivity( intent );
                break;
            case R.id.articleadd:
                Intent aintnet=new Intent( getActivity(),article_add.class );
                aintnet.putExtra("id", str);
                startActivity( aintnet );
                break;

                default:

        }
    }

}
