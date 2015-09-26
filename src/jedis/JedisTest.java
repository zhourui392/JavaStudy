package jedis;

import java.util.Set;

import org.nutz.json.Json;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
	private static JedisPool pool;
	private static final String host = "";
	public static void main(String[] args) {
		pool = new JedisPool(new JedisPoolConfig(), host);
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
