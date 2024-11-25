package com.gc.mapper;

import com.gc.common.AutoFill;
import com.gc.common.OperationType;
import com.gc.dto.DishDto;
import com.gc.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper {
  int count(@Param("categoryId") Long categoryId);

  @AutoFill(OperationType.INSERT)
  void save(Dish dish);
  List<Dish> page(@Param("name") String name);
  Dish getById(@Param("id") Long id);

  @AutoFill(OperationType.UPDATE)
  void update(DishDto dishDto);
}
