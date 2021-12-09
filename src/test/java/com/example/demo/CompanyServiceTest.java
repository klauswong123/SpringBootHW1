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
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CompanyService companyService;

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Klauswong",1,  22,  100000, "Female",1));
        employees.add(new Employee("Klaus1",2,  22,  100000, "Female",1));
        return employees;
    }

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1,"Spring",null));
        given(companyRepository.findAll())
                .willReturn(companies);

        //when
        List<Company> actual = companyService.findAll();

        //then
        assertEquals(companies, actual);
    }
    @Test
    void should_return_a_company_when_get_company_given_company_id() {
        //given
        List<Company> companies = new ArrayList<>();

        Company company1 = new Company(1,"Spring",null);
        Company company2 = new Company(2,"Spring2",null);
        companies.add(company1);
        companies.add(company2);
        given(companyRepository.getByID(1))
                .willReturn(company1);
        //when
        Company actual = companyService.getByID(company1.getId());
        //then
        assertEquals(company1, actual);
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "OOCL",null));
        companies.add(new Company(2, "OOCL2",null));

        Integer page = 1;
        Integer pageSize = 2;

        given(companyRepository.getByPage(page, pageSize))
                .willReturn(companies);

        //when`
        List<Company> actual = companyService.getByPage(page, pageSize);
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        Company newCompany = new Company(3, "OOCL3",null);
        given(companyRepository.create(newCompany))
                .willReturn(newCompany);
        //when
        Company actual = companyService.create(newCompany);
        //then
        assertEquals(newCompany, actual);
    }

    @Test
    void should_return_update_company_when_perform_put_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "OOCL3",null);
        Company updatedCompany = new Company(1, "OOCLL",null);
        company.setName(updatedCompany.getName());
        given(companyRepository.getByID(1))
                .willReturn(company);
        given(companyRepository.update(1, updatedCompany))
                .willReturn(company);
        //when
        Company actual = companyService.edit(1, updatedCompany);
        System.out.println(actual.getName());
        //then
        assertEquals(updatedCompany.getId(), actual.getId());

    }


    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company = new Company(1, "OOCL",null);

        //when
        companyService.delete(company.getId());

        //then
        verify(companyRepository).delete(company.getId());
    }


}