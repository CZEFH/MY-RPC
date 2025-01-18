package org.rpc.fault.tolerant;

import lombok.extern.slf4j.Slf4j;
import org.rpc.model.RpcRequest;
import org.rpc.model.RpcResponse;
import org.rpc.proxy.ServiceProxyFactory;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class FailBackTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) throws Exception {
        // 通过context里的信息 获取降级接口
        RpcRequest rpcRequest = (RpcRequest) context.getOrDefault("rpcRequest", null);
        if (rpcRequest == null) {
            log.info("FAIL_BACK：", e);
            throw new Exception(e.getMessage());
        }

        String serviceName = rpcRequest.getServiceName();
        Class<?> serviceClass = Class.forName(serviceName);

        //使用模拟接口
        Object mockProxy = ServiceProxyFactory.getMockProxy(serviceClass);
        Method method = mockProxy.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        Object result = method.invoke(mockProxy, rpcRequest.getArgs());

        // 返回结果
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setData(result);
        rpcResponse.setDataType(method.getReturnType());
        rpcResponse.setMessage("FAIL_BACK");

        return rpcResponse;
    }
}
