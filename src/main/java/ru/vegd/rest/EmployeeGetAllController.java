package ru.vegd.rest;

import com.google.gson.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Swagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vegd.adapter.LocalDateSerializerAdapter;
import ru.vegd.entity.Employee;
import ru.vegd.exception.EmployeeNotFoundException;
import ru.vegd.service.EmployeeService;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeGetAllController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/getAll")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Returns json with employees"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Returns json with error status")})
    public String getAllEmployees() {
        List<Employee> employeeList = employeeService.getAll();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializerAdapter())
                .create();
        JsonElement response = gson.toJsonTree(employeeList);

        JsonObject status = new JsonObject();
        if (!response.isJsonNull()) {
            status.addProperty("status", HttpStatus.OK.value());
            response.getAsJsonArray().add(status);
            return response.toString();
        }
        throw new EmployeeNotFoundException();
    }
}
