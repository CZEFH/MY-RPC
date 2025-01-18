package org.rpc.fault.tolerant;

import org.rpc.model.RpcRequest;
import org.rpc.model.RpcResponse;
import org.rpc.model.ServiceMetaInfo;
import org.rpc.server.tcp.VertxTcpClient;
import org.rpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) throws Exception {
        // context里存储 request,selectServiceMetaInfoList
        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.getOrDefault("serviceMetaInfoList", new ArrayList<>());
        if (serviceMetaInfoList.isEmpty()) {
            throw new RuntimeException("暂无可用服务", e);
        }
        RpcRequest rpcRequest = (RpcRequest) context.getOrDefault("rpcRequest", null);

        try {
            // 每个服务都试一次
            return VertxTcpClient.doRequest(rpcRequest, serviceMetaInfoList.get(0));
        } catch (Exception err) {
            serviceMetaInfoList.remove(0);
            context.put("serviceMetaInfoList", serviceMetaInfoList);
            return doTolerant(context, e);
        }
    }
}
