package com.example.demo.Service;

import com.example.demo.Entity.Company;
import com.example.demo.Entity.Employee;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository ){
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> findAll() {
//        List<Integer> ids = companyRepository.findAll().stream().map(Company::getId).collect(Collectors.toList());
//        List<Company> companies = ids.stream().map(id->companyRepository.getByID(id)).collect(Collectors.toList());
//        if (companies.size()>0){
//            companies.forEach(company -> company.setEmployees(employeeRepository.getEmployeesByCompanyID(company.getId())));
//            companies.forEach(company -> companyRepository.update(company.getId(),company));
//        }
        return companyRepository.findAll();
    }

    public Company edit(Integer id, Company updateCompany) {
        return companyRepository.update(id,updateCompany);
    }

    public Company getByID(Integer id) {
        Company company = companyRepository.getByID(id);
        company.setEmployees(employeeRepository.getEmployeesByCompanyID(id));
        return company;
    }

    public List<Employee> getEmployeesByCompanyID(Integer companyID){
        return employeeRepository.getEmployeesByCompanyID(companyID);

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
