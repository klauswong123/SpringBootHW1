package com.example.demo.mapper;

import com.example.demo.dto.CompanyRequest;
import com.example.demo.dto.CompanyResponse;
import com.example.demo.dto.CompanyResponseNoEmployee;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CompanyMapper {
    private EmployeeMapper employeeMapper = new EmployeeMapper();
    public Company toEntity(CompanyRequest companyRequest){
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest,company);
        return company;
    }

    public CompanyResponseNoEmployee toResponseNoEmployee(Company company){
        CompanyResponseNoEmployee companyResponseNoEmployee= new CompanyResponseNoEmployee();
        BeanUtils.copyProperties(company, companyResponseNoEmployee);
        return companyResponseNoEmployee;
    }

    public CompanyResponse toResponse(Company company, List<Employee> employees){
        CompanyResponse companyResponse= new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        companyResponse.setEmployeeResponses(
                employees.stream()
                        .map(employee -> employeeMapper.toResponse(employee))
                        .collect(Collectors.toList())
        );

        return companyResponse;
    }
}
