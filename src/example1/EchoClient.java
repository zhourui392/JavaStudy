package example1;

import java.net.InetSocketAddress;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private static Log log = Logs.get();
	private String host;
	private int port;
	
	public EchoClient(String host,int port){
		this.host = host;
		this.port = port;
	}
	
	public void startClient(){
		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		
		bootstrap.group(group).channel(NioSocketChannel.class).
			handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new EchoClientHandler());
				}
				
			});
		
		try {
			ChannelFuture future = bootstrap.connect(host,port).sync();
			
			log.debug(future.isSuccess());
			
			future.channel().closeFuture().sync();
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			if (group != null){
				group.shutdownGracefully();
			}
		}finally{
		}
	}
	
	public static void main(String[] args) {
		new EchoClient("192.168.1.79", 20000).startClient();
	}
}
