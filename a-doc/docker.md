### 命令 
1. systemctl start docker  
    service docker start  启动docker服务
2. docker top  查看容器内部的进程信息  
3. docker stats  查看docker的内存使用情况
### 镜像命令
1. docker images  列出所有镜像
2. docker images -aq  列出所有镜像id
3. docker images -f=?  根据条件查询镜像
4. docker search  从docker hub上查询镜像
5. docker pull  从docker hub上下载镜像到本地
6. docker rmi  从本地删除镜像
7. docker rmi -f $(docker images -aq)  强制删除本地所有镜像  
    docker images -aq | xargs docker rmi -f  强制删除本地所有镜像
8. docker commit  容器id  新的镜像名:版本号  使用已有容器制作新的镜像
    -m='提交信息'
    -a='提交人'
### 容器命令
1. docker run   根据镜像启动容器
    - docker run -d nginx 后台服务启动nginx
    - docker run -it nginx /bin/bash 使用bash交互模式启动并进入容器内部 退出时容器即停止
    - docker run -p 8181:90 nginx 将服务器8181端口和容器80端口绑定 可以通过访问服务器的8181端口访问到容器内80端口提供的服务
    - docker run --name=nginx01 nginx 指定别名 往后可以使用别名代替容器id
    - docker start 容器id 启动容器
    - docker restart 容器id 重启容器
    - docker stop 容器id 停止正在运行的容器
    - docker kill 容器id 停止正在运行的容器
    - docker inspect 容器id 查看容器内部元数据
    - docker run -v 服务器目录:容器目录 nginx 启动时挂载服务器目录
        - 匿名挂载 docker run -v 容器内目录(在/var/lib/docker/volume目录下创建匿名目录)
        - 具名挂载 docker run -v 数据卷名:容器内目录(在/var/lib/docker/volume目录下创建数据卷名目录)
        - 指定目录挂载 docker run -v 服务器目录:容器内目录
        - 设定目录权限 docker run -v 服务器目录:容器内目录:[ro|rw] ro(容器对磁盘只读) rw读写(容器对磁盘可读可写)
2. docker ps 查看正在运行的容器
    - docker ps -a 查看所有容器包括运行过的容器
    - docker ps -l 查看最近运行的的容器
    - docker ps -q 只显示容器id
    - docker ps -n=?  指定显示个数
    - docker ps -f=?  按条件显示
    - docker exec -it 容器id /bin/bash   使用bash进入正在运行的容器(使用exit退出 ctl+p+q不停止容器退出)
    - docker attach 容器id     使用上次运行容器时的指令进入容器
    - docker inspect 容器id    查看容器的元数据
3. docker rm   根据id删除容器
    - docker rm -f 容器id  强制删除
    - docker rm -f $(docker ps -q)  强制删除全部在运行的容器
    - docker rm -f $(docker ps -aq)  强制删除全部容器
    - docker ps -aq | xargs docker rm -f  强制删除全部容器
4. docker logs  查看容器内的日志
    - docker logs -f 容器id 日志持续后缀显示
    - docker logs -t 容器id 显示时间戳
    - docker logs --tail=?  容器id 打开时默认显示日志条数
    - docker attach  进入容器内部(不开启新的终端)
    - docker cp  复制文件到宿主机(容器不必在运行状态但容器必须存在)
    - docker cp  容器id:容器内的文件路径 宿主机的路径
### Dockerfile
1. 作用:创建自定义镜像
2. 脚本内的每一行命令都是构建镜像的一层 从上到下逐层累加
3. 指令(所有指令均大写)
    - FROM               加载基础镜像
    - MAINTAINER         镜像作者
    - RUN                根据该镜像运行容器时需要执行的命令
    - CMD                容器的启动命令 替换模式只会执行最后一个命令
    - ENTRYPOINT         容器的启动命令 追加模式只会执行最后一个命令
    - ENV                容器的环境变量
    - ADD                复制文件到镜像中 如果是压缩包会自动解压
    - COPY               复制文件到镜像中
    - WORKDIR            容器的工作目录默认/
    - VOLUME             数据卷挂载
    - EXPOSE             同端口映射
    - ARG                编译镜像时加入的参数
    - USER               运行命令时的用户
    - ONBUILD            有容器继承时触发
    - STOPSIGNAL         退出时触发
    - LABEL              设置标签
3. ```shell
    # 基础镜像
    FROM centos
    # 目录挂载   
    VOLUME ["v1", "v2"]
    # 输出命令
    CMD  echo '---end---'
    # 使用该镜像运行容器时使用的脚本解释器
    CMD  /bin/bash
    ```
4. 构建镜像
    - docker build -f 文件 -t 名字:标签 文件路径
    - 默认使用指定文件目录下的Dockerfile