package elk;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.json.Json;

import com.alibaba.fastjson.JSONObject;

public class ElasticUtilTest {
	
	private static String indexName = "hello";
	
	@Before
	public void before(){
		Map<String, String> params = new HashMap<>();
		String resturnJson = ElasticUtil.createIndex(indexName,params);
		assertEquals(true, getAcknowledgedByReturnStr(resturnJson));
	}
	
	@Test
	public void testCreateIndex() {
		
	}
	
	public static boolean getAcknowledgedByReturnStr(String resturnJson){
		JSONObject jsonObject = JSONObject.parseObject(resturnJson);
		return jsonObject.getBoolean("acknowledged");
	}
	
	@After
	public void after(){
		String resturnJson = ElasticUtil.deleteIndex(indexName);
		assertEquals(true, getAcknowledgedByReturnStr(resturnJson));
	}
}
