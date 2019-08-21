package com.zjy1001.androiddemotest;

/**
 * Created by asus on 2017/5/29.
 */
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast;

    public static void showTextToast(Context context,String msg,int duration){
        if (mToast == null){
            mToast = Toast.makeText(context,msg,duration);
        }else {
            mToast.setText(msg);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}