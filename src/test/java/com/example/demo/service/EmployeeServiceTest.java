package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
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
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;


    private Employee getSingleEmployee(){
        return new Employee("Klaus",20,99999999,"female");
    }

    private List<Employee> getEmployees(){
        return List.of(
                new Employee("Klaus",20,99999999,"female"),
                new Employee("Jason",24,123,"male"),
                new Employee("Kam",21,112312323,"male"),
                new Employee("jenn",24,123,"female")
        );
    }

    @Test
    void should_return_all_employees_when_get_all_given_employees() {
        //given
        List<Employee> employees = getEmployees();

        given(employeeRepository.findAll()).willReturn(employees);
        //when
        System.out.println(employees.size());
        List<Employee> actual = employeeService.findAll();
        System.out.println(actual.size());
        //return
        assertEquals(employees.size(),actual.size());
    }

    @Test
    void should_update_employee_when_update_given_employees() {
        //given
        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
        Employee updateEmployee = new Employee("Klaus","1",23,99999999,"female","1");
        given(employeeRepository.save(updateEmployee))
                .willReturn(employee);
        //when
        Employee actual = employeeService.edit(employee.getId(),updateEmployee);
        verify(employeeRepository).save(updateEmployee);
        //return
        assertEquals(employee,actual);
    }

    @Test
    void should_employee_when_get_given_id() {
        //given
        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
        given(employeeRepository.findById(any()))
                .willReturn(java.util.Optional.of(employee));
        //when
        Employee actual = employeeService.getByID(employee.getId());
        verify(employeeRepository).findById(employee.getId());
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
        given(employeeRepository.findByGender("male"))
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.getByGender("male");
        verify(employeeRepository).findByGender("male");
        //return
        assertEquals(employees,actual);
    }

    @Test
    void should_create_employee_when_create_given_employee() {
        //given
        Employee employee1 = new Employee("Klaus","1",20,99999999,"female","1");
        given(employeeRepository.insert(employee1))
                .willReturn(employee1);
        //when
        Employee actual = employeeService.create(employee1);
        verify(employeeRepository).insert(employee1);
        //return
        assertEquals(employee1,actual);
    }

//    @Test
//    void should_get_employees_when_get_given_page_and_pageSize() {
//        //given
//        List<Employee> employees = new ArrayList<>();
//        Integer page = 1;
//        Integer pageSize = 2;
//        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
//        given(employeeRepositoryMongo.(page,pageSize))
//                .willReturn(employees);
//        //when
//        List<Employee> actual = employeeService.getByPage(page,pageSize);
//        verify(employeeRepositoryMongo).getByPage(page,pageSize);
//        //return
//        assertEquals(employees,actual);
//    }

//    @Test
//    void should_delete_employee_when_delete_given_id() {
//        //given
//        Employee employee = new Employee("Klaus","1",20,99999999,"female","1");
//        given(employeeRepositoryMongo.deleteById(employee.getId()))
//                .willReturn(employee);
//        //when
//        verify(employeeRepositoryMongo).delete(employee.getId());
//    }
}
