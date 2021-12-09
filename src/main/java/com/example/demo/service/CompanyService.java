package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.exception.NoCompanyFoundException;
import com.example.demo.mapper.CompanyMapper;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;
    private EmployeeMapper employeeMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, EmployeeMapper employeeMapper){
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company edit(String id, Company company){
        Company targetCompany = getByID(id);
        if(company.getName()!=null) targetCompany.setName(company.getName());
        companyRepository.save(targetCompany);
        return targetCompany;
    }

    public Company getByID(String id) {
        return companyRepository.findById(id).orElseThrow(NoCompanyFoundException::new);
    }


    public Company create(Company company) {
        return companyRepository.insert(company);
    }

    public List<Company> getByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page-1, pageSize)).toList();
    }

    public void delete(String id) {
        companyRepository.deleteById(id);
    }
}
