package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeByID(@PathVariable("id") Integer id){
        return employeeRepository.getEmployeeByID(id);
    }

    @GetMapping(params = {"gender"})
    public Employee getEmployeeByID(@RequestParam String gender){
        return employeeRepository.getEmployeeByGender(gender);
    }

}
