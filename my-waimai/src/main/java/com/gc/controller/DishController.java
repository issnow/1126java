package com.gc.controller;

import com.gc.common.Result;
import com.gc.dto.DishDto;
import com.gc.entity.Category;
import com.gc.entity.Dish;
import com.gc.service.CategoryService;
import com.gc.service.DishService;
import com.gc.service.impl.DishFlavorServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//这个controller包括了DishFlavorController,都在这一个里面了

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

  @Autowired
  DishService dishService;

  @Autowired
  DishFlavorServiceImpl dishFlavorService;

  @Autowired
  CategoryService categoryService;

  /*
  因为Dish实体类不满足接收flavor参数，即需要导入DishDto，用于封装页面提交的数据
  DTO，全称为Data Transfer Object，即数据传输对象，一般用于展示层与服务层之间的数据传输。
   */
  @PostMapping("/save")
  public Result<String> save(@RequestBody DishDto dishDto) {
    if (dishDto == null) {
      return Result.error("参数异常");
    }
    log.info("dishDto:{}", dishDto);
    dishService.save(dishDto);
    return Result.success("ok");
  }

  //修改菜品
  @PostMapping("/update")
  public Result<String> update(@RequestBody DishDto dishDto) {
    if (dishDto == null) {
      return Result.error("参数异常");
    }
    dishService.update(dishDto);
    return Result.success("ok");
  }

  //菜品分页查询
  @PostMapping("/page")
  public Result<PageInfo<DishDto>> page(@RequestBody Map<String, Object> map) {
    if (map == null) {
      return Result.error("参数异常");
    }
    Integer pageNum = (Integer) map.get("page");
    Integer pageSize = (Integer) map.get("pageSize");
    PageHelper.startPage(pageNum, pageSize);
    List<Dish> list = dishService.page((String) map.get("name"));
    //dish对象没有分类的名字,只有id,所以要转成dishDto处理
    List<DishDto> list2 = list.stream().map(item -> {
      //声明一个dishDto
      DishDto dishDto = new DishDto();
      //对象拷贝
      BeanUtils.copyProperties(item, dishDto);
      Long categoryId = item.getCategoryId();//分类id
      //根据id查询分类对象
      Category category = categoryService.getById(categoryId);
      String name = category.getName();
      dishDto.setCategoryName(name);
      return dishDto;
    }).collect(Collectors.toList());
    PageInfo<DishDto> dishPageInfo = new PageInfo<>(list2);
    return Result.success(dishPageInfo);
  }

  //根据id查询菜品和口味信息,所以用Dto对象
  @GetMapping("/getById")
  public Result<DishDto> getById(Long id) {
    if (id == null) {
      return Result.error("参数异常");
    }
    return dishService.getById(id);
  }
}
