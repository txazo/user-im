## user-im

* [技术专辑](http://www.52im.net/forum.php?mod=collection&op=all)
* [IM群聊技术文章](http://www.52im.net/forum.php?mod=collection&action=view&ctid=20&fromop=all)
* [IM群聊消息究竟是存1份(即扩散读)还是存多份(即扩散写)？](http://www.52im.net/thread-1616-1-1.html)

* https://www.processon.com/view/5bc2f3e2e4b0bd4db96210c7#map

#### 消息ID生成策略

* 方案一
    * 时间戳 + 自增id + 回话类型 + 回话id -> 16进制

### todo

* Client: 失败重连、断线重连、连接超时切换
* Server: 连接鉴权、非法连接踢出、心跳超时连接踢出
* 群聊: 群聊id
* 消息
    * 协议: 魔数、版本号、压缩标识、消息类型、数据长度、数据
    * 序列化/反序列化: ProtoBuf
    * 压缩/解压
    * handler: 粘包/拆包
* Zookeeper
    * 服务注册与发现: IMServer注册(ip + port)
* IMServer
    * 重启
    * shutdown: 钩子
    * 消息转发到其它IMServer: 直连或MQ中转
* 长连接
    * 心跳
* IMClient
    * 压测: 模拟高并发
        * 批量生成用户名/密码，注册、登陆、鉴权
        * Bootstrap: 可以多次connect
* IMRoute
    * 分配IMServer(负载均衡)
    * userId和IMServer的映射: redis
* 部署
    * Zookeeper集群: Docker，3台机器
    * IMServer: Docker、本机，3台机器
    * IMRoute: Docker、本机，1台机器，域名访问(im.txazo.com)
    * User: Docker、本机，1台机器，域名访问(user.txazo.com)
