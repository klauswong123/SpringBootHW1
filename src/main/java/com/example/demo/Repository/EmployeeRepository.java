package com.example.demo.Repository;

import com.example.demo.Entity.Employee;
import com.example.demo.Exception.NoEmployeeFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees= new ArrayList<>();

    public EmployeeRepository(){
        this.employees.add(new Employee("Klaus",1,23,999999,"male",1));
        this.employees.add(new Employee("Jason",2,24,12312412,"female",1));
    }
    public List<Employee> findAll(){
        return this.employees;
    }

    public Employee getByID(Integer id){
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst().orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> getByGender(String gender){
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee create(Employee employee){
        Integer id = employees.stream().mapToInt(Employee::getId).max().orElse(0)+1;
        employee.setId(id);
        employees.add(employee);
        return employee;
    }

    public Employee update(Integer id, Employee employee) {
        Employee targetEmployee = employees.stream()
                .filter(singleEmployee -> singleEmployee.getId().equals(id))
                .findFirst().orElseThrow(NullPointerException::new);
        if (employee.getAge()!=null) targetEmployee.setAge(employee.getAge());
        if (employee.getSalary()!=null) targetEmployee.setSalary(employee.getSalary());
        employees.remove(employee);
        employees.add(targetEmployee);
        return targetEmployee;
    }

    public Employee delete(Integer id) {
        Employee deletedEmployee = employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst().orElse(null);
        employees.removeIf(employee -> employee.getId().equals(id));
        return deletedEmployee;
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return employees.stream()
                .skip((long) (page-1) *pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByCompanyID(Integer companyID){
        return findAll().stream()
                .filter(employee -> employee.getCompanyID().equals(companyID)).collect(Collectors.toList());
    }

    public void clearAll() {
        employees.clear();
    }
}
