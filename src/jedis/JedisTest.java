package jedis;

import java.util.Set;

import org.nutz.json.Json;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
	private static JedisPool pool;
	private static final String host = "192.168.1.201";
	public static void main(String[] args) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
				//public JedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password)
		pool = new JedisPool(new JedisPoolConfig(), host,6379,2000,"123");
		try (Jedis jedis = pool.getResource()) {
			  /// ... do stuff here ... for example
			  jedis.set("foo", "bar");
			  String foobar = jedis.get("foo");
			  System.out.println(foobar);
			  jedis.zadd("sose", 0, "car"); jedis.zadd("sose", 0, "bike"); 
			  Set<String> sose = jedis.zrange("sose", 0, -1);
			  System.out.println(Json.toJson(sose));
		}
		/// ... when closing your application:
		pool.destroy();
	}
}
