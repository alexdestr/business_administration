package ru.vegd.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.vegd.Application;
import ru.vegd.TestConfig;
import ru.vegd.entity.Employee;
import ru.vegd.entity.Gender;
import ru.vegd.service.EmployeeService;
import ru.vegd.service.impl.EmployeeServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Employee> employeeList;

    @BeforeEach
    public void setUp() {
        employeeList = new ArrayList<>();
        employeeList.add(
                new Employee(1L,
                        "First",
                        "Fir",
                        10L,
                        "ST", Gender.MALE,
                        LocalDate.of(1990, 4, 17)));
        employeeList.add(
                new Employee(2L,
                        "Second",
                        "Sec",
                        20L,
                        "ND", Gender.MALE,
                        LocalDate.of(1995, 6, 16)));
        employeeList.add(
                new Employee(3L,
                        "Third",
                        "Th",
                        30L,
                        "RD", Gender.MALE,
                        LocalDate.of(1991, 3, 2)));
    }

    @Test
    public void getAllEmployees() throws Exception {

        given(employeeService.getAll()).willReturn(employeeList);

        this.mockMvc.perform(get("/employee/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath
                                ("$.size()", is(employeeList.size() + 1)));
        // +1 because http code be kept in json response
    }

    @Test
    public void getEmployeeById() throws Exception {
        final Long employeeId = 1L;
        final Employee employee
                = new Employee(
                        1L,
                "First",
                "Fir",
                10L,
                "ST",
                Gender.MALE,
                LocalDate.of(1988, 11, 26));

        given(employeeService.get(employeeId)).willReturn(employee);

        this.mockMvc.perform(get("/employee/get?id={id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.departmentId", is(employee.getDepartmentId().intValue())))
                .andExpect(jsonPath("$.jobTitle", is(employee.getJobTitle())))
                .andExpect(jsonPath("$.gender", is(employee.getGender().toString())))
                .andExpect(jsonPath("$.dateOfBirth", is(employee.getDateOfBirth().toString())));
    }

    @Test
    public void shouldReturnErrorWhenFindEmployeeById() throws Exception {
        final Long employeeId = 1L;
        given(employeeService.get(employeeId)).willReturn(null);

        this.mockMvc.perform(get("/employee/get?id={id}", employeeId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        given(employeeService.save(any(Employee.class))).willReturn(true);

        final Employee employee
                = new Employee(
                null,
                "Create",
                "Cre",
                10L,
                "TE",
                Gender.MALE,
                LocalDate.of(1980, 10, 16));

        this.mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Successfully created")))
                .andExpect(jsonPath("$.statusCode", is(201)));
    }

    @Test
    public void shouldReturnErrorWhenCreateInvalidEmployee() throws Exception {
        final Employee employee
                = new Employee(
                null,
                "a",
                "b",
                null,
                null,
                null,
                null);

        this.mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid data or employee already exist")))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        given(employeeService.save(any(Employee.class))).willReturn(true);

        final Employee employee
                = new Employee(
                1L,
                "Create",
                "Cre",
                10L,
                "TE",
                Gender.MALE,
                LocalDate.of(1980, 10, 16));

        this.mockMvc.perform(post("/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Successfully updated")))
                .andExpect(jsonPath("$.statusCode", is(200)));
    }

    @Test
    public void shouldReturnErrorWhenUpdateInvalidEmployee() throws Exception {
        final Employee employee
                = new Employee(
                null,
                "a",
                "b",
                null,
                null,
                null,
                null);

        this.mockMvc.perform(post("/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid data or employee already exist")))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {
        Long employeeId = 1L;
        given(employeeService.delete(employeeId)).willReturn(true);

        final Employee employee = new Employee();
        employee.setEmployeeId(1L);

        this.mockMvc.perform(delete("/employee/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Successfully deleted")))
                .andExpect(jsonPath("$.statusCode", is(200)));
    }

    @Test
    public void shouldReturnErrorWhenDelete() throws Exception {
        Long employeeId = 1L;
        given(employeeService.delete(employeeId)).willReturn(true);

        final Employee employee = new Employee();

        this.mockMvc.perform(delete("/employee/delete", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Employee/employees not found")))
                .andExpect(jsonPath("$.statusCode", is(400)));;
    }

}
