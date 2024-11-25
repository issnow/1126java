package com.gc.service.impl;

import com.gc.common.CustomException;
import com.gc.entity.Category;
import com.gc.mapper.CategoryMapper;
import com.gc.service.CategoryService;
import com.gc.service.DishService;
import com.gc.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  CategoryMapper categoryMapper;
  @Autowired
  DishService dishService;
  @Autowired
  SetMealService setMealService;

  @Override
  public void save(Category category) {
    categoryMapper.save(category);
  }

  @Override
  public List<Category> page() {
    return categoryMapper.page();
  }

  @Override
  public void update(Category category) {
    categoryMapper.update(category);
  }

  //删除之前看看分类下面的菜品或套餐有没有东西
  @Override
  public void delete(Map<String, String> map) {
    //查看当前分类是否关联了菜品，如果已经关联，则抛出异常
    String id = map.get("id");
    Long i = Long.valueOf(id);
    int count = dishService.count(i);
    if (count > 0) {
      //已关联菜品，抛出一个业务异常
      throw new CustomException("当前分类下关联了菜品，不能删除");
    }
    //查看当前分类是否关联了套餐，如果已经关联，则抛出异常
    int count2 = setMealService.count(i);
    if (count2 > 0) {
      //已关联套餐，抛出一个业务异常
      throw new CustomException("当前分类下关联了套餐，不能删除");
    }
    //正常删除
    categoryMapper.delete(i);
  }

  @Override
  public List<Category> list(Integer type) {
    return categoryMapper.list(type);
  }

  @Override
  public Category getById(Long id) {
    return categoryMapper.getById(id);
  }
}
