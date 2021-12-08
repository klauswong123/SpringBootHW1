package com.example.demo.Service;

import com.example.demo.Entity.Company;
import com.example.demo.Entity.Employee;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private EmployeeService employeeService;

    private CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        List<Integer> ids = companyRepository.findAll().stream().map(Company::getId).collect(Collectors.toList());
        List<Company> companies = ids.stream().map(id->companyRepository.getByID(id)).collect(Collectors.toList());
        companies.forEach(company -> company.setEmployees(employeeService.getEmployeesByCompanyID(company.getId())));
        companies.forEach(company -> companyRepository.update(company.getId(),company));
        return companies;
    }

    public Company edit(Integer id, Company updateCompany) {
        return companyRepository.update(id,updateCompany);
    }

    public Company getByID(Integer id) {
        Company company = companyRepository.getByID(id);
        company.setEmployees(employeeService.getEmployeesByCompanyID(id));
        return company;
    }

    public List<Employee> getEmployeesByCompanyID(Integer companyID){
        return employeeService.getEmployeesByCompanyID(companyID);

    }
    public Company create(Company company) {
        return companyRepository.create(company);
    }

    public List<Company> getByPage(int page, int pageSize) {
        return companyRepository.getByPage(page,pageSize);
    }

    public Company delete(Integer id) {
        return companyRepository.delete(id);
    }
}
