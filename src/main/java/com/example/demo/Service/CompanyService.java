package com.example.demo.Service;

import com.example.demo.Entity.Company;
import com.example.demo.Entity.Employee;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.EmployeeRepository;

import java.util.List;

public class CompanyService {
    private CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company edit(Integer id, Company updateCompany) {
        return companyRepository.update(id,updateCompany);
    }

    public Company getByID(Integer id) {
        return companyRepository.getByID(id);
    }

    public List<Employee> getCompaniesByCompany(String gender) {
        return null;
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
