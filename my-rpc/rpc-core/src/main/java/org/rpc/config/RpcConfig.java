package org.rpc.config;

import lombok.Data;
import org.rpc.fault.retry.RetryStrategyKeys;
import org.rpc.fault.tolerant.TolerantStrategyKeys;
import org.rpc.loadbalancer.LoadBalancerKeys;
import org.rpc.serializer.SerializerKeys;

/**
 * RPC框架配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "zyb-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8888;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JSON;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.FIXED_INTERVAL;

    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_OVER;
}
