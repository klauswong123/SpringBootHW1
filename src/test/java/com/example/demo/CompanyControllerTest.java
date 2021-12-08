package com.example.demo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void cleanRepository(){
        companyRepository.clearAll();
    }

    @Test
    void should_get_all_companies_when_perform_get_given_() throws Exception {
        //given
        Employee employee1 = new Employee("Klaus",1,23,999999,"male");
        Employee employee2 = new Employee("Jason",2,24,12312412,"female");
        companyRepository.create(new Company(1,"Apple", List.of(employee1,employee2)));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Apple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("Klaus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(999999))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[1].name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[1].age").value(24))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[1].salary").value(12312412))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[1].gender").value("female"));
        //then
    }

    @Test
    void should_get_company_when_perform_get_given_id() throws Exception {
        //given
        Employee employee1 = new Employee("Klaus",1,23,999999,"male");
        Employee employee2 = new Employee("Jason",2,24,12312412,"female");
        companyRepository.create(new Company(1,"Apple", List.of(employee1,employee2)));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Apple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("Klaus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(999999))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[1].name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[1].age").value(24))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[1].salary").value(12312412))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[1].gender").value("female"));
        //then
    }

//    @Test
//    void should_return_employees_when_get_given_page_and_pageSize() throws Exception {
//        //given
//        Employee employee1 = new Employee("Klaus",1,23,999999,"male");
//        Employee employee2 = new Employee("Jason",2,24,12312412,"female");
//        companyRepository.create(new Company(1,"Apple", List.of(employee1,employee2)));
//        Employee employee3 = new Employee("Kam",3,23,999999,"male");
//        Employee employee4 = new Employee("Louis",4,24,12312412,"female");
//        companyRepository.create(new Company(2,"MineCraft",List.of(employee3,employee4)));
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.get("/companies?page=1&pageSize=")){
//
//        }
//        //return
//    }
}
