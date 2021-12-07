package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees= new ArrayList<>();

    EmployeeRepository(){
        this.employees.add(new Employee("Klaus",1,23,999999,"male"));
        this.employees.add(new Employee("Jason",2,24,12312412,"female"));
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

    public Employee update(Integer id, Employee employee) {
        Employee unModifiedEmployee = employees.stream()
                .filter(singleEmployee -> singleEmployee.getId().equals(id))
                .findFirst().orElseThrow(NullPointerException::new);
        System.out.println(employee.getName());
        unModifiedEmployee.setAge(employee.getAge());
        unModifiedEmployee.setGender(employee.getGender());
        unModifiedEmployee.setName(employee.getName());
        unModifiedEmployee.setSalary(employee.getSalary());
        return unModifiedEmployee;
    }

    public void delete(Integer id) {
        employees.removeIf(employee -> employee.getId().equals(id));
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return employees.stream()
                .skip((long) page *pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
