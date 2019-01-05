package org.sparrow.curator;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * zookeeper监听节点数据变化
 *
 * @author :leo
 */
public class ZKWatcher implements CuratorWatcher {
    private String parentPath;
    private String path;

    public ZKWatcher(String parentPath, String path) {
        this.parentPath = parentPath;
        this.path = path;
    }

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {

    }
}
