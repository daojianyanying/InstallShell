#!/bin/bash
##################################################
#
#Auther:liuxiang
#CreateDate:2021.3.29
#Description:docker安装配置gitlab
#UpdateDate:2021.3.29
#
###################################################
#配置脚本执行策略
set -ex 
set -o pipefail

#安装RZ命令，用以传输文件
yum -y install lrzsz
#安装wget命令
yum -y install wget