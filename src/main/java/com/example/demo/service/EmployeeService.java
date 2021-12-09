package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.NoEmployeeFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepositoryMongo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepositoryMongo employeeRepositoryMongo;
    private EmployeeMapper employeeMapper;
    public EmployeeService(EmployeeRepositoryMongo employeeRepositoryMongo, EmployeeMapper employeeMapper){
        this.employeeRepositoryMongo = employeeRepositoryMongo;
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> findAll() {
        return employeeRepositoryMongo.findAll();
    }

    public Employee edit(String id, Employee employee) {
        Employee targetEmployee = getByID(id);
        if(employee.getSalary()!=null) targetEmployee.setSalary(employee.getSalary());
        if(employee.getAge()!=null) targetEmployee.setAge(employee.getAge());
        return employeeRepositoryMongo.save(targetEmployee);
    }

    public Employee getByID(String id) {
        return employeeRepositoryMongo.findById(id).orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepositoryMongo.findByGender(gender);
    }

    public Employee create(Employee employee) {
        return employeeRepositoryMongo.insert(employee);
    }

    public List<Employee> getByPage(int page, int pageSize) {
        return employeeRepositoryMongo.findAll().stream()
                .skip((long) (page-1) *pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        employeeRepositoryMongo.deleteById(id);
    }

    public List<Employee> getEmployeesByCompanyID(String companyID){
        return employeeRepositoryMongo.findByCompanyID(companyID);
    }


}
