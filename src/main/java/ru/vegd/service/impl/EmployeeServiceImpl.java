package ru.vegd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vegd.dao.EmployeeDao;
import ru.vegd.entity.Employee;
import ru.vegd.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public Boolean get(Long id) {
        try {
            employeeDao.findById(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Boolean save(Employee employee) {
        try {
            employeeDao.save(employee);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Boolean delete(Long id) {
        try {
            employeeDao.deleteById(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
