package org.sparrow.common;

import lombok.Data;

import java.io.Serializable;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :Leo
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 5978740177305755386L;

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
    private BaseMsg baseMsg;

    @Override
    public String toString() {
        return this.getClassName() + this.methodName + this.parameters.toString();
    }
}
