package com.gc.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 自定义切面，实现公共字段自动填充处理逻辑(createTime createUser)
 */
@Aspect
@Component
public class AutoFillAspect {
  /*
   * 1. 在切入点表达式里：如果想要缩小spring的扫描范围，可以使用多切入点表达式连接 连接符 &&
   * 选择要增强的方法：
   *        只扫描mapper层里的方法 并且 有AutoFill注解的方法
   * 2. 在注解方式切入点的通知方法，如果想在通知方法里获取到注解对象可以写成：
   *   2.1 在方法上直接加注解类型的形参
   *   2.2 修改切入点表达式
   *       原来：@annotation(注解的全限定类名)
   *       改成：@annotation(方法的注解类型形参名)
   * 注意：如果通知方法有多个形参的话，那么JoinPoint类型的参数要放到第一个
   *
   *
   * Java里如果要调用一个方法，有两种方式
   *   方式1：对象名.方法名(实参)
   *   方式2：反射调用方法
   *       先获取目标类的字节码
   *       在获取想要调用的方法
   *       最后反射执行这个方法
   * */
  @Before("execution(* com.gc.mapper.*.*(..)) && @annotation(autoFill)")
  public void autoFill(JoinPoint joinPoint, AutoFill autoFill) {//JoinPoint 得到要被增强的目标方法
    //获取目标方法的实参
    Object[] args = joinPoint.getArgs();
    // 为了提高程序的健壮性，加一个判断：如果方法没有实参，就什么都不做
    if (args == null || args.length == 0) {
      return;
    }
    Object entity = args[0];
    if (entity instanceof List) {
      //参数是list类型,不是一个实例对象
      for (Object item : (List) entity) {
        Class clazz = item.getClass();
        this.fillValue(autoFill, clazz, item);
      }
    } else {
      //普通实例对象
      Class clazz = entity.getClass();
      this.fillValue(autoFill, clazz, entity);
    }
  }

  public void fillValue(AutoFill autoFill, Class clazz, Object entity) {
    //如果目标方法操作类型是INSERT：设置四个值，否则是操作Update，设置两个值
    if (autoFill.value() == OperationType.INSERT) {
      try {
        Method setCreateTime = clazz.getMethod("setCreateTime", LocalDateTime.class);
        Method setUpdateTime = clazz.getMethod("setUpdateTime", LocalDateTime.class);
        Method setCreateUser = clazz.getMethod("setCreateUser", Long.class);
        Method setUpdateUser = clazz.getMethod("setUpdateUser", Long.class);
        setUpdateUser.invoke(entity, BaseContext.getCurrentId());
        setUpdateTime.invoke(entity, LocalDateTime.now());
        setCreateUser.invoke(entity, BaseContext.getCurrentId());
        setCreateTime.invoke(entity, LocalDateTime.now());
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        Method setUpdateTime = clazz.getMethod("setUpdateTime", LocalDateTime.class);
        Method setUpdateUser = clazz.getMethod("setUpdateUser", Long.class);
        setUpdateUser.invoke(entity, BaseContext.getCurrentId());
        setUpdateTime.invoke(entity, LocalDateTime.now());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

