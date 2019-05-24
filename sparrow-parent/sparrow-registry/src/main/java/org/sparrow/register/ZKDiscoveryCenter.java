package org.sparrow.register;


import lombok.extern.slf4j.Slf4j;
import org.sparrow.curator.CuratorManager;
import org.sparrow.curator.ZKconfig;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :Leo
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Slf4j
public class ZKDiscoveryCenter {


    public static String discovery(String serviceName) {
        try {
            String path = ZKconfig.ZK_REGISTER_PATH + "/" + serviceName;
            List<String> address = CuratorManager.getChildren(path);
            if (CollectionUtils.isEmpty(address)) {
                throw new Exception("can not find server error");
            }
            System.out.println("discovery-------->" + path);
            System.out.println("IServiceDiscovery------" + address);
            return address.get(new Random().nextInt(address.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
