package org.rpc.consumer;

import org.rpc.bootstrap.ConsumerBootstrap;
import org.rpc.config.RpcConfig;
import org.rpc.model.User;
import org.rpc.proxy.ServiceProxyFactory;
import org.rpc.service.UserService;
import org.rpc.utils.ConfigUtils;

public class ConsumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("张云博");
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user为空");
        }
        int number = userService.getNumber();
        System.out.println(number);
    }
}
