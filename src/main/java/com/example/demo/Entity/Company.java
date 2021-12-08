package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {
    private Integer id;
    private String name;
    private List<Employee> employees = new ArrayList<>();

    @SafeVarargs
    public Company(Integer id, String name, List<Employee>... employees) {
        this.id = id;
        this.name = name;
        if(employees.length!=0){
            this.employees = employees[0];
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


}
