package org.rpc.fault.tolerant;

import org.rpc.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败 - 容错策略
 */
public class FailFastTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务异常", e);
    }
}
