package com.gc.service;

import com.gc.common.Result;
import com.gc.dto.DishDto;
import com.gc.entity.Dish;

import java.util.List;

public interface DishService {
  int count(Long categoryId);

  void save(DishDto dishDto);

  void update(DishDto dishDto);

  List<Dish> page(String name);

  Result<DishDto> getById(Long id);
}
