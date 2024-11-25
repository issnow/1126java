package com.gc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SetMealMapper {
  int count(@Param("categoryId") Long categoryId);
}
