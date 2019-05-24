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
import org.sparrow.client.SparrowClient;
import org.sparrow.common.RpcEncoder;
import org.sparrow.common.RpcRequest;
import org.sparrow.common.RpcResponse;
import org.sparrow.register.ZKDiscoveryCenter;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;


/**
 * @ClassName SparrowProxy
 * @Author leo
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
        String serviceAddress = ZKDiscoveryCenter.discovery(serviceName);
        String[] addrs = serviceAddress.split(":");
        String ip = addrs[0];
        int port = NumberUtils.createInteger(addrs[1]);
        System.out.println("ip:" + ip + "---port:" + port);
        SparrowClient client = new SparrowClient(ip, port); // 初始化 RPC 客户端
        RpcResponse response = client.send(request); // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应
        if (response.getError() != null) {
            throw response.getError();
        } else {
            return response.getResult();
        }
    }

}

