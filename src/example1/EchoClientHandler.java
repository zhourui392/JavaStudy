package example1;


import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	
	private static Log log = Logs.get();

	/**
	 * 从服务器端读消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		log.info("Client receive: "+msg);
		
		//读到消息后，继续给服务器发消息
		ctx.writeAndFlush(msg);
	}

	/**
	 * 激活时发送消息给服务器
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelActive invoked");
		ctx.writeAndFlush(Unpooled.copiedBuffer("netty client", CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

}
