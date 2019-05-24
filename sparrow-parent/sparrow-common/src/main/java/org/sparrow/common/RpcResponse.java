package org.sparrow.common;

import lombok.Data;

@Data
public class RpcResponse {

    private String requestId;
    private Throwable error;
    private Object result;
    private BaseMsg baseMsg;


}
