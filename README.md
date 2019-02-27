# Spring Boot 学习项目

## 简介

Spring将很多魔法带入了Spring应用程序的开发之中，其中最重要的是以下四个核心。
    
1. 自动配置：针对很多Spring应用程序常见的应用功能，Spring Boot能自动提供相关配置
2. 起步依赖：告诉Spring Boot需要什么功能，它就能引入需要的库。
3. 命令行界面：这是Spring Boot的可选特性，借此你只需写代码就能完成完整的应用程序，无需传统项目构建。
4. Actuator：让你能够深入运行中的Spring Boot应用程序。

## 快速构建一个 Spring Boot 的 Maven项目

### 方法一:通过Web界面使用

访问：http://start.spring.io/
选择构建工具Maven Project、Spring Boot版本1.5.1以及一些工程基本信息，可参考下图所示

点击Generate Project下载项目压缩包

导入到你的工程，如果是IDEA，则需要：
1. 菜单中选择File–>New–>Project from Existing Sources...
2. 选择解压后的项目文件夹，点击OK
3. 点击Import project from external model并选择Maven，点击Next到底为止。
4. 若你的环境有多个版本的JDK，注意到选择Java SDK的时候请选择Java 7以上的版本

### 方法二:通过 IntelliJ IDEA直接创建

IntelliJ IDEA是非常流行的IDE，IntelliJ IDEA 14.1已经支持Spring Boot了。

创建Spring Boot操作步骤如下：
- 在File菜单里面选择 New > Project,然后选择Spring Initializr，接着一步步操作即可。

## 启动项目

启动项目有三种方式：

1. SpringbootdemoApplication的main方法
2. 使用命令 mvn spring-boot:run”在命令行启动该应用，IDEA中该命令在如下位置
3. 运行“mvn package”进行打包时，会打包成一个可以直接运行的 JAR 文件，使用“java -jar”命令就可以直接运行。


打开浏览器访问http://localhost:8080, 你就能看到页面显示Hello Spring Boot效果了。

可在 application.properties中配置端口:

```
server.port=9090
```

application.properties提供自定义属性的支持，这样我们就可以把一些常量配置在这里：

