package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.NoEmployeeFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper){
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee edit(String id, Employee employee) {
        Employee targetEmployee = getByID(id);
        if(employee.getSalary()!=null) targetEmployee.setSalary(employee.getSalary());
        if(employee.getAge()!=null) targetEmployee.setAge(employee.getAge());
        return employeeRepository.save(targetEmployee);
    }

    public Employee getByID(String id) {
        return employeeRepository.findById(id).orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee create(Employee employee) {
        return employeeRepository.insert(employee);
    }

    public List<Employee> getByPage(int page, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(page-1, pageSize)).toList();
    }

    public void delete(String id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByCompanyID(String companyID){
        return employeeRepository.findByCompanyID(companyID);
    }


}
