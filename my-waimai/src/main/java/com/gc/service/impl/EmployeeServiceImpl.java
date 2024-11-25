package com.gc.service.impl;

import com.gc.common.Result;
import com.gc.entity.Employee;
import com.gc.mapper.EmployeeMapper;
import com.gc.service.EmployeeService;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
/*
所有的逻辑都要写在service中
 */

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
  @Autowired
  EmployeeMapper employeeMapper;

  @Override
  public Employee getEmpByUsername(String username) {
    return employeeMapper.getEmpByUsername(username);
  }

  @Override
  public void save(Employee employee) {
    employeeMapper.save(employee);
  }

  @Override
  public List<Employee> page(String name) {
    return employeeMapper.page(name);
  }

  @Override
  public void update(HttpServletRequest req, Employee employee) {
    //employee.setUpdateUser((Long) req.getSession().getAttribute("employee"));
    //employee.setUpdateTime(LocalDateTime.now());
    employeeMapper.update(employee);
  }

  @Override
  public Employee getEmpById(Integer id) {
    return employeeMapper.getById(id);
  }
}
