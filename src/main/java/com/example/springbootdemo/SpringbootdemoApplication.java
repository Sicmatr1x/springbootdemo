package com.example.springbootdemo;

import com.example.springbootdemo.beans.ConfigBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController注解等价于@Controller+@ResponseBody的结合，使用这个注解的类里面的方法都以json格式输出。
@RestController
@EnableConfigurationProperties({ConfigBean.class})
// @SpringBootApplication是Sprnig Boot项目的核心注解，主要目的是开启自动配置。
@SpringBootApplication
public class SpringbootdemoApplication {

//  @Value("${com.example.name}")
//  private  String name;
//
//  @RequestMapping("/")
//  public String index(){
//    return name + ":Hello Spring Boot";
//  }

  /**
   * 主要作用是作为项目启动的入口
   *
   * 启动项目有三种方式：
   * 1. SpringbootdemoApplication的main方法
   * 2. 使用命令 mvn spring-boot:run”在命令行启动该应用，IDEA中该命令在如下位置：
   * 3. 运行“mvn package”进行打包时，会打包成一个可以直接运行的 JAR 文件，使用“java -jar”命令就可以直接运行。
   *
   * http://localhost:9090
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(SpringbootdemoApplication.class, args);
  }

}
