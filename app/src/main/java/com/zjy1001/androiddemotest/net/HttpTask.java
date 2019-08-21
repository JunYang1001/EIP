package com.zjy1001.androiddemotest.net;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class HttpTask  extends AsyncTask<String, Void, Void>{
	private String reponse;//用于接收响应的字符串
	private HttpCallBack callback;//用于回调
	private HttpHelper httphelper;
	private ProgressDialog progress=null;
	public HttpTask(Context context){
		super();
		httphelper=new HttpHelper();
		progress=ProgressDialog.show(context, "请稍后", "正在加载数据。。。。",true,true);
	}
	public void setCallback(HttpCallBack callback){
		this.callback=callback;
	}
	@Override
	protected Void doInBackground(String... params) {
		if(params.length>1){//带地址，带请求内容
			reponse=httphelper.postPage(params[0], params[1]);
		}else{//带地址，没有传递参数
			reponse=httphelper.postPage(params[0],null);
		}return null;
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progress.dismiss();
		if(callback!=null){
			callback.callback(reponse);
		}}

}
