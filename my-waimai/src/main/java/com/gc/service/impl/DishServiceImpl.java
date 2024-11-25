package com.gc.service.impl;

import com.gc.common.Result;
import com.gc.dto.DishDto;
import com.gc.entity.Dish;
import com.gc.entity.DishFlavor;
import com.gc.mapper.DishMapper;
import com.gc.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class DishServiceImpl implements DishService {
  @Autowired
  DishMapper dishMapper;
  @Autowired
  DishFlavorServiceImpl dishFlavorService;

  @Override
  public int count(Long categoryId) {
    return dishMapper.count(categoryId);
  }

  //新增菜品,同时插入口味数据,操作两张表:dish,dish_flavor
  //注意:多表操作加事务控制
  @Override
  @Transactional
  public void save(DishDto dishDto) {
    //保存到菜品表
    dishMapper.save(dishDto);
    Long id = dishDto.getId();//菜品id
    List<DishFlavor> list = dishDto.getFlavors();
    list = list.stream().map(item -> {
      item.setDishId(id);
      return item;
    }).collect(Collectors.toList());
    //保存到口味表
    dishFlavorService.save(list);
  }

  //更新菜品和口味
  public void update(DishDto dishDto) {
    //更新菜品表
    dishMapper.update(dishDto);
    //先清理当前口味 dish_flavor delete操作
    dishFlavorService.deleteById(dishDto.getId());
    //在保存到口味表中 save操作
    List<DishFlavor> flavors = dishDto.getFlavors();
    //同样需要处理菜品id
    flavors = flavors.stream().map(item -> {
      item.setDishId(dishDto.getId());
      return item;
    }).collect(Collectors.toList());
    dishFlavorService.save(flavors);
  }

  public List<Dish> page(String name) {
    return dishMapper.page(name);
  }

  //根据id查询菜品和口味信息,所以用Dto对象
  public Result<DishDto> getById(Long id) {
    //查询菜品基本信息,dish表
    Dish dish = dishMapper.getById(id);
    //口味信息,dish_flavor表
    List<DishFlavor> list = dishFlavorService.getByDishId(id);

    //复制对象
    DishDto dishDto = new DishDto();
    BeanUtils.copyProperties(dish, dishDto);
    dishDto.setFlavors(list);
    return Result.success(dishDto);
  }
}
