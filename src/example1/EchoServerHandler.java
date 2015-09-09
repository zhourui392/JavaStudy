package example1;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private static Log log = Logs.get();
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		log.debug("channelRead invoked");
		ctx.write(msg); // 读消息后返回信息给客户端
		ctx.flush(); // (2)
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.debug("channelActive invoked");
		super.channelActive(ctx);
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.debug("channelreadcomlete invoked");
	}

	@Override  
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
	    cause.printStackTrace();  
	    ctx.close();  
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.debug("register invoked");
		super.channelRegistered(ctx);
	} 
}
