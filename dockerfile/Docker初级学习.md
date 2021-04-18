# Docker初级学习

docker官网(登陆需要翻墙):	https://www.docker.com/

#### 零、 Docker的安装——linux

docker和linxu系统的版本匹配的问题自己需要关注下

```shell
#!/bin/bash
##################################################
#
#Auther:
#CreateDate:2021.3.29
#Description:安装Docker
#UpdateDate:2021.3.29
#
##################################################
set -ex
set -o pipefail

echo "########start install docker########"

echo "########uninstall prior docker software####"
sudo yum remove docker \
				docker-client \
				docker-client-latest \
				docker-common \
				docker-latest \
				docker-latest-logrotate \
				docker-logrotate \
				docker-engine
#安装必须的工具
sudo yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2

#设置docker源--阿里源
sudo yum-config-manager \
			--add-repo \
			http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

#安装Docker
sudo yum install docker-ce docker-ce-cli containerd.io

echo "######启动docker#######"
sudo systemctl start docker

echo "##设置aliyun的镜像加速#####"
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://ck9ffdmu.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker

#安装docker compose
curl -L https://get.daocloud.io/docker/compose/releases/download/1.25.5/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose && cd /usr/local/bin/ && chmod +x docker-compose
docker-compose version
```





#### 一、 Docker的概念

##### 	1.1 概念

![](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fseo-1255598498.file.myqcloud.com%2Ffull%2F66ec0965a96e9a6d2d1c191ebcc8807ba71baa93.jpg&refer=http%3A%2F%2Fseo-1255598498.file.myqcloud.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620994866&t=9507932691ad2c43bd7df0a8064477c0)

1. 容器：(Docker Containers) 容器是独立运行的一个或一组应用，是镜像运行时的实体。镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的类和实例一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。
2. 镜像：Docker 镜像（Images），就相当于是一个 root 文件系统,Docker 镜像是用于创建 Docker 容器的模板
3. 仓库：(Registry) 仓库可看成一个代码控制中心，用来保存镜像。仓库分为公有仓库（Docker hub、阿里云镜像仓），私有仓的话，可以自己去阿里云上申请。
4. 客户端：(Docker Client) docker 客户端是用来通过命令与docker的守护进程通信(图中的Docker daemon)。
5. 主机：(Docker_HOST) docker主机个物理或者虚拟的机器用于执行 Docker 守护进程和容器。(自己安装时，使用的执行机既是客户端也是主机)

##### 1.2 Hello docker

​	Docker安装完成后，执行Docker run hello-world命令

```shell
[root@devops /]# docker run hello-world
Unable to find image 'hello-world:latest' locally
latest: Pulling from library/hello-world
b8dfde127a29: Pull complete 
Digest: sha256:f2266cbfc127c960fd30e76b7c792dc23b588c0db76233517e1891a4e357d519
Status: Downloaded newer image for hello-world:latest
WARNING: IPv4 forwarding is disabled. Networking will not work.

Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/get-started/

[root@devops /]# 

```

执行过程：首先docker会检查本地仓库是否存在对应的镜像(docker images 可以查看本地镜像仓库)。如果本地有立即运行，本地没有则会从远程镜像仓库pull，完成后会显示pull complete,本地会下载好hello-world镜像，下载完后，则运行镜像。



#### 二、 Docker的基础命令

##### 2.1  镜像命令——docker images

- ​	获取/推送一个镜像  (镜像信息的查看地址：https://hub.docker.com/   请翻墙)

​	**docker pull 镜像名：镜像tag**

**docker push 镜像名：镜像tag**

```shell
[root@localhost ~]# docker pull nginx:1.19.10
1.19.10: Pulling from library/nginx
f7ec5a41d630: Pull complete 
aa1efa14b3bf: Pull complete 
b78b95af9b17: Pull complete 
c7d6bca2b8dc: Pull complete 
cf16cd8e71e0: Pull complete 
0241c68333ef: Pull complete 
Digest: sha256:75a55d33ecc73c2a242450a9f1cc858499d468f077ea942867e662c247b5e412
Status: Downloaded newer image for nginx:1.19.10
docker.io/library/nginx:1.19.10
[root@localhost ~]# 
```

- 删除镜像 

  **docker rmi 镜像名：镜像tag 											//删除一个镜像**

  **docker rmi $(docker  images  -qa) 								//删除所有镜像**

  ```shell
  [root@localhost ~]# docker rmi  nginx:1.19.10
  Untagged: nginx:1.19.10
  Untagged: nginx@sha256:75a55d33ecc73c2a242450a9f1cc858499d468f077ea942867e662c247b5e412
  Deleted: sha256:62d49f9bab67f7c70ac3395855bf01389eb3175b374e621f6f191bf31b54cd5b
  Deleted: sha256:3444fb58dc9e8338f6da71c1040e8ff532f25fab497312f95dcee0f756788a84
  Deleted: sha256:f85cfdc7ca97d8856cd4fa916053084e2e31c7e53ed169577cef5cb1b8169ccb
  Deleted: sha256:704bf100d7f16255a2bc92e925f7007eef0bd3947af4b860a38aaffc3f992eae
  Deleted: sha256:d5955c2e658d1432abb023d7d6d1128b0aa12481b976de7cbde4c7a31310f29b
  Deleted: sha256:11126fda59f7f4bf9bf08b9d24c9ea45a1194f3d61ae2a96af744c97eae71cbf
  Deleted: sha256:7e718b9c0c8c2e6420fe9c4d1d551088e314fe923dce4b2caf75891d82fb227d
  [root@localhost ~]# docker images
  REPOSITORY                                                     TAG           IMAGE ID       CREATED        SIZE
  gitlab/gitlab-ce                                               13.8.7-ce.0   2f9d96c60f66   2 weeks ago    2.17GB
  centos-java                                                    2.0.0         5d9e681d25d6   2 weeks ago    869MB
  ```

  

- 查看本地镜像

  **docker images  																	//查看所有镜像**

  ```shell
  [root@localhost ~]# docker images
  REPOSITORY                                                     TAG           IMAGE ID       CREATED        SIZE
  gitlab/gitlab-ce                                               13.8.7-ce.0   2f9d96c60f66   2 weeks ago    2.17GB
  centos-java                                                    2.0.0         5d9e681d25d6   2 weeks ago    869MB
  registry.cn-hangzhou.aliyuncs.com/daojianyanying/centos-java   2.0.0         5d9e681d25d6   2 weeks ago    869MB
  registry.cn-hangzhou.aliyuncs.com/daojianyanying/centos-java   1.0.0         97b82b28a5d8   2 weeks ago    869MB
  tos-self                                                    01            58360ee57708   2 weeks ago    
  [root@localhost ~]# 
  
  ```



- 设置镜像标签

  **docker tag  镜像id  新镜像名：新镜像tag**

  **docker tag  旧镜像名：旧镜像tag  新镜像名：新镜像tag**

  ```shell
  [root@localhost ~]# docker tag tomcat:8.5 tomcat-latest:latest
  [root@localhost ~]# docker images
  REPOSITORY                                                     TAG           IMAGE ID        SIZE
  gitlab/gitlab-ce                                               13.8.7-ce.0   2f9d96c60f66    2.17GB
  registry.cn-hangzhou.aliyuncs.com/daojianyanying/centos-java   2.0.0         5d9e681d25d6    869MB
  centos-java                                                    2.0.0         5d9e681d25d6    869MB
  daojianyanying/centos-java                                     1.0.0         97b82b28a5d8    869MB
  centos-java                                                    1.0.0         97b82b28a5d8    869MB
  registry.cn-hangzhou.aliyuncs.com/daojianyanying/centos-java   1.0.0         97b82b28a5d8    869MB
  centos-self                                                    02            823fb69d2936    337MB
  centos-self                                                    01            58360ee57708    270MB
  tomcat-8.5-wwebapp                                             withapp       603b2021c94d    537MB
  mysql                                                          5.7           a70d36bc331a    449MB
  tomcat-latest                                                  latest        37bdd9cb0d0e    533MB
  tomcat                                                         8.5           37bdd9cb0d0e    533MB
  tomcat                                                         latest        040bdb29ab37    649MB
  ```

  

- 导入和导出镜像

  导出镜像到文件夹中    **docker save -o 导出文件名 镜像名：镜像tag  ======  docker save 镜像名：镜像tag>导出文件名**

  ```shell
  [root@localhost dockerfile]# docker save -o nginx.tar nginx:1.18.0 
  [root@localhost dockerfile]# ls
  Dockerfile  nginx.tar
  [root@localhost dockerfile]# docker save nginx:1.18.0 > nginx.tar.2
  [root@localhost dockerfile]# ls
  Dockerfile  nginx.tar  nginx.tar.2
  [root@localhost dockerfile]#
  ```

  导入镜像  					**docker load -i save的镜像名**

  ```shell
  [root@localhost dockerfile]# docker load -i nginx.tar
  Loaded image: nginx:1.18.0
  
  ```

  





- 