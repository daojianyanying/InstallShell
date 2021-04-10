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
#定义常量量
DOCKER_DEVOP_IP="192.170.111.0/24"
#创建docker的devops的网络
docker network create --subnet ${DOCKER_DEVOP_IP} develops-1 && echo "tho network of develops-1 is ${DOCKER_DEVOP_IP}"

#开始业务逻辑
mkdir -p /home/gitlab/config /home/gitlab/logs /home/gitlab/data

docker pull gitlab/gitlab-ce:13.8.7-ce.0

docker run -d  -p 443:443 -p 80:80 -p 222:22 \
	--network=develops-1 \
	--name gitlab \
	--privileged=true \
	-v /home/gitlab/config:/etc/gitlab \
	-v /home/gitlab/logs:/var/log/gitlab \
	-v /home/gitlab/data:/var/opt/gitlab \
	gitlab/gitlab-ce:13.8.7-ce.0

#配置文件修改-- vi /data/gitlab/etc/gitlab.rb  external_url 'http://ip'	
#配置文件修改-- vi /data/gitlab/data/gitlab-rails/etc/gitlab.yml
#				找到关键字 * ## Web server settings *
#				将host的值改成映射的外部主机ip地址和端口，这里会显示在gitlab克隆地址
echo "
# 配置http协议所使用的访问地址,不加端口号默认为80
external_url 'http://192.168.199.231'
# 配置ssh协议所使用的访问地址和端口
gitlab_rails['gitlab_ssh_host'] = '192.168.199.231'
gitlab_rails['gitlab_shell_ssh_port'] = 222 # 此端口是run时22端口映射的222端口
"
#-p 222:22 时ssh对应的接口
#--privileged=true 配置gitlab权限，否则gitlab无法启动完成
# -d：后台运行
# -p：将容器内部端口向外映射
# --name：命名容器名称
# -v：将容器内数据文件夹或者日志、配置等文件夹挂载到宿主机指定目录