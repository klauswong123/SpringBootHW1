package com.example.demo.dto;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class CompanyResponse {
    private String id;
    private String name;
    private List<EmployeeResponse> employeeResponses = new ArrayList<>();

    public CompanyResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeResponse> getEmployeeResponses() {
        return employeeResponses;
    }

    public void setEmployeeResponses(List<EmployeeResponse> employeeResponses) {
        this.employeeResponses = employeeResponses;
    }

}
