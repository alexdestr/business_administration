package ru.vegd.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vegd.adapter.LocalDateDeserializerAdapter;
import ru.vegd.entity.Employee;
import ru.vegd.exception.EmployeeNotFoundException;
import ru.vegd.exception.InvalidDataException;
import ru.vegd.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EmployeeDeleteController {
    @Autowired
    EmployeeService employeeService;

    @DeleteMapping("/employee/delete")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Returns json with success msg and status"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Returns json with error status")})
    public Map deleteEmployee(@RequestBody String requestBody) throws IOException {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializerAdapter())
                .create();
        Employee employee = gson.fromJson(requestBody, Employee.class);

        if (employee.getEmployeeId() != null) {
            if (employeeService.delete(employee.getEmployeeId())) {
                HttpStatus status = HttpStatus.OK;
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("message", "Successfully deleted");
                body.put("status", status.getReasonPhrase());
                body.put("statusCode", status.value());
                return body;
            }
        }
        throw new EmployeeNotFoundException();
    }
}
