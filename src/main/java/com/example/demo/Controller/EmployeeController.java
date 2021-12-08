package com.example.demo.Controller;

import com.example.demo.EmployeeService;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeByID(@PathVariable("id") Integer id){
        return employeeService.getByID(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam String gender){
        return employeeService.getByGender(gender);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeeByPage(@RequestParam Integer page, Integer pageSize){
        return employeeService.getByPage(page,pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.create(employee);
    }


    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable ("id") Integer id, @RequestBody Employee employee){
        return employeeService.edit(id,employee);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable ("id") Integer id){
        employeeService.delete(id);
    }

}
