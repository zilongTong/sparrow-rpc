package org.sparrow.register;

import org.apache.zookeeper.CreateMode;
import org.sparrow.curator.CuratorManager;
import org.sparrow.curator.ZKconfig;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :Leo
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ZKRegisterCenter implements IRegisterCenter {


    @Override
    public void register(String serviceName, String serviceAddress) {
        System.out.println("register---" + serviceName + "/" + serviceAddress);
        String servicePath = ZKconfig.ZK_REGISTER_PATH + "/" + serviceName;
        String addressPath = servicePath + "/" + serviceAddress;
        try {
            if (!CuratorManager.checkExists(servicePath)) {
                CuratorManager.deleteChildrenIfNeeded(servicePath);
            }
            CuratorManager.createPersistentNode(servicePath, "0");
            CuratorManager.createEphemeralNode(addressPath, "0");
        } catch (Exception e) {
            System.out.println(e.getCause() + e.getMessage());
        }
    }
}
