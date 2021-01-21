package ru.vegd.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vegd.entity.Employee;

public interface EmployeeDao extends CrudRepository<Employee, Long> {
}
