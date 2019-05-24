package org.sparrow.server;

import org.sparrow.utils.IPUtils;
import org.sparrow.register.ZKRegisterCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RpcServerConfig
 * @Author Leo
 * @Description //TODO
 * @Date: 2019/5/24 15:25
 **/
@Configuration
public class RpcServerConfig {
    @Bean
    public RpcServer configServer() {
        return new RpcServer(new ZKRegisterCenter(), IPUtils.getIpAddress());
    }
}
