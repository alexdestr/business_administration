package ru.vegd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vegd.dao.EmployeeDao;
import ru.vegd.entity.Employee;
import ru.vegd.service.EmployeeService;

import java.util.List;
import java.util.Optional;

/**
 * {@code EmployeeServiceImpl} is a class which implemented {@link EmployeeService}.
 * and provides business logic.
 */
@Service
public class EmployeeServiceImpl
        implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Returns all employees.
     * @return List of all employees or empty list if there aren't employees in db.
     */
    @Override
    public List<Employee> getAll() {
        return (List<Employee>) employeeDao.findAll();
    }

    /**
     * Retrieves employee by id.
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if {@literal id} is {@literal null}
     */
    @Override
    public Employee get(Long id) {
        try {
            Optional<Employee> employee = employeeDao.findById(id);
            return employee.orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Saves or update a given entity. If {@literal employee.employeeId} isn't null will apply add
     * operation, otherwise will apply update operation.
     * @param employee must not be {@literal null}.
     * @return {@literal true} if operation was successfully, {@literal false} otherwise.
     */
    @Override
    public Boolean save(Employee employee) {
        try {
            employeeDao.save(employee);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Deletes the entity with the given id.
     * @param id must not be {@literal null}.
     * @return {@literal true} if operation was successfully, {@literal false} otherwise.
     */
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
