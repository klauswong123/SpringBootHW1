package com.example.demo;
import com.example.demo.Entity.Company;
import com.example.demo.Entity.Employee;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @BeforeEach
    void cleanRepository(){
        companyRepository.clearAll();
    }

    private List<Employee> getEmployees(){
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("Klaus",1,23,999999,"male",1);
        Employee employee2 = new Employee("Jason",2,24,12312412,"female",1);
        employees.add(employee1);
        employees.add(employee2);
        return employees;
    }
    @Test
    void should_get_all_companies_when_perform_get_given_() throws Exception {
        //given
        Employee employee1 = new Employee("Klaus",1,23,999999,"male",1);
        Employee employee2 = new Employee("Jason",2,24,12312412,"female",1);
        companyRepository.create(new Company(1,"Apple",null));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Apple"));
        //then
    }

    @Test
    void should_get_company_when_perform_get_given_id() throws Exception {
        //given
        Employee employee1 = new Employee("Klaus",1,23,999999,"male",1);
        Employee employee2 = new Employee("Jason",2,24,12312412,"female",1);
        companyRepository.create(new Company(1,"Apple", List.of(employee1,employee2)));
        //when
        String employeeAsJson = new ObjectMapper().writeValueAsString(new Company(1,"Apple", List.of(employee1,employee2)));
        String returnBody = mockMvc.perform(MockMvcRequestBuilders.get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Apple"))
                .andReturn().getResponse().getContentAsString();
        assertEquals(returnBody,employeeAsJson);
        //then
    }

    @Test
    void should_get_employees_when_perform_get_given_id() throws Exception {
        //given
        Employee employee1 = new Employee("Klaus",1,23,999999,"male",1);
        Employee employee2 = new Employee("Jason",2,24,12312412,"female",1);
        companyRepository.create(new Company(1,"Apple", List.of(employee1,employee2)));
        //when
        String employeeAsJson = new ObjectMapper().writeValueAsString(List.of(employee1,employee2));
        String returnBody = mockMvc.perform(MockMvcRequestBuilders.get("/companies/1/employees"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(returnBody,employeeAsJson);
        //then
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        Company company1 = new Company(1, "Spring",null);
        Company company2 = new Company(2, "Spring2",null);
        Company company3 = new Company(3, "Spring3",null);

        companyRepository.create(company1);
        companyRepository.create(company2);
        companyRepository.create(company3);

        String page = "1";
        String pageSize = "2";

        mockMvc.perform(MockMvcRequestBuilders.get("/companies?page="+page+"&pageSize="+pageSize))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        String company = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"OOCL\",\n" +
                "    \"employees\": [\n" +
                "    ]\n" +
                "}";

        //when
        //then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(status().isCreated());
    }

    @Test
    void should_return_changed_company_when_perform_put_given_company_id() throws Exception {
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));
        String company="{\"name\": \"Spring111\"}";
        //when
        //then
        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Spring111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        List<Employee> employees = getEmployees();
        Company company1 = new Company(1, "Spring",null);
        Company company2 = new Company(2, "Spring2",null);
        Company company3 = new Company(3, "Spring3",null);

        companyRepository.create(company1);
        companyRepository.create(company2);
        companyRepository.create(company3);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", company1.getId()))
                .andExpect(status().isNoContent());
    }
}
