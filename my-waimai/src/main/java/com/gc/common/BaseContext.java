package com.gc.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 * 以便在处理请求的过程中随时获取当前用户的信息
 */
public class BaseContext {
  //ThreadLocal 提供了线程局部变量，即每个线程都有自己的独立变量副本，线程之间的变量是互不干扰的
  private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

  /**
   * 设置值
   *
   * @param id
   */
  public static void setCurrentId(Long id) {
    threadLocal.set(id);
  }

  /**
   * 获取值
   *
   * @return
   */
  public static Long getCurrentId() {
    return threadLocal.get();
  }
}