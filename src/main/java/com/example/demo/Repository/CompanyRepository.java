package com.example.demo.Repository;

import com.example.demo.Entity.Company;
import com.example.demo.Entity.Employee;
import com.example.demo.Exception.NoCompanyFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    @Autowired
    EmployeeRepository employeeRepository;
    private List<Company> companies = new ArrayList<>();

    CompanyRepository(EmployeeRepository employeeRepository){
        this.companies.add(new Company(1,"Apple"));
        this.companies.add(new Company(2,"MineCraft"));
        this.employeeRepository = employeeRepository;
    }
    public List<Company> findAll(){
        companies.forEach(company -> company.setEmployees(null));
        return companies;
    }

    public Company getByID(Integer id){
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst().orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> getEmployeesByCompanyID(Integer id){
        return Objects.requireNonNull(companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst().orElse(null)).getEmployees();
    }

    public Company create(Company company){
        Integer id = companies.stream().mapToInt(Company::getId).max().orElse(0)+1;
        company.setId(id);
        companies.add(company);
        return company;
    }

    public Company update(Integer id, Company company) {
        Company targetCompany = companies.stream()
                .filter(singleEmployee -> singleEmployee.getId().equals(id))
                .findFirst().orElseThrow(NullPointerException::new);
        if(company.getEmployees()!=null) targetCompany.setEmployees(company.getEmployees());
        if(company.getName()!=null) targetCompany.setName(company.getName());
        companies.remove(company);
        companies.add(company);
        return targetCompany;
    }

    public Company delete(Integer id) {
        Company deletedCompany = getByID(id);
        companies.removeIf(company -> company.getId().equals(id));
        return deletedCompany;
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip((long) (page-1) *pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void clearAll() {
        companies.clear();
    }
}
