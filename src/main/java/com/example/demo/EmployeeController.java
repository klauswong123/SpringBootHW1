package com.example.demo;

import org.springframework.http.HttpStatus;
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
        return employeeRepository.getByID(id);
    }

    @GetMapping(params = {"gender"})
    public Employee getEmployeeByGender(@RequestParam String gender){
        return employeeRepository.getByGender(gender);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeeByPage(@RequestParam Integer page, Integer pageSize){
        return employeeRepository.getByPage(page,pageSize);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.create(employee);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable ("id") Integer id, @RequestBody Employee employee){
        return employeeRepository.update(id,employee);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void updateEmployee(@PathVariable ("id") Integer id){
        employeeRepository.delete(id);
    }

}
