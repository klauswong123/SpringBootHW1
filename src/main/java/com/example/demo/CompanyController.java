package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyRepository companyRepository;
    public CompanyController(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getCompaies(){
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyByID(@PathVariable("id") Integer id){
        return companyRepository.getByID(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByID(@PathVariable("id") Integer id){
        return companyRepository.getEmployees(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getCompanyByPage(@RequestParam Integer page, Integer pageSize){
        return companyRepository.getByPage(page,pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@RequestBody Company company){
        return companyRepository.create(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable ("id") Integer id, @RequestBody Company company){
        return companyRepository.update(id,company);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable ("id") Integer id){
        companyRepository.delete(id);
    }

}
