package org.sparrow.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :tongzilong@mgzf.com
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RpcProxyHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private String response;

    public String getResponse() {
        return response;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("RpcProxyHandler----------------");
        String result = msg.toString(CharsetUtil.UTF_8);
        System.out.println("RpcProxyHandler-----" + result);
        this.response = result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
