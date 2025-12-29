#!/bin/bash

# Docker镜像构建和推送脚本
# 适用于已经写好的Dockerfile

# 配置
VERSION="v0.0.1"
PREFIX="registry.cn-hangzhou.aliyuncs.com/zhbpublic/"
USERNAME="回家急急急"
PASSWORD="@2914718834LJY"

# 只需要指定Dockerfile所在的目录
ADMIN_DOCKERFILE_DIR="like-admin"    # 包含Dockerfile的目录
FRONT_DOCKERFILE_DIR="like-front"    # 包含Dockerfile的目录

echo "登录Docker仓库..."
docker login --username="$USERNAME" --password="$PASSWORD" registry.cn-hangzhou.aliyuncs.com

# 构建函数
build_image() {
    local app_name=$1
    local dockerfile_dir=$2
    local image_name="${PREFIX}${app_name}:${VERSION}"
    local current_dir=$(pwd)  # 保存当前目录

    echo "构建 $app_name..."

    # 检查目录是否存在
    if [ ! -d "$dockerfile_dir" ]; then
        echo "❌ 目录不存在: $dockerfile_dir"
        return 1
    fi

    # 检查Dockerfile是否存在
    if [ ! -f "$dockerfile_dir/Dockerfile" ]; then
        echo "❌ 找不到 Dockerfile 在: $dockerfile_dir"
        return 1
    fi

    # 进入目录并构建
    cd "$dockerfile_dir"
    echo "当前目录: $(pwd)"
    echo "Dockerfile内容:"
    cat Dockerfile
    echo ""

    # 构建镜像
    docker build --rm=true -t "$image_name" .

    # 推送镜像
    docker push "$image_name"

    # 删除本地镜像（可选）
    docker rmi "$image_name"

    # 返回原目录
    cd "$current_dir"

    echo "✅ $app_name 完成"
}

# 构建两个应用
build_image "like-admin" "$ADMIN_DOCKERFILE_DIR"
build_image "like-front" "$FRONT_DOCKERFILE_DIR"

echo "所有镜像已推送到仓库！"

#docker run -d   --name like-admin   -p 8086:8086  registry.cn-hangzhou.aliyuncs.com/zhbpublic/like-admin:v0.0.1
#docker run -d   --name like-front   -p 8088:8088  registry.cn-hangzhou.aliyuncs.com/zhbpublic/like-front:v0.0.1