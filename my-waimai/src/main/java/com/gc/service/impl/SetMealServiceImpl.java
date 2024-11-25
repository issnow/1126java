package com.gc.service.impl;

import com.gc.mapper.SetMealMapper;
import com.gc.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {
  @Autowired
  SetMealMapper setMealMapper;
  @Override
  public int count(Long categoryId) {
    return setMealMapper.count(categoryId);
  }
}
