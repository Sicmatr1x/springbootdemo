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