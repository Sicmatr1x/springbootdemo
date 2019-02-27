package com.example.springbootdemo.controller;

import com.example.springbootdemo.beans.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  ConfigBean configBean;

  @RequestMapping("/")
  public String index(){
    return " " + configBean.getName() + " " + configBean.getWant();
  }
}