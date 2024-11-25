package com.gc.service.impl;

import com.gc.entity.DishFlavor;
import com.gc.mapper.DishFlavorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DishFlavorServiceImpl {
  @Autowired
  DishFlavorMapper dishFlavorMapper;

  public void save(List<DishFlavor> list) {
    dishFlavorMapper.save(list);
  }

  public List<DishFlavor> getByDishId(Long id){
    return dishFlavorMapper.getByDishId(id);
  }

  //根据菜品id删除口味
  public void deleteById(Long id){
    dishFlavorMapper.deleteById(id);
  }
}
