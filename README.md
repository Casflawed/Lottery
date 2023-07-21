# 搭建 DDD 四层架构
**概述**
lottery 架构下共分了 lottery-common、lottery-infrastructure、lottery-domain、lottery-application、lottery-interfaces 和 lottery-rpc 层

lottery-rpc 层和其他模块保持隔离，并且用于暴露接口给外部应用使用，它是其他应用使用 lottery 服务的入口。

**lottery-rpc 如何与其他应用保持隔离**

lottery-rpc 只负责定义接口，再接口的定义描述中不会存在数据表实体，因为数据表实体被定义在了 lottery-infrastructure 中，lottery-rpc 会使用 req，res，和 dto 完成对其他模块的隔离。这样外部应用再引入 lottery-rpc 的 jar 包时就不需要引入其他业务模块了。

# 跑通 RPC 服务调用
将 lottery-rpc 打包，并在 lottery-test 测试 rpc 服务是否能跑通。

**如何打 jar 包**

具有依赖关系的需要按照顺序打包，先得打包被依赖的模块；或者简单的直接从根目录开始打包，它会自动按照依赖关系进行打包并将 jar 下载到本地 maven 仓库中。

**项目中如何使用 jar 包**

【方法一】

和平时开发项目时一样，在 pom.xml 中导入依赖，前提是 jar 包被导入到本地 maven 仓库或者是远程仓库。比如 lottery-rpc 依赖 lottery-common，现在两者都已经打包并下载到本地仓库，那么在 lottery-test 中只需要在 pom.xml 中导入 lottery-rpc 的依赖即可，maven 会自动按照依赖关系将 lottery-common 也导入到项目中。

【方法二】

手动导包，这个时候就需要自己手动将 lottery-rpc 和 lottery-common 同时导入到项目了，如何在 IDEA 中手动导入 jar 包，参考：https://blog.csdn.net/qq_48299903/article/details/120231242

**dubbo 的直连和多播模式**

【直连模式】

直连模式和广播模式的 dubbo 配置：

```yml
# 服务提供方
dubbo:
  application:
    name: Lottery
    version: 1.0.0
  registry:
    address: N/A    # 直连
#    address: multicast://224.5.6.7:1234，多播地址
  protocol:
    name: dubbo
    port: 20880
  scan:
    base-packages: com.flameking.lottery.interfaces


# 服务消费方
dubbo:
  application:
    name: Lottery-test
    version: 1.0.0
  registry:
#    address: multicast://224.5.6.7:1234
    address: N/A
  protocol:
    name: dubbo
    port: 20880
```

服务提供方和服务消费方的 dubbo 配置大体是一致的。在本地测得时候无论是多播或者是直连，都需要指明连接得服务器地址：

```java
// 必须指明 url
@Reference(interfaceClass = IActivityBooth.class, url = "dubbo://127.0.0.1:20880")
IActivityBooth activityBooth;
```

# 抽奖策略领域模块开发

**基于充血模型的DDD领域驱动设计相比基于贫血模型的MVC三层架构有何优点**


