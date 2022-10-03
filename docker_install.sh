#!/bin/sh
#安装docker,docker-compose脚本
docker_install_info=`docker -v|grep -o version`
docker_install_version="19.03.9"
docker_compose_install_version="1.27.3"
#如果安装目录不是/usr/local/bin/ 需要将安装目录链接到/usr/local/bin目录下
docker_compose_install_dir="/usr/local/bin/docker-compose"
echo $docker_install_info
if [ $"$docker_install_info" ]
then
        echo "docker 已经安装,请先确认环境"
        exit
else
        echo "docker未安装,准备开始安装docker"
fi
echo "开始配置docker环境,第一步安装yum-utils"
sudo yum install -y yum-utils
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
echo "安装docker引擎"
sudo yum install docker-ce docker-ce-cli containerd.io -y
docker_install_success=`docker -v|grep -o version`
if [ $"$docker_install_success" ]
then
        echo "docker已经成功安装"
else
        echo "docker未安装成功,请检查执行过程"
        exit
fi
 
echo "更改docker国内镜像站"
mkdir -p /etc/docker
touch /etc/docker/daemon.json
echo "{
  \"registry-mirrors\": [
    \"https://registry.docker-cn.com\",
    \"http://hub-mirror.c.163.com\",
    \"https://docker.mirrors.ustc.edu.cn\"
  ]
}" >>/etc/docker/daemon.json

sudo systemctl enable docker
sudo systemctl start docker
 
echo "开始安装docker-compose(国内源)"
sudo curl -L https://get.daocloud.io/docker/compose/releases/download/$docker_compose_install_version/docker-compose-`uname -s`-`uname -m` > $docker_compose_install_dir
default_install_dir="/usr/local/bin/docker-compose"
if [ "$docker_compose_install_dir" != "$default_install_dir" ]
then
        echo"安装目录非默认目录"
        sudo ln -s $docker_compose_install_dir $default_install_dir
fi
sudo chmod +x $docker_compose_install_dir
 
docker_compose_install_success=`docker-compose -v|grep -o version`
if [ $"$docker_compose_install_success" ]
then
        echo "docker-compose已经成功安装"
else
        echo "docker-compose未安装成功,请检查执行过程"
        exit
fi