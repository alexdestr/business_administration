package ru.vegd.service;

import ru.vegd.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();
    Employee get(Long id);
    Boolean save(Employee employee);
    Boolean delete(Long id);
}
