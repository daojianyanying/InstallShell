#!/bin/bash
##################################################
#
#Auther:liuxiang
#CreateDate:2021.2.15
#Description:安装特定版本的jdk1.8
#UpdateDate:2021.2.15
#
##################################################

#业务逻辑开始
#下载jdk1.8(镜像网址 https://mirrors.tuna.tsinghua.edu.cn/)
mkdir /opt/software/jdk/
wget -P /opt/software/jdk/  https://mirrors.tuna.tsinghua.edu.cn/AdoptOpenJDK/8/jdk/x64/linux/OpenJDK8U-jdk_x64_linux_hotspot_8u282b08.tar.gz

#解压软件到对应的目录
mkdir /usr/local/jdk/
tar -zxvf /opt/software/jdk/OpenJDK8U-jdk_x64_linux_hotspot_8u282b08.tar.gz -C /usr/local/jdk/

#配置jdk命令
cd /usr/local/jdk/jdk*/bin/
echo "export PATH=$PATH:/usr/local/jdk/jdk8u282-b08/bin" >> /etc/profile
