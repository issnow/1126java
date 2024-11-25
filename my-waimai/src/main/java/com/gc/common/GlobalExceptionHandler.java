package com.gc.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/*
全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
  /**
   * 异常处理方法1
   *
   * @return
   */
  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
    String msg = ex.getMessage();
    log.error(msg);
    if (msg.contains("Duplicate entry")) {
      String[] arr = msg.split(" ");
      return Result.error(arr[2] + "已存在");
    }
    return Result.error("未知错误");
  }

  /**
   * 异常处理方法2
   *
   * @return
   */
  @ExceptionHandler(CustomException.class)
  public Result<String> exceptionHandler2(CustomException ex) {
    String msg = ex.getMessage();
    log.error(msg);
    return Result.error(msg);
  }
}
