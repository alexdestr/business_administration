package ru.vegd.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vegd.adapter.LocalDateDeserializerAdapter;
import ru.vegd.adapter.LocalDateSerializerAdapter;
import ru.vegd.entity.Employee;
import ru.vegd.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
public class EmployeeAddController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee/add")
    @ResponseBody
    public String add(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializerAdapter())
                .create();
        Employee employee = gson.fromJson(requestBody, Employee.class);

        if (employee.getEmployeeId() == null) {
            if (employeeService.save(employee)) {
                return "OK"; // TODO
            } else {
                return "NOT OK"; // TODO
            }
        }

        return "NOT OK"; // TODO
    }
}
