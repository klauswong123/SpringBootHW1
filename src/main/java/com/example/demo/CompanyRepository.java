package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    CompanyRepository(){
        Employee employee1 = new Employee("Klaus",1,23,999999,"male");
        Employee employee2 = new Employee("Jason",2,24,12312412,"female");
        this.companies.add(new Company(1,"Apple",List.of(employee1,employee2)));
        Employee employee3 = new Employee("Kam",3,23,999999,"male");
        Employee employee4 = new Employee("Louis",4,24,12312412,"female");
        this.companies.add(new Company(2,"MineCraft",List.of(employee3,employee4)));

    }
    public List<Company> findAll(){
        return this.companies;
    }

    public Company getByID(Integer id){
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Employee> getEmployees(Integer id){
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
        targetCompany.setEmployees(company.getEmployees());
        targetCompany.setName(company.getName());
        return targetCompany;
    }

    public void delete(Integer id) {
        companies.removeIf(company -> company.getId().equals(id));
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip((long) page *pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}
