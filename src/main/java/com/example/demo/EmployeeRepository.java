package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees= new ArrayList<>();

    EmployeeRepository(){
        this.employees.add(new Employee("Klaus",1,23));
    }

    public List<Employee> findAll(){
        return this.employees;
    }

    public boolean create(Employee employee){
        Integer id = employees.stream().mapToInt(Employee::getId).max().orElse(0)+1;
        employee.setId(id);
        return employees.add(employee);
    }
}
