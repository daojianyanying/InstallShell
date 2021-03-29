#!/bin/bash
##################################################
#
#Auther:liuxiang
#CreateDate:2021.3.29
#Description:安装Docker
#UpdateDate:2021.3.29
#
##################################################
set -ex
set -o pipeline

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

#设置docker源
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

