package com.gc.filter;

import com.alibaba.fastjson.JSON;
import com.gc.common.BaseContext;
import com.gc.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
检查用户是否已经登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
  //路径匹配器,支持通配符
  public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    //1.获取请求uri
    String uri = req.getRequestURI();
    log.info("拦截到请求:{}", uri);
    //2.本次请求是否需要处理
    //不需要处理的路径
    String[] urls = {
      "/employee/login",
      "/employee/logout",
      "/backend/**",
      "/front/**",
      "/test/**",
    };
      //"/**"
    boolean check = this.check(urls, uri);
    //3.不需要处理,直接放行
    if (check) {
      log.info("本次请求{}不需要处理", uri);
      chain.doFilter(req, res);
      return;
    }
    //4.判断登录状态,如果已登录,直接放行
    if (req.getSession().getAttribute("employee") != null) {
      log.info("用户已登录:id为{}", req.getSession().getAttribute("employee"));
      Long empId = (Long) req.getSession().getAttribute("employee");
      BaseContext.setCurrentId(empId);
      chain.doFilter(req, res);
      return;
    }
    log.info("用户未登录");
    //5.没登录,返回未登录结果,通过输出流方式向页面响应数据
    res.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    return;
  }

  /**
   * 检查请求是否需要放行
   * @param uri
   * @return
   */
  public boolean check(String[] urls, String uri) {
    for (String url : urls) {
      boolean match = PATH_MATCHER.match(url, uri);
      if (match) {
        return true;
      }
    }
    return false;
  }
}

class  B implements Filter{
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

  }
}
