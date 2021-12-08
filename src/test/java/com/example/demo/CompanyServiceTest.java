package com.example.demo;

import com.example.demo.Entity.Company;
import com.example.demo.Entity.Employee;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Service.CompanyService;
import com.example.demo.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest{
    @Mock
    CompanyRepository mockCompanyRepository;
    @InjectMocks
    CompanyService companyService;
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Klaus",1,20,99999999,"female",1));
        employees.add(new Employee("Klaus",2,20,99999999,"female",1));
        employees.add(new Employee("Klaus",3,20,99999999,"female",1));
        employees.add(new Employee("Klaus",4,20,99999999,"female",2));
        employees.add(new Employee("Klaus",5,20,99999999,"female",2));
        employees.add(new Employee("Klaus",6,20,99999999,"female",2));
        employees.add(new Employee("Klaus",7,20,99999999,"female",2));
        return employees;
    }

    @Test
    void should_return_all_employees_when_get_all_given_employees() {
        //given
        List<Company> companies = new ArrayList<>();
        given(mockCompanyRepository.findAll())
                .willReturn(companies);

        companies.add(new Company(1, "Spring"));
        companies.add(new Company(2, "Spring2"));

        //when
        List<Company> actual = companyService.findAll();
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_employees_when_get_all_given_employees() {
        //given
        List<Company> companies = new ArrayList<>();
        List<Employee> employees = getEmployees();
        companies.add(new Company(1, "Spring"));
        companies.add(new Company(2, "Spring2"));
        Employee employee = new Employee("Klaus",1,20,99999999,"female",1);
        given(employeeService.getEmployeesByCompanyID(1)).willReturn(employees);
        System.out.println(employeeService.getByID(1).getName());
        //when
        List<Employee> actual = companyService.getEmployeesByCompanyID(1);
        //return
        System.out.println(companyService.getEmployeesByCompanyID(1).size());
        assertEquals(employees,actual);
    }
}
