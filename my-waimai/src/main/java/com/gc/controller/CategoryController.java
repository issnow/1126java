package com.gc.controller;

import com.gc.common.Result;
import com.gc.entity.Category;
import com.gc.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单分类表的增删改查
 */

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
  @Autowired
  CategoryService categoryService;

  //新增分类
  @PostMapping
  public Result<String> save(@RequestBody Category category) {
    if (category == null) {
      return Result.error("参数异常");
    }
    categoryService.save(category);
    return Result.success("ok");
  }

  //分页查询
  @PostMapping("/page")
  public Result<PageInfo<Category>> page(@RequestBody Map<String, Object> map) {
    if (map == null) {
      return Result.error("参数异常");
    }
    Integer pageNum = (Integer) map.get("page");
    Integer pageSize = (Integer) map.get("pageSize");
    PageHelper.startPage(pageNum, pageSize);
    List<Category> arr = categoryService.page();
    PageInfo<Category> categoryPageInfo = new PageInfo<>(arr);
    return Result.success(categoryPageInfo);
  }

  //更新
  @PostMapping("/update")
  public Result<String> update(@RequestBody Category category) {
    if (category == null) {
      return Result.error("参数异常");
    }
    categoryService.update(category);
    return Result.success("ok");
  }

  //逻辑删除
  @PostMapping("/delete")
  public Result<String> del(@RequestBody Map<String, String> map) {
    if (map == null) {
      return Result.error("参数异常");
    }
    categoryService.delete(map);
    return Result.success("ok");
  }

  //注意:在get请求的时候,可以直接那实体类接受参数,而不用@RequestBody注解
  //菜品分类
  @GetMapping("/list")
  //public Result<List<Category>> list(Integer type){
  public Result<List<Category>> list(Category category){
    if(category.getType() == null) {
      return Result.error("参数异常");
    }
    List<Category> list = categoryService.list(category.getType());
    return Result.success(list);
  }
}
