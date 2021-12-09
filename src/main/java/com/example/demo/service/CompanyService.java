package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.exception.NoCompanyFoundException;
import com.example.demo.mapper.CompanyMapper;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.CompanyReposityMongo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private CompanyReposityMongo companyReposityMongo;
    private CompanyMapper companyMapper;
    private EmployeeMapper employeeMapper;

    public CompanyService(CompanyReposityMongo companyReposityMongo, CompanyMapper companyMapper, EmployeeMapper employeeMapper){
        this.companyReposityMongo = companyReposityMongo;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    public List<Company> findAll() {
        return companyReposityMongo.findAll();
    }

    public Company edit(String id, Company company){
        Company targetCompany = getByID(id);
        if(company.getName()!=null) targetCompany.setName(company.getName());
        companyReposityMongo.save(targetCompany);
        return targetCompany;
    }

    public Company getByID(String id) {
        return companyReposityMongo.findById(id).orElseThrow(NoCompanyFoundException::new);
    }


    public Company create(Company company) {
        return companyReposityMongo.insert(company);
    }

    public List<Company> getByPage(int page, int pageSize) {
        return companyReposityMongo.findAll().stream()
                .skip((long) (page-1) *pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        companyReposityMongo.deleteById(id);
    }
}
