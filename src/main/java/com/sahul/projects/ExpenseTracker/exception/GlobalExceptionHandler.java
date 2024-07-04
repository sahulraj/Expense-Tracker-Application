package com.sahul.projects.ExpenseTracker.exception;

import com.sahul.projects.ExpenseTracker.entity.ErrorObj;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObj>handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest webRequest)
    {
        ErrorObj errorObj= new ErrorObj();
        errorObj.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObj.setTimestamp(new Date());
        errorObj.setMsg(ex.getMessage());
        return new ResponseEntity<ErrorObj>(errorObj, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObj>handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest webRequest)
    {
        ErrorObj errorObj= new ErrorObj();
        errorObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObj.setTimestamp(new Date());
        errorObj.setMsg(ex.getMessage());
        return new ResponseEntity<ErrorObj>(errorObj, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObj>handleGeneralException(Exception e, WebRequest webRequest)
    {
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMsg(e.getMessage());
        errorObj.setTimestamp(new Date());
        errorObj.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<ErrorObj>(errorObj, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorObj>handleEmailAlreadyExistsException(Exception e)
    {
        ErrorObj errorObj = new ErrorObj();
        errorObj.setStatusCode(HttpStatus.CONFLICT.value());
        errorObj.setMsg(e.getMessage());
        errorObj.setTimestamp(new Date());
        return new ResponseEntity<ErrorObj>(errorObj, HttpStatus.CONFLICT);
    }
    @ExceptionHandler({ItemExistsException.class})
    public ResponseEntity<ErrorObj>handleItemExistsException(Exception e)
    {
        ErrorObj errorObj = new ErrorObj();
        errorObj.setTimestamp(new Date());
        errorObj.setMsg(e.getMessage());
        errorObj.setStatusCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorObj, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object>body = new HashMap<String, Object>();
        body.put("timestamp",new Date());
        body.put("statusCode", HttpStatus.BAD_REQUEST);
        List<String>errors = ex.getBindingResult().getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
        body.put("messages", errors);
        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }
}
