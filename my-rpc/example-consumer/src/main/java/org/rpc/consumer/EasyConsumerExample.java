package org.rpc.consumer;

import org.rpc.model.User;
import org.rpc.proxy.ServiceProxyFactory;
import org.rpc.service.UserService;

public class EasyConsumerExample {

    public static void main(String[] args) {
        //静态代理
//        UserService userService = new UserServiceProxy();
        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("张云博");
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user为空");
        }
    }
}
