package com.gc.mapper;

import com.gc.common.AutoFill;
import com.gc.common.OperationType;
import com.gc.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
  @AutoFill(OperationType.INSERT)
  void save(List<DishFlavor> list);
  List<DishFlavor> getByDishId(@Param("dishId") Long dishId);
  void deleteById(@Param("dishId") Long dishId);
}
