package org.rpc.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {

    @Override
    public void doStart(int port) {

        Vertx vertx = Vertx.vertx();

        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动监听并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("正在监听端口" + port);
            } else {
                System.err.println("监听失败：" + result.cause());
            }
        });
    }
}
