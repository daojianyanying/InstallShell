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

![](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fseo-1255598498.file.myqcloud.com%2Ffull%2F66ec0965a96e9a6d2d1c191ebcc8807ba71baa93.jpg&refer=http%3A%2F%2Fseo-1255598498.file.myqcloud.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620994866&t=9507932691ad2c43bd7df0a8064477c0)

1. 容器：
2. 镜像：
3. 仓库：

