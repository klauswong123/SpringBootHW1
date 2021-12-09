package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.mapper.CompanyMapper;
import com.example.demo.service.CompanyService;
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
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyMapper companyMapper;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    CompanyService companyService;

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Klauswong","1",  22,  100000, "Female","1"));
        employees.add(new Employee("Klaus1","2",  22,  100000, "Female","1"));
        return employees;
    }

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("Spring"));

        //when
        List<Company> actual = companyService.findAll();

        //then
        assertEquals(companies.size(), actual.size());
    }
    @Test
    void should_return_a_company_when_get_company_given_company_id() {
        //given
        List<Company> companies = new ArrayList<>();

        Company company1 = new Company("Spring");
        Company company2 = new Company("Spring2");
        companies.add(company1);
        companies.add(company2);
        //when
        Company actual = companyService.getByID(company1.getId());
        //then
        assertEquals(company1, actual);
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("OOCL"));
        companies.add(new Company("OOCL2"));

        Integer page = 1;
        Integer pageSize = 2;


        //when`
        List<Company> actual = companyService.getByPage(page, pageSize);
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        Company newCompany = new Company("OOCL3");
        //when
        Company actual = companyService.create(newCompany);
        //then
        assertEquals(newCompany, actual);
    }

    @Test
    void should_return_update_company_when_perform_put_given_company_id() throws Exception {
        //given
        Company company = new Company("OOCL3");
        Company updatedCompany = new Company("OOCLL");
        company.setName(updatedCompany.getName());
        //when
        Company actual = companyService.edit("1", updatedCompany);
        System.out.println(actual.getName());
        //then
        assertEquals(updatedCompany.getId(), actual.getId());

    }


    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company = new Company("OOCL");

        //when
        companyService.delete(company.getId());

        //then
    }


}