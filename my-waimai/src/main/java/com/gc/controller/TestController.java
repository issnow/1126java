package com.gc.controller;

import com.gc.common.Result;
import com.gc.entity.DishFlavor;
import com.gc.service.impl.DishFlavorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {
  @Autowired
  DishFlavorServiceImpl dishFlavorService;

  @PostMapping("/list")
  public Result<String> fn(@RequestBody List<DishFlavor> list){
    dishFlavorService.save(list);
    return Result.success("ok");
  }
}
