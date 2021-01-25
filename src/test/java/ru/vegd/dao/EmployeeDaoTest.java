package ru.vegd.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import ru.vegd.TestConfig;
import ru.vegd.entity.Employee;
import ru.vegd.entity.Gender;
import ru.vegd.util.SequenceReseter;

import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class EmployeeDaoTest {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    SequenceReseter seqReseter;

    @After
    public void reseter() {
        seqReseter.resetEmployeeSeq();
    }

    @Test
    @DatabaseSetup(value = "/employee-data.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findAll() {
        Integer expectedSize = 2;
        Integer size = ((ArrayList) employeeDao.findAll()).size();

        Assert.assertEquals(expectedSize, size);
    }

    @Test
    @DatabaseSetup(value = "/employee-data.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findById() {
        Employee employee = new Employee();
        employee.setEmployeeId(3L);
        employee.setFirstName("First");
        employee.setLastName("Fir");
        employee.setDepartmentId(10L);
        employee.setJobTitle("test title 1");
        employee.setGender(Gender.MALE);
        employee.setDateOfBirth(LocalDate.of(1996, 2, 12));

        Assert.assertEquals(employee, employeeDao.findById(3L).get());
    }

}
