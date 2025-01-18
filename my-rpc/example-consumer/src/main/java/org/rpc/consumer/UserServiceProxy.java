package org.rpc.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.rpc.model.RpcRequest;
import org.rpc.model.RpcResponse;
import org.rpc.model.User;
import org.rpc.serializer.JdkSerializer;
import org.rpc.serializer.Serializer;
import org.rpc.service.UserService;

import java.io.IOException;

public class UserServiceProxy implements UserService {

    @Override
    public User getUser(User user) {

        Serializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;

            HttpResponse post = HttpRequest.post("http://localhost:8888")
                    .body(bodyBytes)
                    .execute();
            result = post.bodyBytes();

            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
