package org.sparrow.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.apache.commons.lang3.math.NumberUtils;
import org.sparrow.client.RpcProxyHandler;
import org.sparrow.common.RpcEncoder;
import org.sparrow.common.RpcRequest;
import org.sparrow.common.annotation.Reference;
import org.sparrow.register.ServiceDiscovery;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;


/**
 * @ClassName SparrowProxy
 * @Author Reference
 * @Description //TODO
 * @Date: 2019/1/5 15:38
 **/
public class SparrowProxy implements InvocationHandler {

    private Class<?> interfaceClass;

    public Object bind(Class<?> cls) {
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getSimpleName());
        request.setMethodName(method.getName());
        request.setTypes(method.getParameterTypes());
        request.setParams(args);
        String serviceName = interfaceClass.getSimpleName();
        if (serviceName.startsWith("I")) {
            serviceName = serviceName.substring(1);
            serviceName = serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);
        }
        String serviceAddress = ServiceDiscovery.discovery(serviceName);
        String[] addrs = serviceAddress.split(":");
        String ip = addrs[0];
        int port = NumberUtils.createInteger(addrs[1]);
        System.out.println("ip:" + ip + "---port:" + port);
        final RpcProxyHandler proxyHandler = new RpcProxyHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(ip, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RpcEncoder(RpcRequest.class)) // 将 RPC 请求进行编码（为了发送请求）
                                    // .addLast(new RpcDecoder(RpcResponse.class)) // 将 RPC 响应进行解码（为了处理响应）
                                    .addLast(proxyHandler);
                        }
                    });

            ChannelFuture future = b.connect().sync();
//                    byte[] byteBuff = SerializeProtoStuff.serialize(RpcRequest.class, request);
//                    future.channel().writeAndFlush(Unpooled.copiedBuffer(byteBuff));
            future.channel().writeAndFlush(request);

            //主线程应用程序会一直等待，直到channel关闭
            future.channel().closeFuture().sync();
            System.out.println("proxyHandler.getResponse--" + proxyHandler.getResponse());
            System.out.println("主线程应用程序会一直等待，直到channel关闭");
            return proxyHandler.getResponse();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return proxyHandler.getResponse();
    }

}

