package com.example.demo.controller;

import com.example.demo.service.EmployeeService;
import com.example.demo.entity.Employee;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.mapper.EmployeeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;
    public EmployeeController(EmployeeService employeeService , EmployeeMapper employeeMapper){
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeByID(@PathVariable("id") String id){
        return employeeMapper.toResponse(employeeService.getByID(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeeByGender(@RequestParam String gender){
        List<EmployeeResponse> employeeResponses = employeeService.getByGender(gender).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
        return employeeResponses;
    }

    @GetMapping(params = {"page","pageSize"})
    public List<EmployeeResponse> getEmployeeByPage(@RequestParam Integer page, Integer pageSize){
        List<EmployeeResponse> employeeResponses = employeeService.getByPage(page,pageSize).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
        return employeeResponses;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.create(employeeMapper.toEntity(employeeRequest));
    }


    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable ("id") String id, @RequestBody EmployeeRequest employeeRequest){
        return employeeService.edit(id, employeeMapper.toEntity(employeeRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable ("id") String id){
        employeeService.delete(id);
    }

}
