## user-im

* [技术专辑](http://www.52im.net/forum.php?mod=collection&op=all)
* [IM群聊技术文章](http://www.52im.net/forum.php?mod=collection&action=view&ctid=20&fromop=all)
* [IM群聊消息究竟是存1份(即扩散读)还是存多份(即扩散写)？](http://www.52im.net/thread-1616-1-1.html)

* https://www.processon.com/view/5bc2f3e2e4b0bd4db96210c7#map

#### 消息ID生成策略

* 方案一
    * 时间戳 + 自增id + 回话类型 + 回话id -> 16进制

#### todo

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

* 消息发送回执
    * 已发送: sent
    * 已送达: delivered
    * 已读: read

#### netty优化

* handler复用
* 三个线程池: boss、worker、business
* 大数据拆分推送
* netty高性能三大件: 线程模型、pipeline、bytebuf
* bytebuf: 对象复用、引用计数、release

#### 帐号系统

* 注册
    * 用户名密码
    * 邮箱
    * 手机号
    * 第三方平台
* 第三方平台绑定
* http://gglinux.com/2017/03/31/user/
* https://juejin.im/post/5b8fd46f6fb9a05cdd2ce1d0
* https://juejin.im/post/5a547443f265da3e4d72923d
* https://juejin.im/post/5d0a298bf265da1b827aa06f
* https://juejin.im/post/59b2708b5188257e8a30842f
* https://juejin.im/post/5a9e4db151882555677e0dc1
* https://juejin.im/search?query=%E8%B4%A6%E6%88%B7%20%E7%94%A8%E6%88%B7&type
