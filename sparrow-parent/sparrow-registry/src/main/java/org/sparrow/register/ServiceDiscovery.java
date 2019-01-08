package org.sparrow.register;


import org.sparrow.curator.CuratorClient;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :tongzilong@mgzf.com
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ServiceDiscovery {

    private static CuratorClient zkClient = CuratorClient.getInstance(ZKconfig.ZK_REGISTER_ADDRESS);

    public static String discovery(String serviceName) {
        try {
            String path = ZKconfig.ZK_REGISTER_PATH + "/" + serviceName;
            String address = zkClient.readRandom(path);
            System.out.println("discovery-------->" + path);
            System.out.println("IServiceDiscovery------" + address);
            return address;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
