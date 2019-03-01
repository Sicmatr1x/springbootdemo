package com.example.springbootdemo.controller;

import com.example.springbootdemo.beans.LearnResouce;
import com.example.springbootdemo.service.LearnResourceService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/learn")
public class LearnResourceController {

  /**
   * http://localhost:9090/learn/
   * @return
   */
  @RequestMapping("/")
  public ModelAndView index(){
    List<LearnResouce> learnList =new ArrayList<LearnResouce>();
    LearnResouce bean =new LearnResouce("官方参考文档","Spring Boot Reference Guide","http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#getting-started-first-application");
    learnList.add(bean);
    bean = new LearnResouce("官方SpriongBoot例子","官方SpriongBoot例子","https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples");
    learnList.add(bean);
    bean = new LearnResouce("龙国学院","Spring Boot 教程系列学习","http://www.roncoo.com/article/detail/125488");
    learnList.add(bean);
    bean = new LearnResouce("嘟嘟MD独立博客","Spring Boot干货系列 ","http://tengj.top/");
    learnList.add(bean);
    bean = new LearnResouce("后端编程嘟","Spring Boot教程和视频 ","http://www.toutiao.com/m1559096720023553/");
    learnList.add(bean);
    bean = new LearnResouce("程序猿DD","Spring Boot系列","http://www.roncoo.com/article/detail/125488");
    learnList.add(bean);
    bean = new LearnResouce("纯洁的微笑","Sping Boot系列文章","http://www.ityouknow.com/spring-boot");
    learnList.add(bean);
    bean = new LearnResouce("CSDN——小当博客专栏","Sping Boot学习","http://blog.csdn.net/column/details/spring-boot.html");
    learnList.add(bean);
    bean = new LearnResouce("梁桂钊的博客","Spring Boot 揭秘与实战","http://blog.csdn.net/column/details/spring-boot.html");
    learnList.add(bean);
    bean = new LearnResouce("林祥纤博客系列","从零开始学Spring Boot ","http://412887952-qq-com.iteye.com/category/356333");
    learnList.add(bean);
    ModelAndView modelAndView = new ModelAndView("/index");
    modelAndView.addObject("learnList", learnList);
    return modelAndView;
  }

  @Autowired
  private LearnResourceService learnService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @GetMapping(value = "/resource/{id}")
  public LearnResouce getLearnById(@PathVariable("id") Long id, HttpServletResponse response){
    LearnResouce learnResouce = learnService.queryLearnResouceById(id);
    System.out.println(id + ":" + learnResouce);
    return learnResouce;
  }

  /**
   * 新添教程
   * @param request
   * @param response
   */
  @PostMapping(value = "/add")
  public LearnResouce addLearn(HttpServletRequest request, HttpServletResponse response){
    String author = request.getParameter("author");
    String title = request.getParameter("title");
    String url = request.getParameter("url");
    LearnResouce learnResouce = new LearnResouce();
    learnResouce.setAuthor(author);
    learnResouce.setTitle(title);
    learnResouce.setUrl(url);
    int index=learnService.add(learnResouce);
    if(index>0){
     return learnResouce;
    }else{
      response.setStatus(500);
      return null;
    }
  }
  /**
   * 修改教程
   * @param request
   * @param response
   */
  @PostMapping(value = "/update/{id}")
  public LearnResouce updateLearn(@PathVariable("id") Long id, HttpServletRequest request , HttpServletResponse response){
    LearnResouce learnResouce = learnService.queryLearnResouceById(Long.valueOf(id));
    String author = request.getParameter("author");
    String title = request.getParameter("title");
    String url = request.getParameter("url");
    learnResouce.setAuthor(author);
    learnResouce.setTitle(title);
    learnResouce.setUrl(url);
    int index = learnService.update(learnResouce);
    System.out.println("修改结果=" + index);
    if(index>0){
      return learnResouce;
    }else{
      response.setStatus(500);
      return null;
    }
  }
  /**
   * 删除教程
   * @param request
   */
  @PostMapping(value="/delete/{id}")
  public String deleteUser(@PathVariable("id") String ids, HttpServletRequest request){
    //删除操作
    int index = learnService.deleteByIds(ids.split(","));
    if(index>0){
      return "success";
    }else{
      return "fail";
    }
  }
}
