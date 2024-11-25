package com.gc.service;

import com.gc.common.Result;
import com.gc.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EmployeeService {
  Employee getEmpByUsername(String username);
  void save(Employee employee);
  List<Employee> page(String name);
  void update(HttpServletRequest req, Employee employee);
  Employee getEmpById(Integer id);
}
