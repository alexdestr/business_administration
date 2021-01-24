package ru.vegd.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vegd.adapter.LocalDateDeserializerAdapter;
import ru.vegd.entity.Employee;
import ru.vegd.exception.InvalidDataException;
import ru.vegd.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
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
                HttpStatus status = HttpStatus.CREATED;
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("message", "Successfully created");
                body.put("status", status);
                body.put("statusCode", status.value());
                return body.toString();
            }
        }
        throw new InvalidDataException();
    }
}
