package com.example.demo.Advisors;


import com.example.demo.Exception.NoCompanyFoundException;
import com.example.demo.Exception.NoEmployeeFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GlobalControllerAdvice {
    @ExceptionHandler({NoEmployeeFoundException.class})
    public ErrorResponse handlerEmployeeNotFound(Exception exception){
        return new ErrorResponse(404,"Entity Not Found");
    }

    @ExceptionHandler({NoCompanyFoundException.class})
    public ErrorResponse handlerCompanyNotFound(Exception exception){
        return new ErrorResponse(404,"Entity Not Found");
    }
}
