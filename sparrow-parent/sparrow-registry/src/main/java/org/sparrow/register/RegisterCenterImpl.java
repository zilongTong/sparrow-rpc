package org.sparrow.register;

import org.apache.zookeeper.CreateMode;
import org.sparrow.curator.CuratorClient;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :tongzilong@mgzf.com
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RegisterCenterImpl implements IRegisterCenter {
    private CuratorClient zkClient = CuratorClient.getInstance(ZKconfig.ZK_REGISTER_ADDRESS);

    @Override
    public void register(String serviceName, String serviceAddress) {
        System.out.println("register---" + serviceName + "/" + serviceAddress);
        String servicePath = ZKconfig.ZK_REGISTER_PATH + "/" + serviceName;
        String addressPath = servicePath + "/" + serviceAddress;
        try {
            if (!zkClient.checkPathIsNull(servicePath)) {
                zkClient.deleteNode(servicePath);
            }
            zkClient.writeNode(servicePath, "0", CreateMode.PERSISTENT);
            zkClient.writeNode(addressPath, "0", CreateMode.EPHEMERAL);
        } catch (Exception e) {
            System.out.println(e.getCause() + e.getMessage());
        }
    }
}
