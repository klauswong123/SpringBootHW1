package com.example.demo.controller;

import com.example.demo.dto.CompanyRequest;
import com.example.demo.dto.CompanyResponse;
import com.example.demo.dto.CompanyResponseNoEmployee;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.mapper.CompanyMapper;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.entity.Company;
import com.example.demo.service.CompanyService;
import com.example.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyService companyService;
    private CompanyMapper companyMapper;
    private EmployeeMapper employeeMapper;
    private EmployeeService employeeService;
    public CompanyController(CompanyService companyService, CompanyMapper companyMapper, EmployeeMapper employeeMapper, EmployeeService employeeService){
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Company> getCompaies(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyByID(@PathVariable("id") String id) {
        return companyMapper.toResponse(companyService.getByID(id),companyService.getEmployeesByCompanyID(id));
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeesByID(@PathVariable("id") String id){
        return companyService.getEmployeesByCompanyID(id).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page","pageSize"})
    public List<CompanyResponseNoEmployee> getCompanyByPage(@RequestParam Integer page, Integer pageSize){
        return companyService.getByPage(page,pageSize).stream()
                .map(company -> companyMapper.toResponseNoEmployee(company))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@RequestBody CompanyRequest companyRequest){
        return companyService.create(companyMapper.toEntity(companyRequest));
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable ("id") String id, @RequestBody CompanyRequest companyRequest) {
        System.out.println(companyMapper.toEntity(companyRequest).getName());
        return companyService.edit(id,companyMapper.toEntity(companyRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable ("id") String id){
        companyService.delete(id);
    }

}
