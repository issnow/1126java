package com.gc.controller;

import com.gc.common.Result;
import com.gc.entity.Employee;
import com.gc.mapper.EmployeeMapper;
import com.gc.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 员工表的增删改查
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  EmployeeService employeeService;

  @Autowired
  EmployeeMapper employeeMapper;

  /*
    登录
    前端通过json传参,发送username和password两个参数,用Employee对象接受
    注意:这两个参数需要和Employee对象的属性一一对应
   */
  @PostMapping("/login")
  public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
    //1.将页面提交的密码进行md5加密
    String password = employee.getPassword();
    password = DigestUtils.md5DigestAsHex(password.getBytes());
    //2.根据username查数据库
    String username = employee.getUsername();
    Employee emp = employeeService.getEmpByUsername(username);
    //3.没有查到返回失败
    if (emp == null) {
      return Result.error("登录失败");
    }
    //4.密码比对,如果不一致则登录失败
    if (!emp.getPassword().equals(password)) {
      return Result.error("登录失败");
    }
    //5.查到员工,并且密码也对,则查看员工状态,如果是已禁用,则返回禁用结果
    if (emp.getStatus() == 0) {
      return Result.error("账号已禁用");
    }
    log.info(String.valueOf(emp));
    //6.登录成功,将员工id放在session中,并返回登录成功结果
    request.getSession().setAttribute("employee", emp.getId());
    return Result.success(emp);
  }

  /*
  退出
   */
  @PostMapping("/logout")
  public Result<String> logout(HttpServletRequest request) {
    //1.清理session中员工id
    request.getSession().removeAttribute("employee");
    //2.退出
    return Result.success("退出成功");
  }

  //新增员工
  @PostMapping
  public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
    log.info("新增员工:{}", employee);
    //1.设置初始密码,md5加密
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
    //employee.setCreateTime(LocalDateTime.now());
    //employee.setUpdateTime(LocalDateTime.now());
    Long empId = (Long) request.getSession().getAttribute("employee");
    //employee.setCreateUser(empId);
    //employee.setUpdateUser(empId);
    log.info("新增员工2:{}", employee);
    employeeService.save(employee);
    return Result.success("新增员工成功");
  }

  //员工信息的分页查询
  @GetMapping("/page")
  public Result<PageInfo<Employee>> page(@RequestParam("page") Integer pageNum, Integer pageSize, String name) {
    log.info("page:{},pagesize:{},name:{}", pageNum, pageSize, name);
    //第二种，Mapper接口方式的调用，推荐这种使用方式。
    PageHelper.startPage(pageNum, pageSize);
    List<Employee> emps = employeeService.page(name);
    PageInfo<Employee> employeePageInfo = new PageInfo<>(emps);
    return Result.success(employeePageInfo);
  }

  //编辑员工信息
  @PostMapping("/update")
  public Result<String> update(HttpServletRequest req, @RequestBody Employee employee) {
    if (employee == null) {
      return Result.error("参数异常");
    }
    employeeService.update(req, employee);
    return Result.success("success");
  }

  //查询一个员工根据id
  @GetMapping("/getEmpById")
  public Result<Employee> getEmpById(Integer id) {
    if (id == null) {
      return Result.error("参数异常");
    }
    Employee emp = employeeService.getEmpById(id);
    if (emp != null) {
      return Result.success(emp);
    }
    return Result.error("没查到");
  }
}
