package example1;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	
	private static Log log = Logs.get();
	
	private int port;	//服务器暴露端口
	
	public EchoServer(int port){
		this.port = port;
	}
	
	public void start(){
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		//NioEventLoopGroup来接受和处理连接
		EventLoopGroup group = new NioEventLoopGroup();
		
		//指定通道类型NioServerSocketChannel
		//指定连接后调用的ChannelHandler
		bootstrap.group(group).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(new EchoServerHandler());
						}
					});
		
		try {
			//调用sync()方法会阻塞直到服务器完成绑定
			ChannelFuture future = bootstrap.bind(port).sync();
			
			log.debug(EchoServer.class.getName()+"start and listen on "+future.channel().localAddress());
			
			future.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("thread interrupted");
		}finally{
			if (group != null){
				group.shutdownGracefully();
			}
		}
	}
	
	public static void main(String[] args) {
		int port = 20000;
		new EchoServer(port).start();
	}
}
