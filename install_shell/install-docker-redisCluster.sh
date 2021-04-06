#!/bin/bash
##################################################
#
#Auther:liuxiang
#CreateDate:2021.2.17
#Description:安装docker的集群(6个服务 主从结构)
#UpdateDate:2021.2.17
#
##################################################
#设置脚本的属性
set -x
set -o pipefail

#配置docker的网络，用来专门搞redis的集群
docker network create --subnet 172.172.0.0/16 redis-network

#创建redis的数据卷目录,redis需要添加数据卷的目录是:
