package xmemcached;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.command.KestrelCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcachedTest {
	public static void main(String[] args) throws Exception{
		//New a XMemcachedClient instance
	     XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.2.10:11211"));
	     MemcachedClient client= builder.build();

	    //If you want to use binary protocol
	     XMemcachedClientBuilder builder2 = new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.2.10:11211"));
	     builder2.setCommandFactory(new BinaryCommandFactory());
	     MemcachedClient client2=builder2.build();

	    //If you want to use xmemcached talking with kestrel
	    XMemcachedClientBuilder builder3 = new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.2.10:11211"));
	    builder3.setCommandFactory(new KestrelCommandFactory());
	    MemcachedClient client3=builder3.build();

	    //If you want to store primitive type as String
	    client.setPrimitiveAsString(true);

	    //Add or remove memcached server dynamically
//	    client.addServer("192.168.2.10:11211 192.168.2.10:11211");
//	    client.removeServer("192.168.2.10:11211 192.168.2.10:11211");


	    //get operation
	    String name =client.get("test");

	    //set add replace append prepend gets
	    client.add("hello", 0, "dennis");
	    client.replace("hello", 0, "dennis");
	    client.append("hello", " good");
	    client.prepend("hello", "hello ");
	    GetsResponse response=client.gets("hello");
	    long cas=response.getCas();
	    Object value=response.getValue();

	    //incr decr
	    client.set("a",0,"1");
	    client.incr("a",4);
	    client.decr("a",4);

	    //cas
	    client.cas("a", 0, new CASOperation() {
	                    @Override
	                    public int getMaxTries() {
	                                return 1;  //max try times
	                    }
	                    @Override
	                    public Object getNewValue(long currentCAS, Object currentValue) {
	                                System.out.println("current value " + currentValue);
	                                return 3; //return new value to update
	                    }
	    });

	    //flush_all
	    client.flushAll();

	    //stats
	    Map<InetSocketAddress,Map<String,String>> result=client.getStats();

	    // get server versions
//	    Map<InetSocketAddress,String> version=memcached.getVersions();

	    //bulk get
	    List<String> keys = new ArrayList<String>();
	    keys.add("hello");
	    keys.add("test");
	    Map<String, Object> map = client.get(keys);
	}
}
