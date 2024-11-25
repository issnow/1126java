package com.gc.mapper;

import com.gc.common.AutoFill;
import com.gc.common.OperationType;
import com.gc.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
  @AutoFill(OperationType.INSERT)
  void save(Category category);
  List<Category> page();
  @AutoFill(OperationType.UPDATE)
  void update(Category category);
  void delete(@Param("id") Long id);
  List<Category> list(@Param("type") Integer type);
  Category getById(@Param("id") Long id);
}
