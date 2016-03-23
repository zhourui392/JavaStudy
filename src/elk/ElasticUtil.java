package elk;

import util.HttpUtil;

public class ElasticUtil {
	public static final String HOST = "http://103.21.116.220:9200/";
	/**
	 * index create
	 */
	public static String createIndex(){
		
		String aa = HttpUtil.get(HOST);
		return aa;
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
	public static String deleteIndex(){
		
		return null;
	}
}
