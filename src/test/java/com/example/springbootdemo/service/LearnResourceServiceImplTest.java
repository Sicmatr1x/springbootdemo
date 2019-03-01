package com.example.springbootdemo.service;

import com.example.springbootdemo.beans.LearnResouce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnResourceServiceImplTest {

  @Autowired
  private LearnResourceService learnResourceService;

  @Test
  public void queryLearnResourceById(){
    LearnResouce learnResouce = learnResourceService.queryLearnResouceById(999l);
    System.out.println(learnResouce);
  }
}
