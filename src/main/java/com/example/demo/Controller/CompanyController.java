package com.example.demo.Controller;

import com.example.demo.Exception.NoCompanyFoundException;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Entity.Company;
import com.example.demo.Entity.Employee;
import com.example.demo.Service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyRepository companyRepository;
    private CompanyService companyService;
    public CompanyController(CompanyRepository companyRepository, CompanyService companyService){
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getCompaies(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyByID(@PathVariable("id") Integer id) {
        return companyService.getByID(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByID(@PathVariable("id") Integer id){
        return companyService.getEmployeesByCompanyID(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getCompanyByPage(@RequestParam Integer page, Integer pageSize){
        return companyService.getByPage(page,pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@RequestBody Company company){
        return companyService.create(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable ("id") Integer id, @RequestBody Company company){
        return companyService.edit(id,company);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable ("id") Integer id){
        companyService.delete(id);
    }

}
