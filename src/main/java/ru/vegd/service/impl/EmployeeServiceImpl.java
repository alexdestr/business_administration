package ru.vegd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vegd.dao.EmployeeDao;
import ru.vegd.entity.Employee;
import ru.vegd.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl
        implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> getAll() {
        return (List<Employee>) employeeDao.findAll();
    }

    @Override
    public Employee get(Long id) {
        try {
            Optional<Employee> employee = employeeDao.findById(id);
            return employee.orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Boolean save(Employee employee) {
        try {
            employeeDao.save(employee);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            employeeDao.deleteById(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
