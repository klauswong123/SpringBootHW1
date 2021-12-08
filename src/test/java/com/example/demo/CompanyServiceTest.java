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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CompanyService companyService;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1,"Spring"));
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

        Company company1 = new Company(1,"Spring");
        Company company2 = new Company(2,"Spring2");
        companies.add(company1);
        companies.add(company2);
        given(companyRepository.getByID(any()))
                .willReturn(company1);
        //when
        Company actual = companyService.getByID(company1.getId());
        //then
        assertEquals(company1, actual);
    }



}