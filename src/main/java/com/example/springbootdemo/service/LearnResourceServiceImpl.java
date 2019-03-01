package com.example.springbootdemo.service;

import com.example.springbootdemo.beans.LearnResouce;
import com.example.springbootdemo.dao.LearnResourceDao;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnResourceServiceImpl implements LearnResourceService{
  @Autowired
  LearnResourceDao learnResourceDao;
  @Override
  public int add(LearnResouce learnResouce) {
    return this.learnResourceDao.add(learnResouce);
  }

  @Override
  public int update(LearnResouce learnResouce) {
    return this.learnResourceDao.update(learnResouce);
  }

  @Override
  public int deleteByIds(String[] ids) {
    return this.learnResourceDao.deleteByIds(ids);
  }

  @Override
  public LearnResouce queryLearnResouceById(Long id) {
    return this.learnResourceDao.queryLearnResouceById(id);
  }

  @Override
  public List<LearnResouce> queryLearnResouceList(Map<String,Object> params) {
//    PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
//    return this.learnMapper.queryLearnResouceList(params);
    return null;
  }
}
