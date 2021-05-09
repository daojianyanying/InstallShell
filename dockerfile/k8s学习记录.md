# k8s学习记录

### 一、 k8s的概述和特性

##### 概述：

​	谷歌开源的，用来搞容器管理(docker)，是一个容器化的管理技术。k8s是为了容器化部署的高效。

##### 特性：

- 自动装箱(就是自动化部署)
- 自我修复()
- 水平扩展()
- 负载均衡
- 滚动更新、
- 版本回退
- 密钥和配置管理
- 存储编排
- 批处理

##### k8s的架构组件：

![](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2018.cnblogs.com%2Fblog%2F1219190%2F201909%2F1219190-20190925201151257-1129093821.jpg&refer=http%3A%2F%2Fimg2018.cnblogs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623121150&t=e9ae8f14f4ddbb0b1ccb68a6e3bd9083)

1. Master(主控节点)
   - API Server—— 集群的统一入口，以restful方式，交给etcd存储
   - scheduler——节点调度，选择对应的node节点应用部署
   - controller-manager——处理集群中的常用后台任务，一个资源对应一个控制器
   - etcd——用于保存集群中的所有数据
2. Node(工作节点)
   - kubelet——master派到node节点代表，管理本机
   - kube-proxy——网络代理，用它实现负载均衡等操作
   - docker——容器

##### k8s核心概念：

1. Pod

   k8s中最小的部署单元，一个pod是一组容器的集合，共享网络，生命周期时短暂的

2. Controller

   确保预期的pod的副本数量，无状态的应用部署(随便用)和有状态的应用部署(有特定的条件才能用)。确保所有node，运行同一个pod，一次性任务和定时任务

3. Service

   定义一组pod的访问规则

### 二、k8s的搭建

##### 	2.1.  kubeadm搭建

- 创建一个Master节点  kubeadm init

##### 	2.2.  二进制方式

