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


    private Employee getEmployee(){
        return new Employee("Klaus",20,99999999,"female");
    }

    private Employee getEmployee1(){
        return  new Employee("Chris",23,999999,"male");
    }

    @Test
    void should_return_all_employees_when_get_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        getEmployee();

        given(employeeRepository.findAll()).willReturn(employees);
        //when
        List<Employee> actual = employeeService.findAll();
        //return
        assertEquals(employees.size(),actual.size());
    }

    @Test
    void should_update_employee_when_update_given_employees() {
        //given
        Employee employee= getEmployee();
        Employee updatedEmployee = new Employee("Jason", 10, 2000,"male");
        given(employeeRepository.findById(any()))
                .willReturn(java.util.Optional.of(employee));
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        given(employeeRepository.save(any(Employee.class)))
                .willReturn(employee);
        //when
        Employee actual = employeeService.edit(any(), updatedEmployee);

        //then
        assertEquals(employee, actual);
    }

    @Test
    void should_employee_when_get_given_id() {
        //given
        Employee employee = getEmployee();
        given(employeeRepository.findById(employee.getId()))
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
        Employee employee1 = getEmployee();
        Employee employee2 = getEmployee1();
        employees.add(employee1);
        employees.add(employee2);
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
