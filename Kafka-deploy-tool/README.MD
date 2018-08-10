# 自动生成kafka集群的部署工具


    自动部署工具可以生成kafka+zookeeper集群，并增加启动脚本，附带安装kafka-manager。<br>
    由于原始包比较大，需要自行下载后放入到product目录中，并修改成指定的名称。
    
   
### 准备工作

* 可以到网盘下载所有的安装包，这的版本是经过测试的，其它版本有可能支持，但不保证能够正确运行<br>
   下载后将安装包放到Kafka-deploy-tool/product目录下
    
    链接: https://pan.baidu.com/s/1-QFpWL7B1y9Ey0dEPKqtGA 密码: t6c4

### 使用部署包生成kafka集群


1.首先修改config.properties文件，调整生成数量以及相关端口

2.运行start_deploy_tool.cmd或者source start_deploy_tool.sh启动ant

3.根据提示运行ant指令： 例如 ant unzippro reall。

4.进入部署目录运行启动脚本即可




## 若希望使用新版本，请安以下方法自行获取

如果版本变化了，除获取新的安装包外，还需要修改config.properties中的版本号<br>
INSTALL.PRODUCT.KAFKA.VERSION=kafka_2.11-2.0.0<br>
INSTALL.PRODUCT.KAFKA-MANAGER.VERSION=kafka-manager-1.3.3.17<br>

##### 官网下载kafka安装包,

    * 到http://kafka.apache.org/downloads下载页面下载kafka版本，推荐Scala 2.11  - kafka_2.11-2.0.0.tgz

#####  编译kafka-manager安装包<br>
     在linux服务器上准备好JAVA8环境<br>
     通过 cd ~进入当前用户目录，然后通过命令mkdir .sbt创建.sbt目录，进入创建的该目录，<br>
     使用vi创建repositories文件，编辑内容如下：<br>

```

[repositories]
local
   aliyun: http://maven.aliyun.com/nexus/content/groups/public
   jcenter: https://jcenter.bintray.com/
   typesafe: http://repo.typesafe.com/typesafe/ivy-releases/, [organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext], bootOnly

maven-central
``` 

    到https://github.com/yahoo/kafka-manager/releases下载tar.gz包。推荐使用1.3.3.18<br>
    使用tar命令解压后，然后进入解压后的Kafka-manager 目录，执行下面的命令：
```
> ./sbt clean dist
```
    编译时间很长，编译结束后会在kafka-manager/target/universal目录下生成.zip产品包
    




