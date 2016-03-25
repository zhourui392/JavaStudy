package elk;

import java.util.Map;

import util.HttpUtil;

public class ElasticUtil {
	public static final String HOST = "http://119.254.209.219:9200/";
	/**
	 * index create
	 */
	public static String createIndex(String indexName,Map<String, String> params){
		return HttpUtil.put(HOST+"/"+indexName, params);
	}
	
	/**
	 * index update
	 */
	public static String updateIndex(){
		
		return null;
	}
	
	/**
	 * index query
	 */
	public static String queryIndex(){
		
		return null;
	}
	
	/**
	 * index delete
	 */
	public static String deleteIndex(String indexName) {
		return HttpUtil.delete(HOST+"/"+indexName);
	}
}
