package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeMapper employeeMapper;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_get_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
        employees.add(employee);
        given(employeeService.findAll()).willReturn(employees);
        //when
        List<Employee> actual = employeeService.findAll();
        System.out.println(actual.size());
        //return
        assertEquals(employees,actual);
    }

    @Test
    void should_update_employee_when_update_given_employees() {
        //given
        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
        Employee updateEmployee = new Employee("Klaus","1",23,99999999,"female","1");
        given(employeeRepository.update(employee.getId(),updateEmployee))
                .willReturn(employee);
        //when
        Employee actual = employeeService.edit(employee.getId(),updateEmployee);
        verify(employeeRepository).update(employee.getId(),updateEmployee);
        //return
        assertEquals(employee,actual);
    }

    @Test
    void should_employee_when_get_given_id() {
        //given
        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
        given(employeeRepository.getByID(any()))
                .willReturn(employee);
        //when
        Employee actual = employeeService.getByID(employee.getId());
        verify(employeeRepository).getByID(employee.getId());
        //return
        assertEquals(employee,actual);
    }

    @Test
    void should_employee_when_get_given_gender() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("Klaus","1",20,99999999,"female","1");
        Employee employee2 = new Employee("Nick","2",50,1000,"male","1");
        Employee employee3 = new Employee("Jack","3",60,1,"male","1");
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        given(employeeRepository.getByGender("male"))
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.getByGender("male");
        verify(employeeRepository).getByGender("male");
        //return
        assertEquals(employees,actual);
    }

    @Test
    void should_create_employee_when_create_given_employee() {
        //given
        Employee employee1 = new Employee("Klaus","1",20,99999999,"female","1");
        given(employeeRepository.create(any()))
                .willReturn(employee1);
        //when
        Employee actual = employeeService.create(employee1);
        verify(employeeRepository).create(employee1);
        //return
        assertEquals(employee1,actual);
    }

    @Test
    void should_get_employees_when_get_given_page_and_pageSize() {
        //given
        List<Employee> employees = new ArrayList<>();
        Integer page = 1;
        Integer pageSize = 2;
        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
        given(employeeRepository.getByPage(page,pageSize))
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.getByPage(page,pageSize);
        verify(employeeRepository).getByPage(page,pageSize);
        //return
        assertEquals(employees,actual);
    }

    @Test
    void should_delete_employee_when_delete_given_id() {
        //given
        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
        given(employeeRepository.delete(any()))
                .willReturn(employee);
        //when
        verify(employeeRepository).delete(employee.getId());
    }
}
