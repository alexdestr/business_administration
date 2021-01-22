package ru.vegd.rest;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vegd.adapter.LocalDateSerializerAdapter;
import ru.vegd.entity.Employee;
import ru.vegd.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeGetAllController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/getAll")
    @ResponseBody
    public String getAllEmployees() {
        List<Employee> employeeList = employeeService.getAll();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializerAdapter())
                .create();
        JsonElement response = gson.toJsonTree(employeeList);

        JsonObject status = new JsonObject();
        if (!response.isJsonNull()) {
            status.addProperty("status", HttpStatus.OK.value());
        } else {
            status.addProperty("status", HttpStatus.BAD_REQUEST.value());
        }
        response.getAsJsonArray().add(status);

        return response.toString();
    }
}
