package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_get_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee("Klaus",1,20,99999999,"female");
        employees.add(employee);
        given(employeeRepository.findAll()).willReturn(employees);
        //when
        List<Employee> actual = employeeService.findAll();
        //return
        assertEquals(employees,actual);
    }

    @Test
    void should__when__given() {
        //given
        Employee employee = new Employee("Klaus",1,20,99999999,"female");
        Employee updateEmployee = new Employee("Klaus",1,23,99999999,"female");
        given(employeeRepository.update(employee.getId(),updateEmployee))
                .willReturn(employee);
        //when
        Employee actual = employeeService.edit(employee.getId(),updateEmployee);
        verify(employeeRepository).update(employee.getId(),updateEmployee);
        //return
        assertEquals(employee,actual);
    }
}