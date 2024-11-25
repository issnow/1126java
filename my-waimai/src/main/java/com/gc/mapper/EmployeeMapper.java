package com.gc.mapper;

import com.gc.common.AutoFill;
import com.gc.common.OperationType;
import com.gc.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {
  //根据username查询
  Employee getEmpByUsername(@Param("username") String username);
  //保存
  @AutoFill(OperationType.INSERT)
  void save(Employee employee);
  //查询员工
  List<Employee> page(@Param("name") String name);
  //更新员工
  @AutoFill(OperationType.UPDATE)

  void update(Employee employee);
  Employee getById(@Param("id") Integer id);
}
