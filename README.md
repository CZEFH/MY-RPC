### **项目介绍**

参考 Dubbo 开源项目自主设计实现的 Java 高性能 RPC 框架；基于 Vert.x TCP 服务器 + 自定义协议实现网络传输；基于 Etcd 实现注册中心以完成服务的注册消费；还支持通过 SPI 机制动态扩展序列化器、负载均衡器、重试和容错策略等。

### **主要工作**

#### 后端（简易版 rpc-easy）

1. 核心架构：包括消费方调用、序列化器、网络服务器、请求处理器、服务注册器模块。
2. 网络服务器：基于 Vert.x 的 HTTP 服务器，实现服务提供者和消费者的高性能网络通信。
3. 服务注册器：使用线程安全的 ConcurrentHashMap 存储本地服务注册信息，可以根据服务名称获取到对应实现类，并通过反射完成方法调用。
4. 序列化器：为便于扩展，编写通用的序列化器接口，并基于 Java 原生的 Object 和 ByteArray 输入输出流实现 JdkSerializer 序列化器，使得对象能够网络传输。
5. 请求处理器：基于 Vert.x 的 Handler 接口实现对请求的异步处理，将请求数据反序列化后，从服务注册器中找到服务实现类并通过反射机制调用。
6. 消费方调用：基于 JDK 动态代理 + 工厂模式实现，为指定服务接口类生成可发送 HTTP 请求的代理对象，实现远程方法的无感知调用。

#### 后端（扩展版 rpc-core）

1. 包括消费方调用、序列化器、网络服务器、请求处理器、注册中心、负载均衡器、重试策略、容错策略等模块。
2. 基于 JDK 动态代理 + 工厂模式实现消费方调用模块，为指定服务接口类生成可发送 HTTP 请求的代理对象，实现远程方法的调用。
3. 使用双检锁单例模式维护全局配置对象，并通过 Hutool 的 Props 实现多环境配置文件的加载。
4. 通过 JDK 动态代理 + 工厂模式实现，为指定服务接口类生成返回模拟数据的 Mock 服务对象，便于开发者测试。
5. 定义序列化器接口，实现了基于 JSON、Kryo 和 Hessian 的序列化器，并通过 ThreadLocal 解决了 Kryo 序列化器的线程安全问题。
6. 使用工厂模式 + 单例模式简化创建和获取序列化器对象的操作。并通过扫描资源路径 + 反射自实现了 SPI 机制，用户可通过编写配置的方式扩展和指定自己的序列化器。
7. 基于 Etcd 云原生中间件实现了高可用的分布式注册中心，利用其层级结构和 Jetcd 的 KvClient 存储服务和节点信息，并支持通过 SPI 机制扩展。
8. 利用定时任务和 Etcd Key 的 TTL 实现服务提供者的心跳检测和续期机制，节点下线一定时间后自动移除注册信息。
9. 使用本地对象维护已获取到的服务提供者节点缓存，相比于每次从注册中心获取；并通过 Etcd 的 Watch 机制，监听节点的过期并自动更新缓存。
10. 由于 HTTP 协议头信息较多，基于 Vert.x TCP 服务器 + 类 Dubbo 的紧凑型消息结构（字节数组）自实现了 RPC 协议，提升网络传输性能。
11. 基于 Vert.x 的 RecordParser 完美解决半包粘包问题，并使用装饰者模式封装了 TcpBufferHandlerWrapper 类，一行代码即可对原有的请求处理器进行增强，提高代码的可维护性。
12. 为提高服务提供者集群处理能力，实现了一致性 Hash、轮询、随机等不同算法的负载均衡器，并通过 SPI 机制支持开发者自行扩展。
13. 为提高消费端调用的稳定性，基于 Guava Retrying 实现了包括 fixedWait 等多种重试策略，并通过 SPI 机制支持开发者自行扩展。
14. 为提高系统的稳定性和可用性，设计实现了 FailOver、FailBack、FailSafe、FailFast 等多种重试策略，并通过 SPI 机制支持开发者自行扩展。
