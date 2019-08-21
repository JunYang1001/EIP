package com.zjy1001.androiddemotest.net;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpHelper {
	private DefaultHttpClient httpClient;
	private HttpContext localContext;
	private HttpResponse response;
	private HttpPost httpPost;
	private String ret;

	public HttpHelper() {
		httpClient = new DefaultHttpClient();
		localContext = new BasicHttpContext();
	}
	public String postPage(String url, String data) {
		ret = null;
		data = data == null ? "" : data;
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY
				, CookiePolicy.RFC_2109);

		httpPost = new HttpPost(url);
		response = null;
		StringEntity tmp = null;// 字符串数据
		httpPost.setHeader( "User-Agent",
		"Mozilla/5.0 (X11; U; Linux "
		+ "i686; en-US; rv:1.8.1.6) Gecko" +
		"/20061201 Firefox/2.0.0.6 (Ubuntu-feisty)");
		httpPost.setHeader(
		"Accept",
		"text/html,application/xml,"
		+ "application/xhtml+xml,text/html;q=0.9,text/plain;" +
		"q=0.8,image/png,*/*;q=0.5");
		httpPost.setHeader("Content-Type",
		"application/x-www-form-urlencoded");
		try {
			tmp = new StringEntity(data, "UTF-8");
			httpPost.setEntity(tmp);
			response = httpClient.execute(httpPost, localContext);
			if ((response != null) && (response.getEntity() != null)) {
				ret = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
}
