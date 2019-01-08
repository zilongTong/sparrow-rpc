package org.sparrow.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.sparrow.common.RpcRequest;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :tongzilong@mgzf.com
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Map<String, Object> handlerMap = new HashMap();

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        System.out.println("RpcServerHandler-----" + rpcRequest.toString());
        System.out.println("1111111111" + rpcRequest.getClassName());
        Object result = new Object();
        if (handlerMap.containsKey(rpcRequest.getClassName())) {
            System.out.println("2222222");
            Object clazz = handlerMap.get(rpcRequest.getClassName());
            System.out.println("clazz----" + clazz.toString());
            Method method = clazz.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getTypes());
            result = method.invoke(clazz, rpcRequest.getParams());
            System.out.println("result-----" + result.toString());
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer(result.toString(), CharsetUtil.UTF_8));
    }

    //    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("RpcServerHandler-----" + msg);
//        ByteBuf in = (ByteBuf) msg;
//        System.out.println(in.toString());
//        byte[] b = new byte[in.capacity()];
//        in.readBytes(b);
//        System.out.println("byte msg----" + new String(b));
//        RpcRequest rpc = SerializeProtoStuff.deserialize(RpcRequest.class, b);
//        System.out.println("1111111111" + rpc.getClassName());
//        Object result = new Object();
//        if (handlerMap.containsKey(rpc.getClassName())) {
//            Object clazz = handlerMap.get(rpc.getClassName());
//            Method method = clazz.getClass().getMethod(rpc.getMethodName(), rpc.getTypes());
//            result = method.invoke(clazz, rpc.getParams());
//        }
//
//        ctx.writeAndFlush(Unpooled.copiedBuffer(result.toString(), CharsetUtil.UTF_8));
//        ctx.close();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
