package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee edit(Integer id, Employee updateEmployee) {
        return employeeRepository.update(id,updateEmployee);
    }

    public Employee getByID(Integer id) {
        return employeeRepository.getByID(id);
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.getByGender(gender);
    }
}
