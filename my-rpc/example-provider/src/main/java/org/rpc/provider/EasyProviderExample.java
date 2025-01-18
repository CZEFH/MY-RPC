package org.rpc.provider;

import org.rpc.registry.LocalRegistry;
import org.rpc.server.HttpServer;
import org.rpc.server.VertxHttpServer;
import org.rpc.service.UserService;

public class EasyProviderExample {
    public static void main(String[] args) {

        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8888);
    }
}
