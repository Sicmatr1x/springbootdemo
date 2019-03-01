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

```
com.example.name=Sicmatr1x
```

然后直接在要使用的地方通过注解@Value(value=”${config.name}”)就可以绑定到你想要的属性上面:

```java
  @Value("${com.example.name}")
  private  String name;

  @RequestMapping("/")
  public String index(){
    return name + ":Hello Spring Boot";
  }
```

或者使用自定义属性:

建一个ConfigBean.java类，顶部需要使用注解@ConfigurationProperties(prefix = “com.dudu”)来指明使用哪个

```java
@Configuration
@ConfigurationProperties(prefix = "com.md") 
@PropertySource("classpath:test.properties")
public class ConfigTestBean {
    private String name;
    private String want;
    // 省略getter和setter
}
```

还需要在spring Boot入口类加上@EnableConfigurationProperties并指明要加载哪个bean，如果不写ConfigBean.class，在bean类那边添加

```java
@RestController
@EnableConfigurationProperties({ConfigBean.class})
@SpringBootApplication
public class SpringbootdemoApplication {
    //...
}
```

最后在Controller中引入ConfigBean使用即可:

```java
@RestController
public class UserController {

    @Value("${com.example.name}")
    private  String name;
    @Value("${com.example.want}")
    private  String want;

    @RequestMapping("/")
    public String index(){
        return name+","+want;
    }
}
```

## 使用 Thymeleaf 模板引擎

Spring Boot支持多种模版引擎包括：

1. FreeMarker
2. Groovy
3. Thymeleaf(官方推荐)
4. Mustache

Thymeleaf是一款用于渲染XML/XHTML/HTML5内容的模板引擎。类似JSP，Velocity，FreeMaker等，它也可以轻易的与Spring MVC等Web框架进行集成作为Web应用的模板引擎。与其它模板引擎相比，Thymeleaf最大的特点是能够直接在浏览器中打开并正确显示模板页面，而不需要启动整个Web应用。它的功能特性如下：

* Spring MVC中@Controller中的方法可以直接返回模板名称，接下来Thymeleaf模板引擎会自动进行渲染
* 模板中的表达式支持Spring表达式语言（Spring EL)
* 表单支持，并兼容Spring MVC的数据绑定与验证机制
* 国际化支持

### 引入依赖

spring-boot-starter-thymeleaf会自动包含spring-boot-starter-web，所以我们就不需要单独引入web依赖了

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 编写controller

```java
@RestController
@RequestMapping("/learn")
public class LearnResourceController {
    @RequestMapping("/")
    public ModelAndView index(){
        List<LearnResouce> learnList =new ArrayList<LearnResouce>();
        LearnResouce bean =new LearnResouce("官方参考文档","Spring Boot Reference Guide","http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#getting-started-first-application");
        learnList.add(bean);
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("learnList", learnList);
        return modelAndView;
    }
}
```

### 编写 html 模板文件

通过xmlns:th="http://www.thymeleaf.org" 命令空间，将静态页面转换为动态的视图，需要进行动态处理的元素将使用"th:"前缀。

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <title>learn Resources</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

<div style="text-align: center;margin:0 auto;width: 1000px; ">
  <h1>学习资源大奉送</h1>
  <table width="100%" border="1" cellspacing="1" cellpadding="0">
    <tr>
      <td>作者</td>
      <td>教程名称</td>
      <td>地址</td>
    </tr>
    <!--/*@thymesVar id="learnList" type=""*/-->
    <tr th:each="learn : ${learnList}">
      <!--/*@thymesVar id="author" type=""*/-->
      <td th:text="${learn.author}"></td>
      <td th:text="${learn.title}"></td>
      <td><a th:href="${learn.url}" target="_blank">点我</a></td>
    </tr>
  </table>
</div>
</body>
</html>
```

LearnResourceController 对应访问:http://localhost:9090/learn/



### 附录:Thymeleaf的默认参数配置

```
 THYMELEAF (ThymeleafAutoConfiguration)
#开启模板缓存（默认值：true）
spring.thymeleaf.cache=true 
#Check that the template exists before rendering it.
spring.thymeleaf.check-template=true 
#检查模板位置是否正确（默认值:true）
spring.thymeleaf.check-template-location=true
#Content-Type的值（默认值：text/html）
spring.thymeleaf.content-type=text/html
#开启MVC Thymeleaf视图解析（默认值：true）
spring.thymeleaf.enabled=true
#模板编码
spring.thymeleaf.encoding=UTF-8
#要被排除在解析之外的视图名称列表，用逗号分隔
spring.thymeleaf.excluded-view-names=
#要运用于模板之上的模板模式。另见StandardTemplate-ModeHandlers(默认值：HTML5)
spring.thymeleaf.mode=HTML5
#在构建URL时添加到视图名称前的前缀（默认值：classpath:/templates/）
spring.thymeleaf.prefix=classpath:/templates/
#在构建URL时添加到视图名称后的后缀（默认值：.html）
spring.thymeleaf.suffix=.html
#Thymeleaf模板解析器在解析器链中的顺序。默认情况下，它排第一位。顺序从1开始，只有在定义了额外的TemplateResolver Bean时才需要设置这个属性。
spring.thymeleaf.template-resolver-order=
#可解析的视图名称列表，用逗号分隔
spring.thymeleaf.view-names=
```

## 静态资源

### 默认资源映射

其中默认配置的 /** 映射到 /static （或/public、/resources、/META-INF/resources）

优先级顺序为：META-INF/resources > resources > static > public

注意:META-INF, resources, static, public 需放在 src/main/resources目录下

## 默认日志 logback配置

### maven依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

实际开发中我们不需要直接添加该依赖，你会发现spring-boot-starter其中包含了 spring-boot-starter-logging，该依赖内容就是 Spring Boot 默认的日志框架 logback。而博主这次项目的例子是基于上一篇的，工程中有用到了Thymeleaf，而Thymeleaf依赖包含了spring-boot-starter，最终我只要引入Thymeleaf即可

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 控制台输出

日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出。

启动您的应用程序–debug标志来启用“调试”模式, 以下两种方式皆可:
1. 在运行命令后加入--debug标志，如：$ java -jar springTest.jar --debug
2. 在 application.properties中配置 ```debug=true```，该属性置为true的时候，核心 Logger（包含嵌入式容器、hibernate、spring）会输出更多内容，但是你自己应用的日志并不会输出为DEBUG级别。

### 文件输出

控制台输出之外的日志文件，则需在 application.properties中设置以下两个中的一个:
1. logging.file，设置文件，可以是绝对路径，也可以是相对路径。如：```logging.file=my.log```
2. logging.path，设置目录，会在该目录下创建spring.log文件，并写入日志内容，如：```logging.path=/var/log```

默认情况下，日志文件的大小达到 10MB 时会切分一次，产生新的日志文件，默认级别为：ERROR、WARN、INFO

### 级别控制

所有支持的日志记录系统都可以在 Spring环境中设置记录级别:

application.properties:

```
logging.level.com.example=DEBUG
logging.level.root=WARN
```

---

## 集成 MyBatis

### 添加 maven依赖

```xml
<dependency>
     <groupId>org.mybatis.spring.boot</groupId>
     <artifactId>mybatis-spring-boot-starter</artifactId>
     <version>1.3.0</version>
 </dependency>

<dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
 </dependency>
```

### 配置数据源

src/main/resources/application.properties:

```
spring.datasource.url = jdbc:mysql://localhost:3306/spring?useUnicode=true&characterEncoding=utf-8
spring.datasource.username = root
spring.datasource.password = Tuesday2
spring.datasource.driver-class-name = com.mysql.jdbc.Driver
```

### 添加数据库

```sql
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spring` /*!40100 DEFAULT CHARACTER SET utf8 */;
 USE `spring`;
 DROP TABLE IF EXISTS `learn_resource`;

CREATE TABLE `learn_resource` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `author` varchar(20) DEFAULT NULL COMMENT '作者',
   `title` varchar(100) DEFAULT NULL COMMENT '描述',
   `url` varchar(100) DEFAULT NULL COMMENT '地址链接',
   PRIMARY KEY (`id`)
 ) ENGINE=MyISAM AUTO_INCREMENT=1029 DEFAULT CHARSET=utf8;

insert into `learn_resource`(`id`,`author`,`title`,`url`) values (999,'官方SpriongBoot例子','官方SpriongBoot例子','https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples');
 insert into `learn_resource`(`id`,`author`,`title`,`url`) values (1000,'龙果学院','Spring Boot 教程系列学习','http://www.roncoo.com/article/detail/124661');
 insert into `learn_resource`(`id`,`author`,`title`,`url`) values (1001,'嘟嘟MD独立博客','Spring Boot干货系列','http://tengj.top/');
 insert into `learn_resource`(`id`,`author`,`title`,`url`) values (1002,'后端编程嘟','Spring Boot视频教程','http://www.toutiao.com/m1559096720023553/');

```
