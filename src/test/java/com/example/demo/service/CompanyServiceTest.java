package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.exception.NoCompanyFoundException;
import com.example.demo.exception.NoEmployeeFoundException;
import com.example.demo.mapper.CompanyMapper;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    CompanyService companyService;

    private Employee getEmployee(String companyId){
        return new Employee("Klaus",20,99999999,"female",companyId);
    }

    private Employee getEmployee1(String companyId){
        return  new Employee("Chris",23,999999,"male",companyId);
    }


    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("Spring"));
        given(companyRepository.findAll())
                .willReturn(companies);
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
        given(companyRepository.findById(company1.getId()))
                .willReturn(java.util.Optional.of(company1));
        //when
        Company actual = companyService.getByID(company1.getId());
        //then
        assertEquals(company1, actual);
    }

    @Test
    void should_return_employees_when_get_company_employee_given_company_id() {
        //given
        List<Company> companies = new ArrayList<>();
        Company company1 = new Company("Spring");
        companies.add(company1);
        List<Employee> employees = new ArrayList<>();
        Employee employee = getEmployee(company1.getId());
        employees.add(employee);
        given(employeeRepository.findByCompanyID(company1.getId()))
                .willReturn(employees);
        //when
        List<Employee> actual = companyService.getEmployeesByCompanyID(company1.getId());
        //then
        assertEquals(employees, actual);
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("OOCL"));
        companies.add(new Company("OOCL2"));
        Integer page = 1;
        Integer pageSize = 2;
        given(companyRepository.findAll(PageRequest.of(page-1, pageSize)))
                .willReturn(new PageImpl<>(companies, PageRequest.of(page-1, pageSize), pageSize));

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
        given(companyRepository.insert(newCompany))
                .willReturn(newCompany);
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
        given(companyRepository.findById(company.getId()))
                .willReturn(java.util.Optional.of(company));
        given(companyRepository.save(company))
                .willReturn(company);
        //when
        Company actual = companyService.edit(company.getId(), updatedCompany);
        System.out.println(actual.getName());
        //then
        assertEquals(updatedCompany.getId(), actual.getId());

    }


    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company = new Company("Spring");
        willDoNothing().given(companyRepository).deleteById(company.getId());

        //when
        companyService.delete(company.getId());
        //then
        verify(companyRepository).deleteById(company.getId());
        assertEquals(0, companyRepository.findAll().size());
    }

    @Test
    void should_throw_notFound_when_find_by_id_given_fakeId() {
        //given
        given(companyRepository.findById("123445"))
                .willThrow(NoCompanyFoundException.class);
        //when
        //then
        assertThrows(NoCompanyFoundException.class, () -> companyService.getByID("123445"));
    }
}