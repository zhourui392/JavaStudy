package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class HttpUtil {
	private static final Log logger = Logs.get();
	public static String get(String url){
		HttpGet get = new HttpGet(url);
		//设置5秒的超时
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
		get.setConfig(requestConfig);
		CloseableHttpClient client = null;
		try{
			client = HttpClients.createDefault();
		}catch(IllegalStateException e){
			return null;
		}
		 
		HttpEntity httpEntity = null;
		try {
			CloseableHttpResponse response = client.execute(get);
			httpEntity = response.getEntity();
			if (httpEntity != null){
				return EntityUtils.toString(httpEntity);
			}
		} catch( HttpHostConnectException hostException){
			logger.errorf("Connection error: connect to {}, exception:{}",url,hostException);
		} catch (NoRouteToHostException noroutE) {
			logger.errorf("No route to host: connect{}, exception:{}",url,noroutE);
		} catch (IllegalStateException e) {
			logger.errorf("IllegalStateException: connect{}, exception:{}",url,e);
		} catch (ParseException e) {
			logger.errorf("ParseException: connect{}, exception:{}",url,e);
		} catch (IOException e) {
			logger.errorf("IOException: connect{}, exception:{}",url,e);
		}
		return null;
	}
	
	public static String post(String url, Map<String, String> params){
		HttpPost post = new HttpPost(url);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet){
			formparams.add(new BasicNameValuePair(key, params.get(key)));
		}
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(formparams, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("post throw exception:{}",e);
		}
		post.setEntity(entity);
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpEntity httpEntity = null;
		try {
			CloseableHttpResponse response = client.execute(post);
			httpEntity = response.getEntity();
			if (httpEntity != null){
				return EntityUtils.toString(httpEntity);
			}
		} catch (IllegalStateException e) {
			logger.errorf("post {} and throw IllegalStateException:{}",url,e);
		} catch (IOException e) {
			logger.errorf("post {} and throw IOException:{}",url,e);
		}
		return null;
	}
	
	public static String delete(String url){
		HttpDelete get = new HttpDelete(url);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpEntity httpEntity = null;
		try {
			CloseableHttpResponse response = client.execute(get);
			httpEntity = response.getEntity();
			if (httpEntity != null){
				return EntityUtils.toString(httpEntity);
			}
		} catch (IllegalStateException e) {
			logger.error("IllegalStateException when httpdelete:{}",e);
		} catch (IOException e) {
			logger.error("IllegalStateException when IOException:{}",e);
		}
		return null;
	}
	
	public static String put(String url, Map<String, String> params){
		HttpPut put = new HttpPut(url);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (params != null){
			Set<String> keySet = params.keySet();
			for (String key : keySet){
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity entity = null;
			try {
				entity = new UrlEncodedFormEntity(formparams, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("UnsupportedEncodingException when urlEncode:{}",e);
			}
			put.setEntity(entity);
		}
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpEntity httpEntity = null;
		try {
			CloseableHttpResponse response = client.execute(put);
			httpEntity = response.getEntity();
			if (httpEntity != null){
				return EntityUtils.toString(httpEntity);
			}
		} catch (IllegalStateException e) {
			logger.error("IllegalStateException when httpput:{}",e);
		} catch (IOException e) {
			logger.error("IOException when httpput:{}",e);
		}
		return null;
	}
}