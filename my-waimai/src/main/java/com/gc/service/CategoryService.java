package com.gc.service;

import com.gc.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
  void save(Category category);
  List<Category> page();
  void update(Category category);
  void delete(Map<String,String> map);
  List<Category> list(Integer type);
  Category getById(Long id);
}
