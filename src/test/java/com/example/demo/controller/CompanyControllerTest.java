package com.example.demo.controller;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.mapper.CompanyMapper;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    CompanyService companyService;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    @BeforeEach
    @AfterEach
    void cleanCompany(){
        companyRepository.deleteAll();
    }

    private Company preCreateCompany1(){
        Company company = companyRepository.insert(new Company("Apple"));
        employeeRepository.insert(new Employee("Klaus",23,999999,"male",company.getId()));
        employeeRepository.insert(new Employee("Jason",24,12312412,"female",company.getId()));
        return company;
    }

    private Company preCreateCompany2(){
        Company company = companyRepository.insert(new Company("Wealth"));
        employeeRepository.insert(new Employee("Kam",23,999999,"male",company.getId()));
        employeeRepository.insert(new Employee("Pang",24,12312412,"female",company.getId()));
        return company;
    }


    @Test
    void should_get_all_companies_when_perform_get_given_() throws Exception {
        //given
        preCreateCompany1();
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Apple"));
        //then
    }

    @Test
    void should_get_company_when_perform_get_given_id() throws Exception {
        //given
        Company company = preCreateCompany1();
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}",company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Apple"));
        //then
    }

    @Test
    void should_get_employees_when_perform_get_given_id() throws Exception {
        //given
        Company company = preCreateCompany1();
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", company.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
        //then
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        String page = "1";
        String pageSize = "2";
        Company company = preCreateCompany1();
        Company company1 = preCreateCompany2();
        mockMvc.perform(MockMvcRequestBuilders.get("/companies?page="+page+"&pageSize="+pageSize))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(company.getId(), company1.getId())));
    }

    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        String company = "{\n" +
                "    \"name\": \"OOCL\"\n" +
                "}";
        //when
        //then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("OOCL"));
    }

    @Test
    void should_return_changed_company_when_perform_put_given_company_id() throws Exception {
        //given
        Company company = preCreateCompany1();
        String companyName="{\"name\": \"Spring111\"}";
        //when
        //then
        mockMvc.perform(put("/companies/{id}",company.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Spring111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString());
    }

    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company = preCreateCompany1();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", company.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_notfound_when_perform_get_given_company_and_falseId() throws Exception {
        //given
        Company company = preCreateCompany1();
        companyRepository.delete(company);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}",company.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
