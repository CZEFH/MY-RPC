package org.rpc.fault.retry;

import org.rpc.loadbalancer.LoadBalancer;
import org.rpc.spi.SpiLoader;

public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final RetryStrategy DEFAULT_LOAD_BALANCER = new FixedIntervalRetryStrategy();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
