package org.sparrow.curator;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * zookeeper监听节点数据变化
 *
 * @author :tongzilong@mgzf.com
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
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
