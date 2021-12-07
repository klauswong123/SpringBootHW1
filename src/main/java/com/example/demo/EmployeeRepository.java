package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees= new ArrayList<>();

    EmployeeRepository(){
        this.employees.add(new Employee("Klaus",1,23,999999,"male"));
    }
    public List<Employee> findAll(){
        return this.employees;
    }

    public Employee getByID(Integer id){
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Employee getByGender(String gender){
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .findFirst().orElse(null);
    }

    public Employee create(Employee employee){
        Integer id = employees.stream().mapToInt(Employee::getId).max().orElse(0)+1;
        employee.setId(id);
        employees.add(employee);
        return employee;
    }
}
