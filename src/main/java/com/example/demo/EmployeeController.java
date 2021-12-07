package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.create(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable ("id") Integer id, @RequestBody Employee employee){
        return employeeRepository.update(id,employee);
    }

    @DeleteMapping("/{id}")
    public void updateEmployee(@PathVariable ("id") Integer id){
        employeeRepository.delete(id);
    }

}
