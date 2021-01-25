package ru.vegd.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.vegd.dao.EmployeeDao;
import ru.vegd.entity.Employee;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock private EmployeeDao employeeDao;

    @InjectMocks private EmployeeServiceImpl employeeService;

    @Test
    public void getAll() {
        employeeService.getAll();

        verify(employeeDao).findAll();
    }

    @Test
    public void get() {
        Long employeeId = anyLong();

        employeeService.get(employeeId);

        verify(employeeDao).findById(employeeId);
    }

    @Test
    public void save() {
        Employee employee = mock(Employee.class);

        employeeService.save(employee);

        verify(employeeDao).save(employee);
    }

    @Test
    public void delete() {
        Long employeeId = anyLong();

        employeeService.delete(employeeId);

        verify(employeeDao).deleteById(employeeId);
    }
}
