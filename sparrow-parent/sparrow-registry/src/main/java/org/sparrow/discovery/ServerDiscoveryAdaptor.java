package org.sparrow.discovery;


import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.sparrow.bean.VMState;
import org.sparrow.bean.WorkerNode;
import org.sparrow.curator.CuratorManager;
import org.sparrow.curator.ZKconfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WorkerDiscoveryImpl
 * @Author Leo
 * @Description //TODO
 * @Date: 2019/5/15 17:41
 **/
@Slf4j
public class ServerDiscoveryAdaptor implements InitializingBean {

    /**
     * key:父路径
     * <p>
     * value：Map-->key:子路径，value:子路径中的值
     */
    @Getter
    private static Map<String, ConcurrentHashMap<String, String>> nodeCacheMap = new ConcurrentHashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        nodeCacheWatcher();
    }

    public void nodeCacheWatcher() {
        this.initData();
        Arrays.stream(VMState.values()).forEach(item -> {
            PathChildrenCache pathChildrenCache = CuratorManager.registerPathChildListener(ZKconfig.ZK_REGISTER_PATH + "/" + item.name(), (client, event) -> {
                ChildData childData = event.getData();
                if (childData == null) {
                    return;
                }
                String path = childData.getPath();
                if (StringUtils.isEmpty(path)) {
                    return;
                }
                String[] paths = path.split("/");
                if (paths == null || path.length() < 4) {
                    return;
                }
                String ip = paths[3];
                ConcurrentHashMap<String, String> map = nodeCacheMap.get(item.name());
                switch (event.getType()) {
                    case CHILD_ADDED:
                        log.info("正在新增子节点：" + childData.getPath());
                        map.put(ip, new String(childData.getData()));
                        break;
                    case CHILD_UPDATED:
                        log.info("正在更新子节点：" + childData.getPath());
                        map.put(ip, new String(childData.getData()));
                        break;
                    case CHILD_REMOVED:
                        log.info("子节点被删除");
                        map.remove(ip);
                        break;
                    case CONNECTION_LOST:
                        log.info("连接丢失");
                        clearData();
                        break;
                    case CONNECTION_SUSPENDED:
                        log.info("连接被挂起");
                        break;
                    case CONNECTION_RECONNECTED:
                        log.info("恢复连接");
                        initData();
                        break;
                }
            });
        });
    }


    public String getValue(String key) {
        for (Map.Entry<String, ConcurrentHashMap<String, String>> entry : nodeCacheMap.entrySet()) {
            String weight = entry.getValue().get(key);
            if (Objects.nonNull(weight))
                return weight;
        }
        return null;
    }

    private ConcurrentHashMap<String, String> fetchNodeFromRegister(VMState state) {
        try {
            String rootPath = ZKconfig.ZK_REGISTER_PATH + "/" + state.name();
            List<String> strings = CuratorManager.getChildren(rootPath);
            if (!CollectionUtils.isEmpty(strings)) {
                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(strings.size());
                strings.forEach(i -> {
                    try {
                        map.put(i, CuratorManager.getData(rootPath + "/" + i));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });
                return map;
            }
        } catch (Exception e) {
            log.error("worker discover failed ", e);
        }
        return new ConcurrentHashMap<>();
    }

    public String getNodeCache() {
        return JSONObject.toJSONString(nodeCacheMap);
    }

    public void initData() {
        if (MapUtils.isNotEmpty(nodeCacheMap)) {
            nodeCacheMap.clear();
        }
        ConcurrentHashMap<String, String> full_nodeCacheMap = fetchNodeFromRegister(VMState.FULL);
        ConcurrentHashMap<String, String> cold_nodeCacheMap = fetchNodeFromRegister(VMState.COLD);
        ConcurrentHashMap<String, String> idle_nodeCacheMap = fetchNodeFromRegister(VMState.IDLE);
        nodeCacheMap.put(VMState.COLD.name(), cold_nodeCacheMap);
        nodeCacheMap.put(VMState.IDLE.name(), idle_nodeCacheMap);
        nodeCacheMap.put(VMState.FULL.name(), full_nodeCacheMap);
    }

    public void clearData() {
        nodeCacheMap.get(VMState.COLD.name()).clear();
        nodeCacheMap.get(VMState.IDLE.name()).clear();
        nodeCacheMap.get(VMState.FULL.name()).clear();
    }

}
