package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private List<Employee> employees= new ArrayList<>();

    EmployeeRepository(){
        this.employees.add(new Employee("Klaus",1,23,999999,"male"));
    }
    public List<Employee> findAll(){
        return this.employees;
    }

    public Employee getEmployeeByID(Integer id){
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Employee getEmployeeByGender(String gender){
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .findFirst().orElse(null);
    }

    public boolean create(Employee employee){
        Integer id = employees.stream().mapToInt(Employee::getId).max().orElse(0)+1;
        employee.setId(id);
        return employees.add(employee);
    }
}
