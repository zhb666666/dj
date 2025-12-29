#!/bin/sh

# 前端项目镜像构建脚本
# 分别构建pc和admin两个项目的镜像

##版本号
version="v0.0.1"
#前缀
prefix="registry.cn-hangzhou.aliyuncs.com/zhbpublic/"

# 登录Docker仓库
docker login --username=回家急急急 --password=@2914718834LJY registry.cn-hangzhou.aliyuncs.com

function build(){
  # $1: 镜像名，$2: Dockerfile路径
  docker build --rm=true -t $prefix$1":"$version -f $2 .
  docker push $prefix$1":"$version
  docker rmi $prefix$1":"$version
}

# 构建PC端镜像
build like-pc Dockerfile-pc

# 构建Admin端镜像
build like-pc-admin Dockerfile-admin

#docker run -d --name like-pc-admin -p 6666:80 registry.cn-hangzhou.aliyuncs.com/zhbpublic/like-pc-admin:v0.0.1
#docker run -d --name like-pc -p 6667:80 registry.cn-hangzhou.aliyuncs.com/zhbpublic/like-pc:v0.0.1
