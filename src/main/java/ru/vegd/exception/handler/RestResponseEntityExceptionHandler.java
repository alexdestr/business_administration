package ru.vegd.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.vegd.exception.EmployeeNotFoundException;
import ru.vegd.exception.InvalidDataException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { EmployeeNotFoundException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleEmployeeNotFoundException(
            RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Employee/employees not found");
        body.put("status", status.getReasonPhrase());
        body.put("statusCode", status.value());
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value
            = { InvalidDataException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleInvalidDataException(
            RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Invalid data or employee already exist");
        body.put("status", status.getReasonPhrase());
        body.put("statusCode", status.value());
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Method not allowed.");
        body.put("status", status.getReasonPhrase());
        body.put("statusCode", status.value());
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Not found.");
        body.put("status", status.getReasonPhrase());
        body.put("statusCode", status.value());
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), status, request);
    }


}
