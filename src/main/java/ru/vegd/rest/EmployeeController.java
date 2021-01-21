package ru.vegd.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vegd.adapter.LocalDateAdapter;
import ru.vegd.entity.Employee;
import ru.vegd.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/getAll")
    @ResponseBody
    public String getAllEmployees() {
        List<Employee> employeeList = employeeService.getAll();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String response = gson.toJson(employeeList);

        return response;
    }
}
