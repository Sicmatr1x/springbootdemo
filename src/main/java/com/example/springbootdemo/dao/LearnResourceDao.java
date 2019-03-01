package com.example.springbootdemo.dao;

import com.example.springbootdemo.beans.LearnResouce;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface LearnResourceDao {
  int add(LearnResouce learnResouce);
  int update(LearnResouce learnResouce);
  int deleteByIds(String[] ids);
  LearnResouce queryLearnResouceById(Long id);
  public List<LearnResouce> queryLearnResouceList(Map<String, Object> params);

}
