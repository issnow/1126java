package com.gc.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD) // 只能加载方法上
@Retention(RetentionPolicy.RUNTIME)// 固定写法
public @interface AutoFill {
  OperationType value();//枚举，数据库操作类型：UPDATE INSERT
}
