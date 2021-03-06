package ru.vegd.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vegd.adapter.LocalDateSerializerAdapter;
import ru.vegd.entity.Employee;
import ru.vegd.exception.EmployeeNotFoundException;
import ru.vegd.service.EmployeeService;

import java.net.HttpURLConnection;
import java.time.LocalDate;

@Controller
public class EmployeeGetController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/get")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Returns json with employee"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Returns json with error status")})
    public String getEmployee(@RequestParam("id") Long id) {
        Employee employee = null;
        if ((employee = employeeService.get(id)) != null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerializerAdapter())
                    .create();
            JsonElement response = gson.toJsonTree(employee);

            if (!response.isJsonNull()) {
                response.getAsJsonObject().addProperty("status", HttpStatus.OK.value());
                return response.toString();
            }
        }
        throw new EmployeeNotFoundException();
    }
}
