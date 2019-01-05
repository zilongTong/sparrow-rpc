package org.sparrow.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.math.NumberUtils;
import org.sparrow.common.RpcEncoder;
import org.sparrow.common.RpcRequest;
import org.sparrow.register.IServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :tongzilong@mgzf.com
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RpcClientProxy {
    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T create(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setClassName(method.getDeclaringClass().getSimpleName());
                request.setMethodName(method.getName());
                request.setTypes(method.getParameterTypes());
                request.setParams(args);
                String serviceName = interfaceClass.getSimpleName();
                String serviceAddress = serviceDiscovery.discovery(serviceName);
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
        });
    }
}
